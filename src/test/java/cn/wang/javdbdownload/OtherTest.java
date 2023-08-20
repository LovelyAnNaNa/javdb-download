package cn.wang.javdbdownload;

import cn.hutool.core.util.NumberUtil;
import cn.wang.javdbdownload.util.DownloadPicFromURL;
import org.junit.jupiter.api.Test;

public class OtherTest {


    @Test
    public void test11(){
        DownloadPicFromURL downloadPicFromURL = new DownloadPicFromURL();

        downloadPicFromURL.downloadPictureByUrlName("https://cdn-msp2.jm-comic1.club/media/photos/480485/00035.webp","D:\\data\\jm\\test");

    }

}
