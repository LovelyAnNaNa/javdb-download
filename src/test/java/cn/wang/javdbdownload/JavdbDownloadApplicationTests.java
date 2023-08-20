package cn.wang.javdbdownload;

import cn.wang.javdbdownload.jm.entity.pojo.Theme;
import cn.wang.javdbdownload.jm.service.ThemeService;
import cn.wang.javdbdownload.jm.service.impl.ThemeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class JavdbDownloadApplicationTests {

    @Resource
    private ThemeService themeService;

    @Test
    void contextLoads() {

        List<Theme> themeList = themeService.list();
        themeList.forEach(e -> themeService.getAlbumByThemeId(e.getId()));
    }

}
