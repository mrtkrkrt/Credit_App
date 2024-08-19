package com.mrtkrkrt.creditapp.auth.filter.constants;

import java.util.List;

public class PathConstants {

    public static final List<String> publicPaths = List.of("/register", "/login", "/swagger-ui/index.html", "/swagger-ui.html", "/v3/api-docs",
        "/swagger");

    public static boolean isTokenRequired(String path) {
        return publicPaths.stream().anyMatch(path::contains);
    }

}
