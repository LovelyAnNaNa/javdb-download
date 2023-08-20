package cn.wang.javdbdownload.jm.mapper;

import cn.wang.javdbdownload.common.Constants;
import cn.wang.javdbdownload.inject.CustomBaseMapper;
import cn.wang.javdbdownload.jm.entity.pojo.AlbumChapter;
import com.baomidou.dynamic.datasource.annotation.DS;

/**
 * <p>
 * 章节信息表 Mapper 接口
 * </p>
 *
 * @author wangbh
 * @since 2023-08-20
 */
@DS(Constants.DATASOURCE_JM)
public interface AlbumChapterMapper extends CustomBaseMapper<AlbumChapter> {

}
