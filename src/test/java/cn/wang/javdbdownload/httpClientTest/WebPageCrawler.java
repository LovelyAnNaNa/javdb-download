package cn.wang.javdbdownload.httpClientTest;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Slf4j
public class WebPageCrawler {


    @Test
    public void testJsoup() throws Exception{

        Document document = Jsoup.connect("https://jm-comic1.club/theme/")
                .get();

        Elements documentElements = document.getElementsByClass("container");
        documentElements.forEach(documentElement -> {
            Elements rowElements = documentElement.getElementsByClass("row");
            if (!rowElements.isEmpty()){
                rowElements.forEach(rowElement -> {
                    Elements h4Elements = rowElement.getElementsByTag("h4");
                    if (!h4Elements.isEmpty()){
                        Element h4Element = h4Elements.get(0);
                        String h4Text = h4Element.text();
                        Elements ulElements = rowElement.getElementsByTag("ul");
                        if(!ulElements.isEmpty()){
                            Element ulElement = ulElements.get(0);
                            Elements aElements = ulElement.getElementsByTag("a");
                            aElements.forEach(aElement -> {
                                String aHref = aElement.attr("href");
                                String aText = aElement.text();
                                log.info("{},{},{}",h4Text,aText,aHref);
                            });
                        }
                    }
                });
            }
        });

    }

    @Test
    public void test() throws Exception {

        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://javdb521.com/actors/censored")

                ; // 设置要抓取的网页URL

        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String html = EntityUtils.toString(entity, "UTF-8");

            // 在这里可以对网页内容进行处理
            System.out.println(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}