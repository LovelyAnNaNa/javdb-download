package cn.wang.javdbdownload.util;

import cn.hutool.core.util.URLUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author wangbh
 * 获取网页元素
 */
public class JsoupUtil {


    public static Document getUrlDocument(String url){
        Document document = null;
        try {
            url = URLUtil.encode(url);
             document = Jsoup.connect(url)
                    .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return document;
    }

}
