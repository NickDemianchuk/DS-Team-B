package com.kaniademianchuk.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil extends Date {
    public static final int DAILY = 1000 * 60 * 60 * 24;
    public static final int WEEKLY = DAILY * 7;
    public static final Integer MINUTE_IN_MILLISECONDS = 1000 * 60;
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DateUtil() {
    }

    public static Date add(Date date, int units, int numberOfUnits) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(units, numberOfUnits);
        return cal.getTime();
    }
}
