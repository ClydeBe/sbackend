package com.thewheel.sawatu.constants;

import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final String TOKEN_GRANTED_AUTHORITY_NAME = "role";

    public static final List<String> PUBLIC_ENDPOINTS_EXACT = Arrays.asList(
            "/",
            "/login",
            "/account/refreshtoken",
            "/account/register",
            "/account/verify-token",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html");

    public static final List<String> PUBLIC_ENDPOINTS_PATTERN = Arrays.asList(
            "swagger",
            "v2/api-docs",
            "v3/api-docs",
            "webjars",
            "product");

    private Constants() {}
}
