package me.bis.dbshop.service.google;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.bis.dbshop.domain.google.GeoResults;
import me.bis.dbshop.domain.google.LatLng;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;

@Service
public class GoogleLocationServiceImpl implements GoogleLocatorService {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(GoogleLocationServiceImpl.class);

    private static final double VAL = 60.0 * 1.1515;

    private static final double DEGREES_180 = 180.0;

    private static final int SCALE = 10;

    private String replaceLast(String text,
                               String regex) {
        return text.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ")", "");
    }

    private BigDecimal deg2rad(BigDecimal degrees) {
        return degrees.multiply(new BigDecimal(Math.PI).divide(new BigDecimal(DEGREES_180, MathContext.DECIMAL128), SCALE, BigDecimal.ROUND_HALF_EVEN));
    }

    private BigDecimal rad2deg(BigDecimal radians) {
        return (radians.multiply(new BigDecimal(DEGREES_180, MathContext.DECIMAL128).divide(new BigDecimal(Math.PI, MathContext.DECIMAL128), SCALE, BigDecimal.ROUND_HALF_EVEN)));
    }

    private LatLng getGeoResults(String json, String postCode) {
        ObjectMapper objectMapper = new ObjectMapper();
        String bracketOpen = "\\[";
        String bracketClose = "\\]";
        json = json.replace("\n\\s", "");
        json = json.replaceFirst(bracketOpen, "");
        json = replaceLast(json, bracketClose);
        try {
            GeoResults geoResults = objectMapper.readValue(json, GeoResults.class);
            logger.info("Latitude: {} ", geoResults.getResults().getGeometry().getLocation().getLatitude());
            logger.info("Longitude: {} ", geoResults.getResults().getGeometry().getLocation().getLongitude());
            return new LatLng(geoResults.getResults().getGeometry().getLocation().getLatitude().setScale(SCALE, BigDecimal.ROUND_HALF_EVEN),
                    geoResults.getResults().getGeometry().getLocation().getLongitude().setScale(SCALE, BigDecimal.ROUND_HALF_EVEN));
        } catch (IOException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    public LatLng GetLatLng(String postCode) {
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject("https://maps.googleapis.com/maps/api/geocode/json?address=" + postCode, String.class);
        return getGeoResults(json, postCode);
    }

    public BigDecimal calculateDistance(BigDecimal latitudeFrom,
                                        BigDecimal longitudeFrom,
                                        BigDecimal latitudeTo,
                                        BigDecimal longitudeTo) {
        BigDecimal theta = longitudeFrom.subtract(longitudeTo, MathContext.DECIMAL128);
        BigDecimal dist = new BigDecimal(Math.sin(deg2rad(latitudeFrom).doubleValue())
                * Math.sin(deg2rad(latitudeTo).doubleValue())
                + Math.cos(deg2rad(latitudeFrom).doubleValue()) * Math.cos(deg2rad(latitudeTo).doubleValue())
                * Math.cos(deg2rad(theta).doubleValue()));
        dist = rad2deg(new BigDecimal(Math.acos(dist.doubleValue())));
        dist = dist.multiply(new BigDecimal(VAL, MathContext.DECIMAL128)).setScale(SCALE, BigDecimal.ROUND_HALF_EVEN);
        return dist;
    }
}
