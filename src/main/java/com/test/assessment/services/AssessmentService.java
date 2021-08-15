package com.test.assessment.services;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.LatLng;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssessmentService implements IAssessmentService {
    Logger log = LoggerFactory.getLogger(AssessmentService.class);

    @Autowired
    private Environment env;

    @Override
    public DirectionsResult getDirection(String origin, String destination, String requestId) throws IOException, InterruptedException, ApiException {

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(env.getProperty("map.apiKey"))
                .build();

        DirectionsApiRequest request = DirectionsApi.getDirections(context, origin, destination);
        log.info("RequestId={} {}",requestId, "making call to direction API");

        try {
            return request.await();
        } catch (Exception e) {
            log.error("RequestId={}, errorMsg={}, errorDetail={}", requestId, "Error while making direction request, error: ", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<String> getCoordinates(String origin, String destination, String requestId) throws IOException, InterruptedException, ApiException {
        List<String> coordinatesInLatLng = new ArrayList<>();
        DirectionsResult directions = getDirection(origin, destination, requestId);
        DirectionsLeg leg = directions.routes[0].legs[0];
        coordinatesInLatLng.add(leg.startLocation.lat + "," + leg.startLocation.lng);
        DirectionsStep[] steps = leg.steps;

        for (DirectionsStep step: steps) {
            int pointsInInterval = Integer.parseInt(env.getProperty("pointsInSomeInterval"));
            long distanceInMeter = step.distance.inMeters;
            int numberOfPoints = (int) (distanceInMeter/pointsInInterval);
            if (numberOfPoints <= 1) {
                coordinatesInLatLng.add(step.endLocation.lat + "," + step.endLocation.lng);
            } else {
                List<LatLng> latLngs = step.polyline.decodePath();
                int numberOfCordinates = latLngs.size();
                if (numberOfCordinates <= numberOfPoints || numberOfCordinates <= pointsInInterval) {
                    coordinatesInLatLng.addAll(latLngs.stream().map(latLng -> latLng.lat + "," + latLng.lng).collect(Collectors.toList()));
                } else {
                    int nthElement = numberOfCordinates / pointsInInterval;
                    for (int i = 0; i < numberOfCordinates; i+=nthElement) {
                        LatLng latLng = latLngs.get(i);
                        coordinatesInLatLng.add(latLng.lat + "," + latLng.lng);
                    }
                }
            }
        }

        return coordinatesInLatLng;
    }
}
