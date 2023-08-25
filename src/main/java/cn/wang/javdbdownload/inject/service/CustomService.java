package cn.wang.javdbdownload.inject.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author wbh
 * @date 2023/1/6 17:01
 */
public interface CustomService<T> extends IService<T> {


    Integer customSaveOrUpdateBatch(List<T> list);

    /**
     * 根据实体类中的唯一字段进行更新或新增
     * @param list
     */
    void customSaveOrUpdateByUniqueCol(List<T> list, SFunction<T,?> fn);

}
