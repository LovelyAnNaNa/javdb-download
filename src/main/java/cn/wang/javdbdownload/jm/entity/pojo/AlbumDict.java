package cn.wang.javdbdownload.jm.entity.pojo;

import cn.wang.javdbdownload.jm.common.emum.AlbumDictTypeEmun;
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
 * 漫画详情页多个标签的索引
 * </p>
 *
 * @author wangbh
 * @since 2023-08-19
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("album_dict")
@ApiModel(value="AlbumDict对象", description="漫画详情页多个标签的索引")
public class AlbumDict implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("href")
    private String href;

    @TableField("name")
    private String name;

    @ApiModelProperty(value = "类型（works-作品、actor-登场人物、tags-标签、author-作者）")
    @TableField("type")
    private AlbumDictTypeEmun type;

    @TableField("album_id")
    private Long albumId;


}
