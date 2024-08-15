package com.mrtkrkrt.creditapp.auth.filter.model.response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class RequestWrapper extends HttpServletRequestWrapper {

    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public Map<String, String> getAllHeaders() {
        Map<String, String> headers = new HashMap<>();
        Collections.list(getHeaderNames()).forEach(headerName -> headers.put(headerName, getHeader(headerName)));
        return headers;
    }

}
