package me.bis.dbshop.service.google;

import me.bis.dbshop.domain.google.LatLng;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * Google Geo finder service test
 */
public class GoogleLocationServiceTest {

    private GoogleLocationServiceImpl googleLocationService = new GoogleLocationServiceImpl();

    private static final Integer SCALE = 10;

    private final static String POST_CODE = "TN2 5HJ";

    private static BigDecimal EXPECTED_LATITUDE = new BigDecimal("51.1200772000");

    private static BigDecimal EXPECTED_LONGITUDE = new BigDecimal("0.2800051000");

    private static BigDecimal TN2_FROM_LATITUDE = new BigDecimal("51.1424544").setScale(SCALE);

    private static BigDecimal TN2_FROM_LONGITUDE = new BigDecimal("0.3077243").setScale(SCALE);

    private static BigDecimal TW6_FROM_LATITUDE = new BigDecimal("51.4668442").setScale(SCALE);

    private static BigDecimal TW6_FROM_LONGITUDE = new BigDecimal("-0.4529518").setScale(SCALE);

    private static BigDecimal TN2_TW6_EXPECTED_DISTANCE = new BigDecimal("39.7720607289").setScale(SCALE);

    @Test
    public void GetLatLngTest() {
        LatLng latLng = googleLocationService.GetLatLng(POST_CODE);
        System.out.println(latLng.getLatitude());
        System.out.println(latLng.getLongitude());
        assertNotNull(latLng);
        assertThat(latLng, instanceOf(LatLng.class));
        assertThat(latLng.getLatitude(), Matchers.comparesEqualTo(EXPECTED_LATITUDE));
        assertThat(latLng.getLongitude(), Matchers.comparesEqualTo(EXPECTED_LONGITUDE));
    }

    @Test
    public void calculateDistanceTest() {
        BigDecimal distance = googleLocationService.calculateDistance(TN2_FROM_LATITUDE,
                TN2_FROM_LONGITUDE,
                TW6_FROM_LATITUDE, TW6_FROM_LONGITUDE);
        assertNotNull(distance);
        assertThat(distance, instanceOf(BigDecimal.class));
        assertThat(distance, Matchers.comparesEqualTo(TN2_TW6_EXPECTED_DISTANCE));
    }
}
