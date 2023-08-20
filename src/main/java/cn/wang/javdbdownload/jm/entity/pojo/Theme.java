package cn.wang.javdbdownload.jm.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 
 *  </p>
 * jm漫画分类
 * @author wangbh
 * @since 2023-08-19
 */
@Data
@ApiModel("jm漫画分类")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("theme")
@EqualsAndHashCode(callSuper = false)
public class Theme implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 分类名称
     */
    @ApiModelProperty("分类名称")
    private String name;

    /**
     * 路径
     */
    @ApiModelProperty("路径")
    private String href;

    /**
     * 分类标签
     */
    @ApiModelProperty("分类标签")
    private String label;


}
