package cn.wang.javdbdownload.jm.service.impl;

import cn.wang.javdbdownload.common.Constants;
import cn.wang.javdbdownload.jm.entity.pojo.Album;
import cn.wang.javdbdownload.jm.mapper.AlbumMapper;
import cn.wang.javdbdownload.jm.service.AlbumService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService {

    @Override
    public int myInsertOrUpdateBatch(List<Album> list) {
        list.forEach(e -> {
            String jmId = e.getJmId();
            Album dbAlbum = super.getOne(new LambdaQueryWrapper<Album>().eq(Album::getJmId, e.getJmId()));
            if (dbAlbum != null){
                BeanUtils.copyProperties(e,dbAlbum,"id");
                super.updateById(dbAlbum);
            }else{
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
