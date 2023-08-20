package cn.wang.javdbdownload.jm.controller;


import cn.wang.javdbdownload.common.ApiRes;
import cn.wang.javdbdownload.jm.entity.pojo.Album;
import cn.wang.javdbdownload.jm.entity.pojo.Theme;
import cn.wang.javdbdownload.jm.service.ThemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wangbh
 * @since 2023-08-19
 */
@Api(tags = "jm标题模块")
@RestController
@RequestMapping("/jm/theme")
public class ThemeController {

    @Resource
    private ThemeService themeService;

    @ApiOperation(value = "获取对应标题id下的所有漫画信息并入库")
    @GetMapping("/getAlbumByThemeId")
    public ApiRes<List<Album>> getAlbumByThemeId(Long id){
        return ApiRes.ok(themeService.getAlbumByThemeId(id));
    }

    @ApiOperation(value = "初始化jm标题")
    @GetMapping("/initTheme")
    public ApiRes<List<Theme>> initTheme(){
        return ApiRes.ok(themeService.initTheme());
    }
}

