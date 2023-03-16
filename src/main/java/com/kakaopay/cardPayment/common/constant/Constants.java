package com.kakaopay.cardPayment.common.constant;

public class Constants {
    public static int DATA_LENGTH = 446;
    public static String MASKING_CHAR = "*";
    public static String CARD_NUM_REGEX = "^[0-9]{10,16}$";
    public static String EXPIRY_REGEX = "^[0-9]{4}$";
    public static String CVC_REGEX = "^[0-9]{3}$";
    public static String INSTALL_REGEX = "^[0-9]{1,2}$";
    public static String PRICE_REGEX = "^[0-9]{3,10}$";
    public static String ID_REGEX = "^[a-zA-Z0-9]{20}$";

}
