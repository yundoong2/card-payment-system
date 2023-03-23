package com.yoonhyeok.cardPayment.common.constant;

import java.util.regex.Pattern;

/**
 * 공통으로 쓰이는 static 변수 및 메소드 정의
 * @author cyh68
 * @since 2023-03-18
 */
public class Constants {
    public static int DATA_LENGTH = 446;
    public static String MASKING_CHAR = "*";
    public static String CARD_NUM_REGEX = "^[0-9]{10,16}$";
    public static String EXPIRY_MONTH_REGEX = "^(1[0-2]|[0-9])$";
    public static String CVC_REGEX = "^[0-9]{3}$";
    public static String INSTALL_REGEX = "^(1[0-2]|[0-9])$";
    public static String ID_REGEX = "^[a-zA-Z0-9]{20}$";
    public static String PAY_URL = "/payment";
    public static String CANCEL_URL = "/cancel";
    public static String FIND_URL = "/find";

    /**
     * 결제 금액 유효성 체크
     * @param price {@link Long}
     * @return boolean {@link Boolean}
     * @since 2023-03-18
     */
    public static boolean isValidPrice(Long price) {
        return price >= 100 && price <= 1000000000;
    }

    /**
     * 유효기간 유효성 체크
     * @param expiryDate
     * @return boolean {@link Boolean}
     * @since 2023-03-18
     */
    public static boolean isValidExpiryDate(String expiryDate) {
        return expiryDate.length() == 4 && Pattern.matches(Constants.EXPIRY_MONTH_REGEX, expiryDate.substring(0, 2));
    }
}
