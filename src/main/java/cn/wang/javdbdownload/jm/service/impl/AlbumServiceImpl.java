package cn.wang.javdbdownload.jm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.wang.javdbdownload.common.Constants;
import cn.wang.javdbdownload.inject.service.CustomServiceImpl;
import cn.wang.javdbdownload.jm.common.JmConstants;
import cn.wang.javdbdownload.jm.entity.pojo.Album;
import cn.wang.javdbdownload.jm.entity.pojo.AlbumChapter;
import cn.wang.javdbdownload.jm.entity.pojo.Theme;
import cn.wang.javdbdownload.jm.mapper.AlbumMapper;
import cn.wang.javdbdownload.jm.service.AlbumChapterService;
import cn.wang.javdbdownload.jm.service.AlbumDictService;
import cn.wang.javdbdownload.jm.service.AlbumService;
import cn.wang.javdbdownload.jm.service.ThemeService;
import cn.wang.javdbdownload.jm.util.JmHtmlUtil;
import cn.wang.javdbdownload.util.DownloadPicFromURL;
import cn.wang.javdbdownload.util.JsoupUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import oracle.jrockit.jfr.jdkevents.ThrowableTracer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 漫画信息 服务实现类
 * </p>
 *
 * @author wangbh
 * @since 2023-08-19
 */
@Service
@DS(Constants.DATASOURCE_JM)
public class AlbumServiceImpl extends CustomServiceImpl<AlbumMapper, Album> implements AlbumService {


    @Resource
    private AlbumDictService albumDictService;
    @Resource
    private ThemeService themeService;
    @Resource
    private AlbumChapterService albumChapterService;

    @Override
    public Album flushDataByWeb(Long id) {
        Album album = super.getById(id);
        if (album == null) {
            return null;
        }



        DownloadPicFromURL downloadPicFromURL = new DownloadPicFromURL();
        String albumPicLocalPath = this.getAlbumPicBasePath(album);

        Document document = JsoupUtil.getUrlDocument(String.join("", Arrays.asList(JmConstants.BASE_URL, album.getAlbumHref())));

        Elements panelBodyElements = document.getElementsByClass("panel-body");
        if (panelBodyElements.isEmpty()) {
            return album;
        }

        panelBodyElements.forEach(panelBodyElement -> {

            Element panelBodyPreElement = panelBodyElement.previousElementSibling();
            if (panelBodyPreElement == null || !panelBodyPreElement.attr("class").contains("panel-heading")) {
                return;
            }

            Element albumPhotoCoverElement = panelBodyElement.getElementById("album_photo_cover");
            Element firstThumbOverlay = albumPhotoCoverElement.getElementsByClass("thumb-overlay").get(0);
            Elements photoCoverImg = firstThumbOverlay.getElementsByTag("img");
            album.setAlbumPhotoCover(photoCoverImg.attr("src"));
            //下载封面
            try {
                String photoPicSuffix = FileUtil.getSuffix(downloadPicFromURL.getUrlImgName(album.getAlbumPhotoCover()));
                String photoCoverFileName = String.format(JmConstants.PHOTO_FILENAME, 0, photoPicSuffix);
                String filePath = downloadPicFromURL.downloadPicture(album.getAlbumPhotoCover(), albumPicLocalPath + photoCoverFileName);
                album.setPhotoCoverLocalDir(photoCoverFileName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            //
            Element infoElement = albumPhotoCoverElement.nextElementSibling();
            //章节列表
            albumChapterService.setChapterInfoByDocument(infoElement,album);
            //作品，登场人物，标签，作者等信息提取
            albumDictService.setDictByDocument(infoElement, album);

            //上架日期和更新日期
            infoElement.getElementsByAttributeValue("itemprop", "datePublished").forEach(datePublishedElement -> {
                String datePublishedStr = datePublishedElement.text();

                boolean isCreateDate = datePublishedStr.contains("上架");
                datePublishedStr = JmHtmlUtil.getTextFromColon(datePublishedStr);
                if (isCreateDate) {
                    //设置总页数
                    Element pageElement = datePublishedElement.parent().previousElementSibling();
                    album.setTotalPage(Integer.valueOf(JmHtmlUtil.getTextFromColon(pageElement.text())));
                    album.setDatePublishedCreate(LocalDateTimeUtil.parseDate(datePublishedStr));
                } else {
                    album.setDatePublishedUpdate(LocalDateTimeUtil.parseDate(datePublishedStr));
                    //观看次数
                    Element viewsNumberElement = datePublishedElement.nextElementSibling();
                    String viewsNumberText = viewsNumberElement.child(0).child(0).text();
                    album.setViewsNumber(JmHtmlUtil.caseStrNumberToInteger(viewsNumberText));
                    //收藏
                    Element likeElement = infoElement.getElementById(String.format("albim_likes_%s", album.getJmId()));
                    album.setCollectNumber(JmHtmlUtil.caseStrNumberToInteger(likeElement.text()));
                }
            });

        });

        super.updateById(album);
        return album;
    }

    @Override
    public String getAlbumPicBasePath(Album album) {
        Theme theme = themeService.getById(album.getThemeId());
        return String.format(JmConstants.ALBUM_PIC_INFO_PATH, theme.getName(),theme.getId(),replaceSpecialChar(album.getTitle()),album.getId());
    }

    private String replaceSpecialChar(String str){
        String[] specialArray = {"/","\\\\","\\！","\\[","\\]","\\*","\\?","\\|",":","\"","\n"};
        try {
            for (String s : specialArray) {
                str = str.replaceAll(s,"");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return str;
    }


    @Override
    public int myInsertOrUpdateBatch(List<Album> list) {
        list.forEach(e -> {
            String jmId = e.getJmId();
            Album dbAlbum = super.getOne(new LambdaQueryWrapper<Album>().eq(Album::getJmId, e.getJmId()));
            if (dbAlbum != null) {
                BeanUtils.copyProperties(e, dbAlbum, "id");
                super.updateById(dbAlbum);
            } else {
                try {
                    super.save(e);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        return list.size();
    }
}
