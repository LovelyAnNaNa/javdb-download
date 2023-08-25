package cn.wang.javdbdownload.inject.service;


import cn.wang.javdbdownload.inject.CustomBaseMapper;
import cn.wang.javdbdownload.util.FieldUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.Iterator;
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
    public void customSaveOrUpdateByUniqueCol(List<T> list, SFunction<T,?> fn) {
        //根据列值去重
        ArrayList<Object> colValueList = new ArrayList<>(list.size());
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()){
            T next = iterator.next();
            Object colValue = fn.apply(next);
            if (colValueList.contains(colValue)) {
                iterator.remove();
            }else{
                colValueList.add(colValue);
            }
        }


        list.forEach(e -> {
            LambdaQueryWrapper<T> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(fn,fn.apply(e));
            T dbEntity = baseMapper.selectOne(queryWrapper);
            if (dbEntity != null){
                BeanUtils.copyProperties(e,dbEntity,FieldUtil.getKeyName(e.getClass()));
                baseMapper.updateById(dbEntity);
            }else{
                baseMapper.insert(e);
            }
        });
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
