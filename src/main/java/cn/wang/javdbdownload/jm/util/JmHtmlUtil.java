package cn.wang.javdbdownload.jm.util;

import cn.hutool.core.util.NumberUtil;

public class JmHtmlUtil {

    public static String  getIdFormHref(String href){
        String[] split = href.split("/");
        for (String s : split) {
            if (NumberUtil.isInteger(s)) {
                return s;
            }
        }
        return null;

    }

}
