package cn.wang.javdbdownload.jm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.wang.javdbdownload.common.Constants;
import cn.wang.javdbdownload.inject.service.CustomServiceImpl;
import cn.wang.javdbdownload.jm.entity.pojo.Album;
import cn.wang.javdbdownload.jm.mapper.AlbumChapterMapper;
import cn.wang.javdbdownload.jm.entity.pojo.AlbumChapter;
import cn.wang.javdbdownload.jm.service.AlbumChapterService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 章节信息表 服务实现类
 * </p>
 *
 * @author wangbh
 * @since 2023-08-20
 */
@Service
@DS(Constants.DATASOURCE_JM)
public class AlbumChapterServiceImpl extends CustomServiceImpl<AlbumChapterMapper, AlbumChapter> implements AlbumChapterService {


    @Override
    public boolean removeByEntity(AlbumChapter chapter) {
        LambdaUpdateWrapper<AlbumChapter> updateWrapper = new LambdaUpdateWrapper<>();

        if (chapter.getAlbumId() != null) {
            updateWrapper.eq(AlbumChapter::getAlbumId, chapter.getAlbumId());
        }

        if (!updateWrapper.isEmptyOfWhere()) {
            return super.remove(updateWrapper);
        }

        return Boolean.FALSE;
    }

    @Override
    @Transactional
    public List<AlbumChapter> setChapterInfoByDocument(Element infoElement, Album album) {
        this.removeByEntity(AlbumChapter.builder().albumId(album.getId()).build());

        ArrayList<AlbumChapter> documentChapterList = new ArrayList<>();

        //章节列表
        Elements ulElemetns = infoElement.getElementsByTag("ul");
        List<AlbumChapter> chapterList = new ArrayList<>();
        //循环所有的ul列表，获取阅读的url列表
        ulElemetns.forEach(e -> {
            Element previousElementSibling = e.parent();
            if (previousElementSibling != null && previousElementSibling.attr("class").contains("episode")) {
                //所有章节信息
                Elements aElements = e.getElementsByTag("a");
                aElements.forEach(aElement -> {
                    documentChapterList.add(castAElementToEntity(aElement, album));
                });
            }
        });
        //如果只有一章
        if (CollectionUtil.isEmpty(chapterList)) {
            infoElement.children().forEach(infoBlockElement -> {
                Elements aElements = infoBlockElement.getElementsByTag("a");
                aElements.forEach(aElement -> {
                    if (aElement.text().contains("开始阅读")) {
                        documentChapterList.add(AlbumChapter.builder()
                                .albumId(album.getId())
                                .name(aElement.text())
                                .num(1)
                                .href(aElement.attr("href")).build());
                    }
                });
            });
        }


        if (CollectionUtil.isNotEmpty(documentChapterList)) {
            try {
                super.customSaveOrUpdateBatch(documentChapterList);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        return null;
    }

    public AlbumChapter castAElementToEntity(Element aElement, Album album) {
        AlbumChapter.AlbumChapterBuilder chapterBuilder = AlbumChapter.builder();

        String chapterHref = aElement.attr("href");
        String chapterText = aElement.child(0).text();
        String[] textSplit = chapterText.split(" ");
        String numberStr = textSplit[0];
        numberStr = numberStr.substring(1, numberStr.length() - 1);
        chapterBuilder.num(Integer.valueOf(numberStr));
        chapterBuilder.name(textSplit[1]);
        chapterBuilder.href(chapterHref);
        chapterBuilder.albumId(album.getId());

        return chapterBuilder.build();
    }

}
