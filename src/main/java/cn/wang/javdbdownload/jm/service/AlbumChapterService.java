package cn.wang.javdbdownload.jm.service;

import cn.wang.javdbdownload.inject.service.CustomService;
import cn.wang.javdbdownload.jm.entity.pojo.Album;
import cn.wang.javdbdownload.jm.entity.pojo.AlbumChapter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 章节信息表 服务类
 * </p>
 *
 * @author wangbh
 * @since 2023-08-20
 */
public interface AlbumChapterService extends CustomService<AlbumChapter> {

    boolean removeByEntity(AlbumChapter chapter);

    /**
     * 页面节点上的内容章节初始化
     * @param infoElements
     * @param album
     * @return
     */
    @Transactional
    List<AlbumChapter> setChapterInfoByDocument(Element infoElements, Album album);
}
