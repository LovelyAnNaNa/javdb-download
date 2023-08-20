package cn.wang.javdbdownload.jm.mapper;

import cn.wang.javdbdownload.common.Constants;
import cn.wang.javdbdownload.jm.entity.pojo.Album;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 漫画信息 Mapper 接口
 * </p>
 *
 * @author wangbh
 * @since 2023-08-19
 */
@DS(Constants.DATASOURCE_JM)
public interface AlbumMapper extends BaseMapper<Album> {

}
