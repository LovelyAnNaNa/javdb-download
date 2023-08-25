package cn.wang.javdbdownload.jm.mapper;

import cn.wang.javdbdownload.common.Constants;
import cn.wang.javdbdownload.inject.CustomBaseMapper;
import cn.wang.javdbdownload.jm.entity.pojo.Album;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 漫画信息 Mapper 接口
 * </p>
 *
 * @author wangbh
 * @since 2023-08-19
 */
@DS(Constants.DATASOURCE_JM)
@CacheNamespace
public interface AlbumMapper extends CustomBaseMapper<Album> {

}
