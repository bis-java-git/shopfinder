package me.bis.dbshop.service.google;

import me.bis.dbshop.domain.google.LatLng;

import java.math.BigDecimal;

public interface  GoogleLocatorService {

    LatLng GetLatLng(String postCode) throws GeoLocatorServiceException;

    BigDecimal calculateDistance(BigDecimal latitudeFrom,
                                        BigDecimal longitudeFrom,
                                        BigDecimal latitudeTo,
                                        BigDecimal longitudeTo);
}
