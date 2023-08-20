package cn.wang.javdbdownload.jm.service.impl;

import cn.wang.javdbdownload.common.Constants;
import cn.wang.javdbdownload.jm.entity.pojo.AlbumDict;
import cn.wang.javdbdownload.jm.mapper.AlbumDictMapper;
import cn.wang.javdbdownload.jm.service.AlbumDictService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class AlbumDictServiceImpl extends ServiceImpl<AlbumDictMapper, AlbumDict> implements AlbumDictService {

}
