package vip.self.cliservice.other.util;

import vip.self.concommon.exception.NFException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class MyUtil {

    // 判断字符串是否全是字母
    public static Pattern pattern = Pattern.compile("[a-z]*");
    // 日期字符串模板，可以将日期字符串转日期对象
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");



    /**
     * 检查字符串数组是否含有空字符串或null值
     * 当该方法没有参数的时候，args不为null
     * @param args 字符串数组
     * @return 有返回true 没有返回false
     */
    public static boolean checkStrArr(String... args) {
        if (args.length == 0) throw new NFException(20001, "字符串数组长度为0");
        for (int i = 0; i < args.length; i++) {
            if (strIsEmpty(args[i])) return true;
        }
        return false;
    }


    /**
     * 判断是否是空字符串或null值
     * @param str 字符串
     * @return 是返回true 不是返回false
     */
    public static boolean strIsEmpty(String str) {
        if (str == null || "".equals(str) || "".equals(str.trim())) return true;
        return false;
    }

    /**
     * 判断字符串是否全是小写字母
     * @param str 字符串
     * @return 是返回true, 不是返回false
     */
    public static boolean matcherAlphabet(String str) {
        if (str == null) return false;
        return pattern.matcher(str).matches();
    }

    public static Date formatDateStr(String dateStr) throws ParseException {
        Date date = null;
        if (!strIsEmpty(dateStr)) date = sdf.parse(dateStr);
        return date;
    }

    public static String date2Str(Date date) {
        if (date == null) return null;
        String dataStr = sdf.format(date);
        return dataStr;
    }


}
