package com.habeebcycle.marketplace.util;

public class ApplicationConstants {
    //REST ENDPOINTS
    public static final String API_PREFIX = "/api/v1";

    public static final String AUTH_ENDPOINTS = API_PREFIX + "/auth/**";
    public static final String AUTH_ENDPOINT = API_PREFIX + "/auth";
    public static final String ADMIN_ENDPOINTS = API_PREFIX + "/admin/**";
    public static final String ADMIN_ENDPOINT = API_PREFIX + "/admin";
    public static final String USER_ENDPOINTS = API_PREFIX + "/user/**";
    public static final String USER_ENDPOINT = API_PREFIX + "/user";
    public static final String UTIL_ENDPOINTS = API_PREFIX + "/util/**";
    public static final String UTIL_ENDPOINT = API_PREFIX + "/util";
    public static final String USER_AVAILABILITY_ENDPOINT = USER_ENDPOINT + "/checkUsernameAvailability";
    public static final String EMAIL_AVAILABILITY_ENDPOINT = USER_ENDPOINT + "/checkEmailAvailability";

    public static final String PRODUCT_CATEGORY_ENDPOINTS = API_PREFIX + "/product-category/**";
    public static final String PRODUCT_CATEGORY_ENDPOINT = API_PREFIX + "/product-category";
    public static final String PRODUCT_ENDPOINTS = API_PREFIX + "/product/**";
    public static final String PRODUCT_ENDPOINT = API_PREFIX + "/product";
    public static final String BRAND_ENDPOINTS = API_PREFIX + "/brand/**";
    public static final String BRAND_ENDPOINT = API_PREFIX + "/brand";
    public static final String BANNER_ENDPOINTS = API_PREFIX + "/banner/**";
    public static final String BANNER_ENDPOINT = API_PREFIX + "/banner";
    public static final String PROMOTION_ENDPOINTS = API_PREFIX + "/promotion/**";
    public static final String PROMOTION_ENDPOINT = API_PREFIX + "/promotion";
    public static final String STORE_ENDPOINTS = API_PREFIX + "/store/**";
    public static final String STORE_ENDPOINT = API_PREFIX + "/store";
    public static final String COLLECTION_ENDPOINTS = API_PREFIX + "/collection/**";
    public static final String COLLECTION_ENDPOINT = API_PREFIX + "/collection";
    public static final String POST_ENDPOINTS = API_PREFIX + "/post/**";
    public static final String POST_ENDPOINT = API_PREFIX + "/post";

    public static final String IMAGE_ENDPOINT = "/media/image/";
    public static final String IMAGE_ENDPOINTS = "/media/image/**";

    //GRAPHQL ENDPOINTS
    public static final String GRAPHIQL_ENDPOINT = "/graphiql";
    public static final String GRAPHIQL_ENDPOINTS = "/graphiql/**";
    public static final String GRAPHQL_ENDPOINT = "/graphql";
    public static final String GRAPHQL_ENDPOINTS = "/graphql/**";
    public static final String SUBSCRIPTION_ENDPOINT = "/subscriptions";

    //PUBLIC ENDPOINTS
    public static final String[] PUBLIC_ENDPOINTS = {"/",
        "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.js"};
}
