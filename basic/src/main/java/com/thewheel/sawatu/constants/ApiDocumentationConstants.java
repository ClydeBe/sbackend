package com.thewheel.sawatu.constants;

public class ApiDocumentationConstants {

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";


    public static final String OPERATION_SUCCESS = "Successful operation";
    public static final String OPERATION_ERROR = "An error occurred";

    // Account
    public static final String ACCOUNT_GET_USER = "Get user by username";
    public static final String ACCOUNT_UPDATE_USER = "Update user";
    public static final String ACCOUNT_CREATE_USER = "Create a new user";
    public static final String ACCOUNT_VERIFY_EMAIL = "Verify email and activate account";
    public static final String ACCOUNT_REFRESH_TOKEN = "Get new access token";

    public static final String ACCOUNT_REFRESH_TOKEN_NOTE = "Return a new access token if the refresh token is valid";
    public static final String ACCOUNT_UPDATE_USER_NOTE = "Return the updated user";
    public static final String ACCOUNT_GET_USER_NOTE = "Return the user associated with the given id (username)";
    public static final String ACCOUNT_CREATE_USER_NOTE = "Receive a JSON at least containing email, username and " +
                                                          "password. If isActive value is set, it will be ignored " +
                                                          "since every new user must validate email before being " +
                                                          "active";
    public static final String ACCOUNT_VERIFY_EMAIL_NOTE = "Verify token sent by email. The token must be sent as a " +
                                                           "get parameter. If it's valid, the account will be " +
                                                           "activated and email marked as verified";


    // Appointment
    public static final String APPOINTMENT_GET_ALL = "Get user's appointments";
    public static final String APPOINTMENT_GET = "Get user's appointment";
    public static final String APPOINTMENT_CREATE = "Create new appointment";
    public static final String APPOINTMENT_DELETE = "Delete user's appointment";

    public static final String APPOINTMENT_GET_ALL_NOTE = "Returns page of appointment";
    public static final String APPOINTMENT_GET_NOTE = "Returns an appointment";
    public static final String APPOINTMENT_CREATE_NOTE = "Returns the created appointment";

    // Availability
    public static final String AVAILABILITY_GET_ALL = "Get user's availabilities";
    public static final String AVAILABILITY_GET = "Get user's availability";
    public static final String AVAILABILITY_CREATE = "Create availability";
    public static final String AVAILABILITY_UPDATE = "Update availability";
    public static final String AVAILABILITY_DELETE = "Delete availability";

    public static final String AVAILABILITY_GET_ALL_NOTE = "Returns page of availabilities";
    public static final String AVAILABILITY_GET_NOTE = "Returns user's availability";
    public static final String AVAILABILITY_CREATE_NOTE = "Returns created availability";
    public static final String AVAILABILITY_UPDATE_NOTE = "Returns updated availability";

    // Comment
    public static final String COMMENT_GET_ALL = "Get post's comments";
    public static final String COMMENT_GET = "Get a specific comment";
    public static final String COMMENT_CREATE = "Create comment";
    public static final String COMMENT_DELETE = "Delete comment";
    public static final String COMMENT_UPDATE = "Update comment";

    public static final String COMMENT_GET_ALL_NOTE = "Returns page of comments";
    public static final String COMMENT_GET_NOTE = "Returns associated comment";
    public static final String COMMENT_CREATE_NOTE = "Returns created comment";
    public static final String COMMENT_UPDATE_NOTE = "Returns updated comment";

    // Haircut
    public static final String HAIRCUT_GET_ALL = "Get all haircuts";
    public static final String HAIRCUT_GET = "Get an haircut";
    public static final String HAIRCUT_CREATE = "Create an haircut";
    public static final String HAIRCUT_UPDATE = "Modify an haircut";
    public static final String HAIRCUT_DELETE = "Delete an haircut";

    public static final String HAIRCUT_GET_ALL_NOTE = "Returns page of haircuts";
    public static final String HAIRCUT_GET_NOTE = "Returns an haircut";
    public static final String HAIRCUT_CREATE_NOTE = "Returns created haircut";
    public static final String HAIRCUT_UPDATE_NOTE = "Returns updated haircut";

    // Haircut order
    public static final String HAIRCUT_ORDER_GET_ALL = "Get all user's haircut orders";
    public static final String HAIRCUT_ORDER_GET = "Get an haircut order";
    public static final String HAIRCUT_ORDER_CREATE = "Create an haircut order";
    public static final String HAIRCUT_ORDER_UPDATE = "Modify an haircut order";
    public static final String HAIRCUT_ORDER_DELETE = "Delete an haircut order";

    public static final String HAIRCUT_ORDER_GET_ALL_NOTE = "Returns page of haircut orders";
    public static final String HAIRCUT_ORDER_GET_NOTE = "Returns an haircut order";
    public static final String HAIRCUT_ORDER_CREATE_NOTE = "Returns created haircut order";
    public static final String HAIRCUT_ORDER_UPDATE_NOTE = "Returns updated haircut order";

    // Post
    public static final String POST_GET_ALL = "Get all posts";
    public static final String POST_GET = "Get a post";
    public static final String POST_GET_LAST = "Get last post";
    public static final String POST_CREATE = "Create a post";
    public static final String POST_UPDATE = "Modify a post";
    public static final String POST_DELETE = "Delete a post";

    public static final String POST_GET_ALL_NOTE = "Returns page of posts";
    public static final String POST_GET_NOTE = "Returns a post";
    public static final String POST_GET_LAST_NOTE = "Return the three latest post";
    public static final String POST_CREATE_NOTE = "Returns created post";
    public static final String POST_UPDATE_NOTE = "Returns updated post";

    // Product
    public static final String PRODUCT_GET_ALL = "Get all products";
    public static final String PRODUCT_QUERY = "Find products";
    public static final String PRODUCT_GET = "Get a product";
    public static final String PRODUCT_CREATE = "Create a product";
    public static final String PRODUCT_UPDATE = "Modify a product";
    public static final String PRODUCT_DELETE = "Delete a product";

    public static final String PRODUCT_GET_ALL_NOTE = "Returns page of products";
    public static final String PRODUCT_QUERY_NOTE = "Returns found product";
    public static final String PRODUCT_GET_NOTE = "Returns a product";
    public static final String PRODUCT_CREATE_NOTE = "Returns created product";
    public static final String PRODUCT_UPDATE_NOTE = "Returns updated product";

    // Product order
    public static final String PRODUCT_ORDER_GET_ALL = "Get user's order";
    public static final String PRODUCT_ORDER_GET = "Get an order";
    public static final String PRODUCT_ORDER_CREATE = "Create an order";
    public static final String PRODUCT_ORDER_UPDATE = "Modify an order";
    public static final String PRODUCT_ORDER_DELETE = "Delete an order";

    public static final String PRODUCT_ORDER_GET_ALL_NOTE = "Returns page of user's orders";
    public static final String PRODUCT_ORDER_GET_NOTE = "Returns an order";
    public static final String PRODUCT_ORDER_CREATE_NOTE = "Returns created order";
    public static final String PRODUCT_ORDER_UPDATE_NOTE = "Returns updated order";

    // Reviews
    public static final String REVIEW_GET_ALL = "Get user's reviews";
    public static final String REVIEW_GET = "Get a review";
    public static final String REVIEW_CREATE = "Create a review";
    public static final String REVIEW_UPDATE = "Modify a review";
    public static final String REVIEW_DELETE = "Delete a review";

    public static final String REVIEW_GET_ALL_NOTE = "Returns page of user's reviews";
    public static final String REVIEW_GET_NOTE = "Returns a review";
    public static final String REVIEW_CREATE_NOTE = "Returns created review";
    public static final String REVIEW_UPDATE_NOTE = "Returns updated review";
    public static final String REVIEW_DELETE_NOTE = "Returns deleted review";


    // Role
    public static final String ROLE_GET = "Get all roles";

    public static final String ROLE_GET_NOTE = "Return application roles";

    // Statistic
    public static final String STATISTICS_GET = "Get user's statistics";

    public static final String STATISTICS_GET_NOTE = "Return user's statistics";

    private ApiDocumentationConstants() {}
}
