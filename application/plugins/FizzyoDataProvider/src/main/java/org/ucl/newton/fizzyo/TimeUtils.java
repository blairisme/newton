package org.ucl.newton.fizzyo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Instances of this class provide function of conversion between java Date and Unix time stamp.
 *
 * @author Xiaolong Chen
 */
public class TimeUtils {
    private static Logger logger = LoggerFactory.getLogger(TimeUtils.class);
    private TimeUtils(){}
    public static String timeToUnixStamp(String dateStr){
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        }catch (ParseException e){
            logger.error("Fail to parse the date string into unix stamp");
        }
        return "1470744743";
    }
    public static String unixStampToTime(String timeStamp){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long timestamp = Long.parseLong(timeStamp)*1000;
        String date = sdf.format(new Date(timestamp));
        return date;
    }
}
