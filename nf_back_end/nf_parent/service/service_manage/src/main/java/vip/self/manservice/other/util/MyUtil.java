package vip.self.manservice.other.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import vip.self.concommon.exception.NFException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class MyUtil {

    // 判断字符串是否全是字母
    public static Pattern pattern = Pattern.compile("[a-z]*");

    // 日期字符串模板，可以将日期字符串转日期对象
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


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

    /**
     * 输入两个日期字符串返回两个日期
     * 字符串的格式 yyyy-MM-dd HH:mm:ss
     * @param preDate 第一个日期字符
     * @param sufDate 第二个日期字符串
     * @return 日期数组
     * @throws ParseException 字符串格式不正确
     */
    public static Date[] formatPreSufDateStr(String preDate, String sufDate) throws ParseException {
        Date pre = null;
        Date suf = null;
        if (!strIsEmpty(preDate)) pre = sdf.parse(preDate);
        if (!strIsEmpty(sufDate)) suf = sdf.parse(sufDate);
        Date[] dates = { pre, suf };
        return dates;
    }

    /**
     * 该方法完全没必要，只是巧合有两块代码相同，所以才提取出来
     * @param state 参数
     * @param wrapper 判断语句对象
     */
    public static <T> void queryState(Integer state, QueryWrapper<T> wrapper) {
        if (state == 0 || state == 1 || state == 2 || state == 3)
            wrapper.eq("state", state);
    }

    /**
     * args为两个参数，先后顺序时 文章id，msg
     * msg可以没有
     * @param args
     * @return
     */
    public static String jointMessageText(String... args) {
        boolean b = checkStrArr(args);
        if (b) return null;
        StringBuilder sb = new StringBuilder();
        sb.append("你的<a href=\"");
        if (args.length == 1) {
            sb.append("http://localhost:7770/article/"); // TODO
        } else if (args.length == 2) {
            sb.append("http://localhost:7770/preview/"); // TODO
        }
        sb.append(args[0]);
        if (args.length == 1) { // pass
            sb.append("\">文章</a>审核通过了。");
        } else if (args.length == 2) { // nopass
            sb.append("\">文章</a>审核未通过。原因：");
            sb.append(args[1]);
            sb.append("。");
        }
        return sb.toString();
    }

}
