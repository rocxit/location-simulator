package com.test.assessment.controller;

import com.google.maps.errors.ApiException;
import com.test.assessment.exceptions.MissingParameterException;
import com.test.assessment.services.IAssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;

@RestController
@RequestMapping("/assessment")
public class AssessmentController {

    Logger log = LoggerFactory.getLogger(AssessmentController.class);
    @Autowired
    private IAssessmentService assessmentService;

    @Autowired
    Environment environmen;

    @Autowired
    ServletaaRequestTagger servletaaRequestTagger;

    @GetMapping("/status")
    public String status(final WebRequest webRequest) {
        log.info(servletaaRequestTagger.getTag(webRequest), "Status");
        return "OK";
    }

    @GetMapping("/coordinates")
    public ResponseEntity<?> getCoordinate(@RequestParam String origin, @RequestParam String destination, final WebRequest webRequest)
            throws IOException, InterruptedException, ApiException {
            log.info("Request={}, RequestBody={}, ResponseCode={}, ResponseBody={}",
                webRequest,
                HttpStatus.OK);
            if (origin.isEmpty() || destination.isEmpty()) {
                log.error("Request={}, errorMessage={}", servletaaRequestTagger.getTag(webRequest), "Empty query parameter");
                throw new MissingParameterException("Missing one of origin or destination parameter");
            }

        return new ResponseEntity<>(assessmentService.getCoordinates(origin, destination, servletaaRequestTagger.getTag(webRequest)), HttpStatus.OK);
    }
}
