package cn.wang.javdbdownload.jm.common;

import java.io.File;

public class JmConstants {
    public static final String BASE_URL = "https://jm-comic1.club";

    public static final String PAGE_URL_PARAM = "&page=%d";

    public static final String PIC_BASE_PATH = "D:\\data\\jm";

    public static final String ALBUM_PIC_BASE_PATH = "\\album";

    /**
     * 格式化漫画存储路径 最后一个%s代表jmId
     * 示例D:\data\jm\album\255662\
     */
    public static final String ALBUM_PIC_INFO_PATH = String.format("%s%s%s",PIC_BASE_PATH , ALBUM_PIC_BASE_PATH , File.separator) + "%s" + File.separator;

    public static final String PHOTO_FILENAME = "%d.%s";



}
