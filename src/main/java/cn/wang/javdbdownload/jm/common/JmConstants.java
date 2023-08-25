package cn.wang.javdbdownload.jm.common;

import java.io.File;

public class JmConstants {
    public static final String BASE_URL = "https://jm-comic1.club";

    public static final String PAGE_URL_PARAM = "&page=%d";

    public static final String PIC_BASE_PATH = "D:\\data\\jm";

    public static final String ALBUM_PIC_BASE_PATH = "\\album";

    /**
     * 格式化漫画存储路径 最后一个%s代表jmId
     * 示例D:\data\jm\album\themeName_themeId\albumTitle_albumId\
     * 第一个%s themeName ，第二个%s themeId,第三个%s albumTitle 第四个 albumName
     */
    public static final String ALBUM_PIC_INFO_PATH = String.format("%s%s%s%s_%s%s%s_%s%s",PIC_BASE_PATH , ALBUM_PIC_BASE_PATH ,File.separator,"%s","%s",File.separator,"%s","%s",File.separator);

    public static final String PHOTO_FILENAME = "%d.%s";



}
