package com.blubank.doctorappointment.base;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String message(String code, String... params) {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource.getMessage(code, params, Locale.getDefault());
    }

    public static Date addMinutesToDate(Date date, int minutes) {
        return addToDate(date, Calendar.MINUTE, minutes);
    }

    public static Date addDaysToDate(Date date, int days) {
        return addToDate(date, Calendar.DATE, days);
    }

    private static Date addToDate(Date date, int field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, amount);
        return cal.getTime();
    }

    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
