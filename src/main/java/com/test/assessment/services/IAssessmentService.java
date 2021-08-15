package com.test.assessment.services;

import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;

import java.io.IOException;
import java.util.List;


public interface IAssessmentService {
    DirectionsResult getDirection(String origin, String destination, String requestId) throws IOException, InterruptedException, ApiException;
    List<String> getCoordinates(String origin, String destination, String requestId) throws IOException, InterruptedException, ApiException;
}
