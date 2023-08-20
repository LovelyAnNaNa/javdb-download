package cn.wang.javdbdownload.jm.controller;


import cn.wang.javdbdownload.common.ApiRes;
import cn.wang.javdbdownload.jm.entity.pojo.Album;
import cn.wang.javdbdownload.jm.service.AlbumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 漫画信息 前端控制器
 * </p>
 *
 * @author wangbh
 * @since 2023-08-19
 */
@Api(tags = "漫画模块")
@RestController
@RequestMapping("/jm/album")
public class AlbumController {


    @Resource
    private AlbumService albumService;


    @ApiOperation("根据url重新获取漫画信息")
    @GetMapping("/flushDataByWeb")
    public ApiRes<Album> flushDataByWeb(Long id){
        return ApiRes.ok(albumService.flushDataByWeb(id));
    }
}

