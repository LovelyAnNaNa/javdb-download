package cn.wang.javdbdownload.jm.service;

import cn.wang.javdbdownload.inject.service.CustomService;
import cn.wang.javdbdownload.jm.entity.pojo.Album;
import cn.wang.javdbdownload.jm.entity.pojo.AlbumDict;
import org.jsoup.nodes.Element;

import java.util.List;

/**
 * <p>
 * 漫画详情页多个标签的索引 服务类
 * </p>
 *
 * @author wangbh
 * @since 2023-08-19
 */
public interface AlbumDictService extends CustomService<AlbumDict> {

    boolean removeByEntity(AlbumDict albumDict);

    /**
     * 根据节点信息获取动漫对应的信息
     * @param infoElement
     * @param album
     * @return
     */
    List<AlbumDict> setDictByDocument(Element infoElement, Album album);

}
