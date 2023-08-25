package cn.wang.javdbdownload.jm.mapper;

import cn.wang.javdbdownload.common.Constants;
import cn.wang.javdbdownload.inject.CustomBaseMapper;
import cn.wang.javdbdownload.jm.entity.pojo.AlbumDict;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 漫画详情页多个标签的索引 Mapper 接口
 * </p>
 *
 * @author wangbh
 * @since 2023-08-19
 */
@DS(Constants.DATASOURCE_JM)
@CacheNamespace
public interface AlbumDictMapper extends CustomBaseMapper<AlbumDict> {

}
