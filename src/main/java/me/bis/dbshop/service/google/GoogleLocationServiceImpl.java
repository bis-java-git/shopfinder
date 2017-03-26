package me.bis.dbshop.service.google;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

    final static Logger logger = (Logger) LoggerFactory.getLogger(GoogleLocationServiceImpl.class);

    public static final double VAL = 60.0 * 1.1515;

    public static final double DEGREES_180 = 180.0;

    public static final int SCALE = 10;

    private String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ")", replacement);
    }

    private BigDecimal deg2rad(BigDecimal degrees) {
        return degrees.multiply(new BigDecimal(Math.PI).divide(new BigDecimal(DEGREES_180, MathContext.DECIMAL128), SCALE, BigDecimal.ROUND_HALF_EVEN));
    }

    private BigDecimal rad2deg(BigDecimal radians) {
        return (radians.multiply(new BigDecimal(DEGREES_180, MathContext.DECIMAL128).divide(new BigDecimal(Math.PI, MathContext.DECIMAL128), SCALE, BigDecimal.ROUND_HALF_EVEN)));
    }

    private LatLng getGeoResults(String json, String postCode) throws GeoLocatorServiceException {

        ObjectMapper objectMapper = new ObjectMapper();
        /**  TODO
         * JSON is slightly broken from google API or some more work needs doing here,
         * hence as time pressured I have replacing JSON violating [ and ]
         * NEED MORE WORK TO COMPLETE IT
         */
        String bracketOpen = "\\[";
        String bracketClose = "\\]";
        json = json.replace("\n\\s", "");
        json = json.replaceFirst(bracketOpen, "");
        json = replaceLast(json, bracketClose, "");



        try {
            GeoResults geoResults = objectMapper.readValue(json, GeoResults.class);
            logger.info("Latitude: {} ", geoResults.getResults().getGeometry().getLocation().getLatitude());
            logger.info("Longitude: {} ", geoResults.getResults().getGeometry().getLocation().getLongitude());

            return new LatLng(geoResults.getResults().getGeometry().getLocation().getLatitude().setScale(SCALE),
                    geoResults.getResults().getGeometry().getLocation().getLongitude().setScale(SCALE));

        } catch (JsonParseException | JsonMappingException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            throw new GeoLocatorServiceException("Parser Latitude/Longitude not found for postcode  " + postCode);
        } catch (IOException e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            throw new GeoLocatorServiceException("IO Latitude/Longitude not found for postcode  " + postCode);
        }
    }

    public LatLng GetLatLng(String postCode) throws GeoLocatorServiceException {
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject("http://maps.googleapis.com/maps/api/geocode/json?address=" + postCode, String.class);
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
