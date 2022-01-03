package com.thewheel.sawatu.constants;

import com.thewheel.sawatu.Role;

public class TestConstants {

    private static final String USERNAME = "USER";
    public static final String USERNAME_1 = USERNAME + "_1";
    public static final String USERNAME_2 = USERNAME + "_2";
    public static final String USERNAME_3 = USERNAME + "_3";
    public static final String USERNAME_4 = USERNAME + "_4";

    public static final Long ID_1 = 1L;
    public static final Long ID_2 = 2L;
    public static final Long ID_3 = 3L;
    public static final Long ID_4 = 4L;
    public static final Long ID_5 = 5L;

    private static final String EMAIL = "USER_%s@SAWATU.COM";
    public static final String EMAIL_1 = String.format(EMAIL, "1");
    public static final String EMAIL_2 = String.format(EMAIL, "2");
    public static final String EMAIL_3 = String.format(EMAIL, "3");
    public static final String EMAIL_4 = String.format(EMAIL, "4");

    private static final String PASSWORD = "#SAWATU@PASSWORD_";
    public static final String PASSWORD_1 = "#SAWATU@PASSWORD_1";
    public static final String PASSWORD_2 = "#SAWATU@PASSWORD_2";
    public static final String PASSWORD_3 = "#SAWATU@PASSWORD_3";
    public static final String PASSWORD_4 = "#SAWATU@PASSWORD_4";

    private static final String AVAILABILITY = "AVAILABILITY_";
    public static final String AVAILABILITY_1 = AVAILABILITY + 1;
    public static final String AVAILABILITY_2 = AVAILABILITY + 2;

    private static final String BODY = "BODY_";
    public static final String BODY_1 = BODY + 1;
    public static final String BODY_2 = BODY + 2;

    private static final String PHOTO = "PHOTO_";
    public static final String PHOTO_1 = PHOTO + 1;
    public static final String PHOTO_2 = PHOTO + 2;

    private final static String LABEL = "LABEL_";
    public final static String LABEL_1 = LABEL + 1;
    public final static String LABEL_2 = LABEL + 2;

    public final static Role ROLE_USER =Role.USER;
    public final static Role ROLE_ADMIN =Role.ADMIN;
    public final static Role ROLE_STAFF =Role.STAFF;
    public final static Role ROLE_VENDOR =Role.VENDOR;

    public final static short DURATION_1 = 10;
    public final static short DURATION_2 = 20;

    public final static float PRICE_1 = 10;
    public final static float PRICE_2 = 20;

    public final static float VAT_RATIO_1 = 0.1925f;
    public final static float VAT_RATIO_2 = 0.2225f;

    private final static String DESCRIPTION = "DESCRIPTION_";
    public final static String DESCRIPTION_1 = DESCRIPTION + 1;
    public final static String DESCRIPTION_2 = DESCRIPTION + 2;

    private final static String STRING_CONSTANT = "STRING_CONSTANT_";
    public final static String STRING_CONSTANT_1 = STRING_CONSTANT + 1;
    public final static String STRING_CONSTANT_2 = STRING_CONSTANT + 2;
    public final static String STRING_CONSTANT_3 = STRING_CONSTANT + 3;
    public final static String STRING_CONSTANT_4 = STRING_CONSTANT + 4;

    public static final short NUMERIC_CONSTANT_1 = 1;
    public static final short NUMERIC_CONSTANT_2 = 2;

    private TestConstants() {}
}
