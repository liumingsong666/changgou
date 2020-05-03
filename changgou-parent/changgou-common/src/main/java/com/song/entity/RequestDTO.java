package com.song.entity;

import com.song.utils.BeanUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-28 10:19
 * @Description:
 */

@Data
@ApiModel("请求的dto")
public class RequestDTO<T> {

    @ApiModelProperty("请求业务对象")
    @Valid
    private T data;

    @ApiModelProperty(value = "唯一请求标识，每次请求唯一性，幂等", notes = "每次请求对应一个ID")
    @NotBlank
    private String requestId;

    @ApiModelProperty("业务订单号")
    private String businessId;

    @NotNull
    @ApiModelProperty("调用方系统")
    private String accessor;

    @ApiModelProperty("备注信息")
    private String comment;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("操作人")
    private String opertor;

    /**
     * 通用创建对象
     *
     * @param data
     * @return
     */
    public static <T> RequestDTO<T> create(T data){
        RequestDTO<T> requestDTO = new RequestDTO<>();
        requestDTO.setData(data);
        requestDTO.setAccessor("");
        requestDTO.setRequestId(UUID.randomUUID().toString());
        return requestDTO;
    }

    public <S> S toType(Class<S> cls){
        return BeanUtils.toNewType(this.getData(), cls);
    }
}
