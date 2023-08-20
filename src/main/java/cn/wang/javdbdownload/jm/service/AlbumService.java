package cn.wang.javdbdownload.jm.service;

import cn.wang.javdbdownload.inject.service.CustomService;
import cn.wang.javdbdownload.jm.entity.pojo.Album;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 漫画信息 服务类
 * </p>
 *
 * @author wangbh
 * @since 2023-08-19
 */
public interface AlbumService extends CustomService<Album> {

    /**
     * 根据url重新获取漫画信息
     * @param id
     * @return
     */
    Album flushDataByWeb(Long id);

    int myInsertOrUpdateBatch(List<Album> list);

}
