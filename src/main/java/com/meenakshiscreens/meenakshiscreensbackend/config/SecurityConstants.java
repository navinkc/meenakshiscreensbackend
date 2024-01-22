package com.meenakshiscreens.meenakshiscreensbackend.config;

public class SecurityConstants {
    public static final String SECRET_KEY = "fL7;hF8@gH5yM8!fU4-e";
    public static final int TOKEN_EXPIRATION = 7200000; // 7200000 milliseconds = 2 hours
    public static final String BEARER = "Bearer "; // "Authorization": "Bearer " + Token
    public static final String AUTHORIZATION = "Authorization"; // "Authorization": "Bearer .."

    public static final String API_PATH = "/api/private/v1"; // common api path

    public static final String API_PATH_ALL = API_PATH + "/**"; // common api path
    public static final String REGISTER_PATH = API_PATH + "/users/register"; // Public path clients used to register user
}
