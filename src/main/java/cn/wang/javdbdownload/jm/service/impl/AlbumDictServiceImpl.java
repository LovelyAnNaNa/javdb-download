package cn.wang.javdbdownload.jm.service.impl;

import cn.wang.javdbdownload.common.Constants;
import cn.wang.javdbdownload.inject.service.CustomService;
import cn.wang.javdbdownload.inject.service.CustomServiceImpl;
import cn.wang.javdbdownload.jm.common.emum.AlbumDictTypeEmun;
import cn.wang.javdbdownload.jm.entity.pojo.Album;
import cn.wang.javdbdownload.jm.entity.pojo.AlbumDict;
import cn.wang.javdbdownload.jm.mapper.AlbumDictMapper;
import cn.wang.javdbdownload.jm.service.AlbumDictService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 漫画详情页多个标签的索引 服务实现类
 * </p>
 *
 * @author wangbh
 * @since 2023-08-19
 */
@Service
@DS(Constants.DATASOURCE_JM)
public class AlbumDictServiceImpl extends CustomServiceImpl<AlbumDictMapper, AlbumDict> implements AlbumDictService {

    @Override
    public boolean removeByEntity(AlbumDict albumDict) {
        LambdaUpdateWrapper<AlbumDict> updateWrapper = new LambdaUpdateWrapper<>();
        if (albumDict.getAlbumId() != null) {
            updateWrapper.eq(AlbumDict::getAlbumId, albumDict.getAlbumId());
        }

        if (!updateWrapper.isEmptyOfNormal()) {
            return super.remove(updateWrapper);
        }

        return Boolean.FALSE;
    }

    @Override
    @Transactional
    public List<AlbumDict> setDictByDocument(Element infoElement, Album album) {

        this.removeByEntity(AlbumDict.builder().albumId(album.getId()).build());

        ArrayList<AlbumDict> albumDictArrayList = new ArrayList<>();

        for (AlbumDictTypeEmun typeEnum : AlbumDictTypeEmun.values()) {

            Elements elementsByClass = infoElement.getElementsByAttributeValue("data-type", typeEnum.getType());

            elementsByClass.forEach(element -> {
                Elements aElements = element.getElementsByTag("a");
                aElements.forEach(aElement -> albumDictArrayList.add(AlbumDict.builder()
                        .href(aElement.attr("href"))
                        .name(aElement.text())
                        .type(typeEnum)
                        .albumId(album.getId())
                        .build()));


            });
        }

        super.customSaveOrUpdateBatch(albumDictArrayList);
        return albumDictArrayList;
    }
}
