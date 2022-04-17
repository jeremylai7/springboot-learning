package com.jeremy.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: laizc
 * @date: created in 2022/4/16
 * @desc:
 **/
public class DateUtil {

    public static String dateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
