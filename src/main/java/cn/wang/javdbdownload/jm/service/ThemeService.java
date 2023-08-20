package cn.wang.javdbdownload.jm.service;

import cn.wang.javdbdownload.jm.entity.pojo.Album;
import cn.wang.javdbdownload.jm.entity.pojo.Theme;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangbh
 * @since 2023-08-19
 */
public interface ThemeService extends IService<Theme> {

    /**
     * 初始化分类
     */
    List<Theme> initTheme();

    /**
     * 获取对应分类下的所有漫画链接和标题
     * @param id
     * @return
     */
    List<Album> getAlbumByThemeId(Long id);
}
