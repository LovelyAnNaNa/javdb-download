package cn.wang.javdbdownload.jm.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 章节信息表
 * </p>
 *
 * @author wangbh
 * @since 2023-08-20
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("album_chapter")
@ApiModel(value="AlbumChapter对象", description="章节信息表")
public class AlbumChapter implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "章节数")
    @TableField("num")
    private Integer num;

    @ApiModelProperty(value = "章节链接")
    @TableField("href")
    private String href;

    @ApiModelProperty(value = "章节标题")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "漫画id")
    @TableField("album_id")
    private Long albumId;


}
