package cn.wang.javdbdownload.inject.service;


import cn.wang.javdbdownload.inject.CustomBaseMapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * @author wbh
 * @date 2023/1/6 17:02
 */
public class CustomServiceImpl<M extends CustomBaseMapper<T>, T> extends ServiceImpl<M,T> implements CustomService<T>, InitializingBean {

    @Getter
    private String dataSourceName = null;

    @Override
    public Integer customSaveOrUpdateBatch(List<T> list) {

        return baseMapper.customSaveOrUpdateBatch(list);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        Class<? extends CustomServiceImpl> clz = this.getClass();
        DS dsAnnotation = clz.getAnnotation(DS.class);
        if (dsAnnotation != null) {
            dataSourceName = dsAnnotation.value();
        }

    }
}
