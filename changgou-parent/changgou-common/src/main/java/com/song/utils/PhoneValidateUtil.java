package com.song.utils;

/**
 * @author: mingsong.liu
 * @date: 2020-05-11 15:52
 * @description: 手机号校验工具
 */

public class PhoneValidateUtil {
    private static final String MOBILE_NO_REGEX = "^((13[0-9][0-9])|(15[0-9][0-9])|(17[0-9][0-9])|(18[0-9][0-9])|(147|145)[0-9])\\d{7}$";
    private static final String TAI_WAN_MOBLIL = "^09[0-9]{8}$";
    private static final String AUSTRALIA_MOBLIL = "^04[0-9]{8}$";
    private static final String MOBILE_ALL = "^((13[0-9][0-9])|(15[0-9][0-9])|(17[0-9][0-9])|(18[0-9][0-9])|(147|145|144|199|198|166)[0-9]|(09[0-9])|(04[0-9]))\\d{7}||$";
    private static final String SPECIAL_PLANE = "^(0\\d{2,3}-\\d{7,8})|\\d{7,12}";

    public PhoneValidateUtil() {
    }

    public static boolean isNormalMobile(String phoneNo) {
        return validate(phoneNo, "^((13[0-9][0-9])|(15[0-9][0-9])|(17[0-9][0-9])|(18[0-9][0-9])|(147|145)[0-9])\\d{7}$");
    }

    public static void main(String[] args) {
        System.out.println(isSpecialPlane("028-23213212"));
    }

    public static boolean isTaiwanMobile(String phoneNo) {
        return validate(phoneNo, "^09[0-9]{8}$");
    }

    public static boolean isMobile(String phoneNo) {
        return validate(phoneNo, "^((13[0-9][0-9])|(15[0-9][0-9])|(17[0-9][0-9])|(18[0-9][0-9])|(147|145|144|199|198|166)[0-9]|(09[0-9])|(04[0-9]))\\d{7}||$");
    }

    public static boolean isSpecialPlane(String phoneNo) {
        return validate(phoneNo, "^(0\\d{2,3}-\\d{7,8})|\\d{7,12}");
    }

    private static boolean validate(String phoneNo, String reg) {
        Boolean flag = null;
        if (phoneNo != null && phoneNo.length() != 0) {
            flag = phoneNo.matches(reg);
        } else {
            flag = false;
        }

        return flag;
    }

    public static boolean isAustraliaMobile(String phoneNo) {
        return validate(phoneNo, "^04[0-9]{8}$");
    }
}