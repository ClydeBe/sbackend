package com.thewheel.sawatu.shared.constant;

public class ApiEndpointsConstants {

    // Account
    public static final String ENDPOINT_ACCOUNT_GET_USER = "/user/{username}";
    public static final String ENDPOINT_ACCOUNT_REGISTER = "/register";
    public static final String ENDPOINT_ACCOUNT_VERIFY_EMAIL = "/verify-email";
    public static final String ENDPOINT_ACCOUNT_UPDATE = "/update";
    public static final String ENDPOINT_ACCOUNT_CREATE_USER = "/register";
    public static final String ENDPOINT_ACCOUNT_REFRESH_TOKEN = "/refreshtoken";

    // Appointment
    public static final String ENDPOINT_APPOINTMENT_GET_ALL = "/appointments/{username}";
    public static final String ENDPOINT_APPOINTMENT_GET = "/appointment/{id}";
    public static final String ENDPOINT_APPOINTMENT_CREATE = "/appointment";
    public static final String ENDPOINT_APPOINTMENT_DELETE = "/appointment";

    // Availability
    public static final String ENDPOINT_AVAILABILITY_GET_ALL = "/availabilities/{username}";
    public static final String ENDPOINT_AVAILABILITY_GET = "/availability/{id}";
    public static final String ENDPOINT_AVAILABILITY_CREATE = "/availability";
    public static final String ENDPOINT_AVAILABILITY_UPDATE = "/availability";
    public static final String ENDPOINT_AVAILABILITY_DELETE = "/availability";

    // Comment
    public static final String ENDPOINT_COMMENT_GET_ALL = "/post/{postId}/comments";
    public static final String ENDPOINT_COMMENT_GET = "/post/comment/{id}";
    public static final String ENDPOINT_COMMENT_CREATE = "/post/{postId}/comment";
    public static final String ENDPOINT_COMMENT_UPDATE = "/post/{postId}/comment";
    public static final String ENDPOINT_COMMENT_DELETE = "/comment";

    // Haircut
    public static final String ENDPOINT_HAIRCUT_GET_ALL = "/haircuts";
    public static final String ENDPOINT_HAIRCUT_GET = "/haircut/{id}";
    public static final String ENDPOINT_HAIRCUT_CREATE = "/haircut";
    public static final String ENDPOINT_HAIRCUT_UPDATE = "/haircut";
    public static final String ENDPOINT_HAIRCUT_DELETE = "/haircut";

    // HaircutOrder
    public static final String ENDPOINT_HAIRCUT_ORDER_GET_ALL = "/haircut_orders/{username}";
    public static final String ENDPOINT_HAIRCUT_ORDER_GET = "/haircut_order/{id}";
    public static final String ENDPOINT_HAIRCUT_ORDER_CREATE = "/haircut_order";
    public static final String ENDPOINT_HAIRCUT_ORDER_UPDATE = "/haircut_order";
    public static final String ENDPOINT_HAIRCUT_ORDER_DELETE = "/haircut_order";


    // Post
    public static final String ENDPOINT_POST_GET_ALL = "/posts/{username}";
    public static final String ENDPOINT_POST_GET_LAST_POST = "/latest_posts/";
    public static final String ENDPOINT_POST_GET = "/post/{id}";
    public static final String ENDPOINT_POST_CREATE = "/posts";
    public static final String ENDPOINT_POST_DELETE = "/posts";
    public static final String ENDPOINT_POST_UPDATE = "/posts";

    // Product
    public static final String ENDPOINT_PRODUCT_GET_ALL = "/products";
    public static final String ENDPOINT_PRODUCT_QUERY = "/products/search";
    public static final String ENDPOINT_PRODUCT_GET = "/product/{id}";
    public static final String ENDPOINT_PRODUCT_CREATE = "/product";
    public static final String ENDPOINT_PRODUCT_UPDATE = "/product";
    public static final String ENDPOINT_PRODUCT_DELETE = "/product";

    // Product order
    public static final String ENDPOINT_PRODUCT_ORDER_GET_ALL = "/product_orders/{username}";
    public static final String ENDPOINT_PRODUCT_ORDER_GET = "/product_order/{id}";
    public static final String ENDPOINT_PRODUCT_ORDER_CREATE = "/product_order";
    public static final String ENDPOINT_PRODUCT_ORDER_UPDATE = "/product_order";
    public static final String ENDPOINT_PRODUCT_ORDER_DELETE = "/product_order";

    // Reviews
    public static final String ENDPOINT_REVIEW_GET_ALL = "/reviews/{username}";
    public static final String ENDPOINT_REVIEW_GET = "/review/{id}";
    public static final String ENDPOINT_REVIEW_CREATE = "/review";
    public static final String ENDPOINT_REVIEW_UPDATE = "/review";
    public static final String ENDPOINT_REVIEW_DELETE = "/review";

    // Role
    public static final String ENDPOINT_ROLE_GET_ALL = "/roles";

    // Statistics
    public static final String ENDPOINT_STATISTICS_GET = "/statistic/{username}";


    public static final String MESSAGE_OK = "Ok";
    public static final String MESSAGE_CREATED = "Created";
    public static final String MESSAGE_NOT_FOUND = "Not found";

    public static final int CODE_OK = 200;
    public static final int CODE_CREATED = 201;
    public static final int CODE_NOT_FOUND = 404;

    private ApiEndpointsConstants() {}

}
