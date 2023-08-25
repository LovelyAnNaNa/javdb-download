package cn.wang.javdbdownload.jm.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 漫画信息
 * </p>
 *
 * @author wangbh
 * @since 2023-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("album")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="Album对象", description="漫画信息")
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "漫画url")
    @TableField("album_href")
    private String albumHref;

    @ApiModelProperty(value = "url上的id")
    @TableField("jm_id")
    private String jmId;

    @ApiModelProperty(value = "总页数")
    @TableField("total_page")
    private Integer totalPage;

    @ApiModelProperty(value = "上架日期")
    @TableField("date_published_create")
    private LocalDate datePublishedCreate;

    @ApiModelProperty(value = "更新日期")
    @TableField("date_published_update")
    private LocalDate datePublishedUpdate;

    @ApiModelProperty(value = "观看次数")
    @TableField("views_number")
    private Integer viewsNumber;

    @ApiModelProperty(value = "收藏数量")
    @TableField("collect_number")
    private Integer collectNumber;

    @ApiModelProperty(value = "漫画封面url")
    @TableField("album_photo_cover")
    private String albumPhotoCover;

    @ApiModelProperty(value = "漫画封面本地路径")
    @TableField("photo_cover_local_dir")
    private String photoCoverLocalDir;

    @ApiModelProperty(value = "分类id")
    @TableField("theme_id")
    private Long themeId;


}
