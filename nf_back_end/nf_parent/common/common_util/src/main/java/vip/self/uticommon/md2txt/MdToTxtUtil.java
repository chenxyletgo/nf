package vip.self.uticommon.md2txt;

import org.pegdown.PegDownProcessor;

public class MdToTxtUtil {

    public static PegDownProcessor pdp = new PegDownProcessor(Integer.MAX_VALUE);

    /**
     * html文本去除标签
     * @param html html文本
     * @return 去除标签后的文本
     */
    private static String delHtmlTags(String html) {
        String scriptRegex="<script[^>]*?>[\\s\\S]*?<\\/script>";
        //定义style的正则表达式，去除style样式，防止css代码过多时只截取到css样式代码
        String styleRegex="<style[^>]*?>[\\s\\S]*?<\\/style>";
        //定义HTML标签的正则表达式，去除标签，只提取文字内容
        String htmlRegex="<[^>]+>";
        //定义空格,回车,换行符,制表符
        String spaceRegex = "\\s*|\t|\r|\n";
        // 过滤script标签
        html = html.replaceAll(scriptRegex, "");
        // 过滤style标签
        html = html.replaceAll(styleRegex, "");
        // 过滤html标签
        html = html.replaceAll(htmlRegex, "");
        // 过滤空格等
        html = html.replaceAll(spaceRegex, "");
        return html.trim(); // 返回文本字符串
    }

    /**
     * html文本转为普通文本
     * @param html html文本
     * @return 普通文本
     */
    private static String getTextFromHtml(String html){
        //去除html标签
        html = delHtmlTags(html);
        //去除空格" "
        html = html.replaceAll(" ","");
        return html;
    }

    /**
     * markdown文本转普通文本
     * @param md markdown文本
     * @return 普通文本
     */
    public static String mdToText(String md){
        String html = pdp.markdownToHtml(md);
        return getTextFromHtml(html);
    }
}
