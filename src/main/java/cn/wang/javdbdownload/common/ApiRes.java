package cn.wang.javdbdownload.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@ApiModel("统一返回格式")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Accessors(chain = true)
public class ApiRes<T>  implements Serializable {

    @ApiModelProperty("状态码")
    private int code;

    @ApiModelProperty("返回信息")
    private String message;

    @ApiModelProperty("返回数据")
    private T data;

    public ApiRes(T data){
        this.data = data;
    }

    public  static <T> ApiRes<T> ok(T data){
        ApiRes<T> apiRes = new ApiRes<>(data);
        return apiRes;
    }


}

