package com.song.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-27 17:49
 * @Description: 接收定时任务的参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledDto {

    @NotBlank(groups = {CheckAll.class,CheckJobInfo.class} ,message = "任务job名不能为空")
    @ApiParam("任务job名")
    private String name;

    @NotBlank(groups = {CheckAll.class},message = "定时任务的参数")
    @ApiParam("定时任务的参数")
    private String username;

    @NotBlank(groups = {CheckAll.class,CheckJobInfo.class},message = "任务job组不能为空")
    @ApiParam("任务job组")
    private String group;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate = new Date();

    @NotBlank(groups = CheckAll.class,message = "cron表达式不能为空")
    private String cron;

    public interface CheckAll{

    }

    public interface CheckJobInfo{}
}
