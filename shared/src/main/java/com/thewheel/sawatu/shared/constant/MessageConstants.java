package com.thewheel.sawatu.shared.constant;

public class MessageConstants {

    public static final String ENTITY_NOT_FOUND = "No %s found with id '%s'";
    public static final String USER_NOT_FOUND = "No user found with username '%s'";

    public static final String APPOINTMENT = "APPOINTMENT";
    public static final String AVAILABILITY = "AVAILABILITY";
    public static final String COMMENT = "COMMENT";
    public static final String HAIRCUT = "HAIRCUT";
    public static final String HAIRCUT_ORDER = "HAIRCUT_ORDER";
    public static final String POST = "POST";
    public static final String PRODUCT = "PRODUCT";
    public static final String PRODUCT_ORDER = "PRODUCT_ORDER";
    public static final String REVIEWS = "REVIEWS";
    public static final String STATISTICS = "STATISTICS";

    public static final String STATISTICS_ARE_NOT_YET_COMPUTED = "Statistics for User[username=%s] are not yet " +
                                                                 "computed";

    public static final String NO_PASSWORD_FOUND = "No password found. Must provide password";
    public static final String CANNOT_SEND_EMAIL = "Cannot send email: %s";
    public static final String INVALID_TOKEN = "Unable to parse token";
    public static final String NO_TOKEN_PROVIDED = "No token provided";
    public static final String ACCOUNT_ACTIVATED = "Account activated";
    public static final String PRODUCT_QUERY_EXCEPTION = "Either query or both min and max must be provide";


    private MessageConstants() {}

}
