package cn.wang.javdbdownload.jm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.wang.javdbdownload.common.Constants;
import cn.wang.javdbdownload.inject.service.CustomServiceImpl;
import cn.wang.javdbdownload.jm.common.JmConstants;
import cn.wang.javdbdownload.jm.entity.pojo.Album;
import cn.wang.javdbdownload.jm.entity.pojo.Theme;
import cn.wang.javdbdownload.jm.mapper.ThemeMapper;
import cn.wang.javdbdownload.jm.service.AlbumService;
import cn.wang.javdbdownload.jm.service.ThemeService;
import cn.wang.javdbdownload.jm.util.JmHtmlUtil;
import cn.wang.javdbdownload.util.JsoupUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangbh
 * @since 2023-08-19
 */
@Slf4j
@Service
@DS(Constants.DATASOURCE_JM)
public class ThemeServiceImpl extends CustomServiceImpl<ThemeMapper, Theme> implements ThemeService {

    @Resource
    private AlbumService albumService;

    private static final String THMEM_URI = "/theme";

    @Override
    @Transactional
    public List<Theme> initTheme() {
        Document document = JsoupUtil.getUrlDocument(JmConstants.BASE_URL + THMEM_URI);
        ArrayList<Theme> themeList = new ArrayList<>();
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

                                Theme theme = Theme.builder().name(aText)
                                        .href(aHref)
                                        .label(h4Text).build();
                                themeList.add(theme);
                            });
                        }
                    }
                });
            }
        });

        super.customSaveOrUpdateByUniqueCol(themeList,Theme::getName);

        return themeList;
    }

    @Override
    public List<Album> getAlbumByThemeId(Long id) {
        Theme theme = super.getById(id);
        if (theme == null){
            return null;
        }

        ArrayList<Album> allAlbumList = new ArrayList<>();
        AtomicInteger atomicPage = new AtomicInteger();
        boolean hasNextPage = true;

        while (hasNextPage){
            atomicPage.incrementAndGet();


            Document document = JsoupUtil.getUrlDocument(JmConstants.BASE_URL + theme.getHref() + String.format(JmConstants.PAGE_URL_PARAM,atomicPage.get()));
            Element rowElements = document.getElementById("wrapper").getElementsByClass("container").get(0).getElementsByClass("row").get(0);
            ArrayList<Album> tempAlbumList = new ArrayList<>();

            rowElements.forEach(rowElement -> {
                Elements a = rowElement.getElementsByTag("a");
                a.forEach(aElement -> {
                    String href = aElement.attr("href");
                    if (href.contains("/album")){
                        Elements imgElements = aElement.getElementsByTag("img");
                        if (!imgElements.isEmpty()){
                            Element coverImg = aElement.getElementsByTag("img").get(0);
                            String title = coverImg.attr("title");
                            Album album = Album.builder()
                                    .jmId(JmHtmlUtil.getIdFormHref(href))
                                    .albumHref(href)
                                    .title(title)
                                    .themeId(id)
                                    .build();

                            tempAlbumList.add(album);
                        }
                    }
                });
            });


            allAlbumList.addAll(tempAlbumList);
            new Thread(() -> {
                ArrayList<Album> threadAlbumList = new ArrayList<>(tempAlbumList);
                albumService.customSaveOrUpdateByUniqueCol(threadAlbumList,Album::getJmId);
            }).start();
            Elements prevnextElement = document.getElementsByClass("prevnext");
            hasNextPage = !prevnextElement.isEmpty();
        }


        return allAlbumList;
    }
}
