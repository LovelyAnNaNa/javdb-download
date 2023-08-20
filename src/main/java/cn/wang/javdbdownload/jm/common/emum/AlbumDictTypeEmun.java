package cn.wang.javdbdownload.jm.common.emum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 漫画信息分类
 */
@Getter
@AllArgsConstructor
public enum AlbumDictTypeEmun {

    WORKS("works"),
    ACTOR("actor"),
    TAGS("tags"),
    AUTHOR("author")
    ;


    @EnumValue
    private String type;

}
