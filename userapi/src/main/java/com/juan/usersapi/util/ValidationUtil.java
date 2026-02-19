package com.juan.usersapi.util;

import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern RFC_PATTERN =
            Pattern.compile("^[A-Z]{4}[0-9]{6}[A-Z0-9]{3}$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^(\\+\\d{1,3})?\\d{10}$");

    public static boolean isValidRFC(String rfc) {
        return rfc != null && RFC_PATTERN.matcher(rfc).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }
}
