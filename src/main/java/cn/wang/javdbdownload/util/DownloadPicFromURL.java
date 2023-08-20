package cn.wang.javdbdownload.util;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

@Slf4j
public class DownloadPicFromURL {

    /**
     * 获取图片路径中图片的名称
     */
    public String getUrlImgName(String url) {
        url = url.substring(0, url.lastIndexOf("?"));
        String[] split = url.split("/");
        return split[split.length - 1];
    }

    public String downloadPictureByUrlName(String picUrl, String path) throws Exception {
        String urlImgName = this.getUrlImgName(picUrl);
        path = path + File.separator + urlImgName;
        return this.downloadPicture(picUrl, path);
    }

    //链接url下载图片
    public String downloadPicture(String picUrl, String path) throws Exception {
        URL url = null;
        try {
            this.initPathDir(path);

            url = new URL(picUrl);
            URLConnection uc = url.openConnection();
            uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            DataInputStream dataInputStream = new DataInputStream(uc.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
            output.close();

            log.info("图片 {} 路径 {} 下载成功!", picUrl, path);

        } catch (Exception e) {
            e.printStackTrace();
            String errMsg = String.format("图片 %s 路径 %s 下载失败!", picUrl, path);
            log.info(errMsg);
            throw new Exception(errMsg);
        }

        return path;
    }

    public void initPathDir(String path) {

        path = path.replaceAll("\\\\", "/");

        String[] split = path.split("/");
        File dirFile;
        if (split[split.length - 1].contains(".")) {
            split[split.length - 1] = "";
            dirFile = new File(String.join("/", split));
        } else {
            dirFile = new File(path);
        }

        dirFile.mkdirs();

    }
}