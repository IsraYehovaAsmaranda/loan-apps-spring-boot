package com.enigma.livecode_loan_app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    public static Date parseDate(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        Date tempDate = new Date();
        try {
            tempDate = sdf.parse(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return tempDate;
    }
}
