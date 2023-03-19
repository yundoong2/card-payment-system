package com.kakaopay.cardPayment.common.constant;

import java.util.regex.Pattern;

public class Constants {
    public static int DATA_LENGTH = 446;
    public static String MASKING_CHAR = "*";
    public static String CARD_NUM_REGEX = "^[0-9]{10,16}$";
    public static String EXPIRY_MONTH_REGEX = "^(1[0-2]|[0-9])$";
    public static String CVC_REGEX = "^[0-9]{3}$";
    public static String INSTALL_REGEX = "^(1[0-2]|[0-9])$";
    public static String ID_REGEX = "^[a-zA-Z0-9]{20}$";

    public static boolean isValidPrice(Long price) {
        return price >= 100 && price <= 1000000000;
    }

    public static boolean isValidExpiryDate(String expiryDate) {
        return Pattern.matches(Constants.EXPIRY_MONTH_REGEX, expiryDate.substring(0, 2));
    }
}
