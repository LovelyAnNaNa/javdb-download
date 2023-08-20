package cn.wang.javdbdownload.inject.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author wbh
 * @date 2023/1/6 17:01
 */
public interface CustomService<T> extends IService<T> {


    Integer customSaveOrUpdateBatch(List<T> list);

}
