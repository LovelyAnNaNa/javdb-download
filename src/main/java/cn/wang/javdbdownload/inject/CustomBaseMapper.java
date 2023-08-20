package cn.wang.javdbdownload.inject;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  自定义
 * @author Jeremy
 */
public interface CustomBaseMapper<T> extends BaseMapper<T> {


    /**
     * 自定义批量插入
     * @param batchList
     * @return
     */
    int customSaveOrUpdateBatch(@Param("list") List<T> batchList);


}
