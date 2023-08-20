package cn.wang.javdbdownload.jm.util;

import cn.hutool.core.util.NumberUtil;

public class JmHtmlUtil {

    /**
     * 获取rest接口的id
     *
     * @param href
     * @return
     */
    public static String getIdFormHref(String href) {
        String[] split = href.split("/");
        for (String s : split) {
            if (NumberUtil.isInteger(s)) {
                return s;
            }
        }
        return null;
    }

    /**
     * 获取冒号后的内容
     *
     * @param text
     * @return
     */
    public static String getTextFromColon(String text) {
        text = text.replaceAll("：", ":");
        text = text.replaceAll("：", ":");
        return text.substring(text.indexOf(":") + 1).trim();
    }


    public static Integer caseStrNumberToInteger(String strNumber) {
        Integer i = null;

        Integer numberUnit = null;
        switch (strNumber.substring(strNumber.length() - 1)) {
            case "K":
                numberUnit = 1000;
                break;

            case "M":
                numberUnit = 100000000;
                break;

            default:
        }

        if (numberUnit != null) {
            Integer num = Integer.valueOf(strNumber.substring(0, strNumber.length() - 1));
            i = num * numberUnit;
        }

        return i;
    }
}
