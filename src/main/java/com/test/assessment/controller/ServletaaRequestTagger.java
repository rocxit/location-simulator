package com.test.assessment.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import java.util.UUID;

@Component
public class ServletaaRequestTagger implements ServletRequestListener {

    @Override
    public void requestInitialized(final ServletRequestEvent sre) {
        String uniqueRequestId = UUID.randomUUID().toString();
        sre.getServletRequest().setAttribute("tag", uniqueRequestId);
    }

    public String getTag(final WebRequest request) {
        Object attribute = request.getAttribute("tag", RequestAttributes.SCOPE_REQUEST);
        return (attribute != null) ? attribute.toString() : java.util.UUID.randomUUID().toString();
    }
}
