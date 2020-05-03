package com.song.control;

import com.song.entity.Result;
import com.song.dto.ScheduledDto;
import com.song.service.ScheduledService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-27 17:33
 * @Description:
 */
@RestController
@RequestMapping("/scheduled")
@RequiredArgsConstructor(onConstructor = @_({@Autowired}))
public class ScheduledController {

    private final ScheduledService scheduledService;

    @PostMapping("/start")
    public Result startScheduled(@Validated(value = ScheduledDto.CheckAll.class) @RequestBody ScheduledDto scheduledDto){
        return scheduledService.startScheduled(scheduledDto);
    }

    @PostMapping("/pause")
    public Result pauseScheduled(@Validated(value = ScheduledDto.CheckJobInfo.class) @RequestBody ScheduledDto scheduledDto){
        return scheduledService.pauseScheduled(scheduledDto);
    }

    @PostMapping("/delete")
    public Result deleteScheduled(@Validated(value = ScheduledDto.CheckJobInfo.class) @RequestBody ScheduledDto scheduledDto){
        return scheduledService.deleteScheduled(scheduledDto);
    }

    @PostMapping("/reset")
    public Result resetScheduled(@Validated(value = ScheduledDto.CheckAll.class) @RequestBody ScheduledDto scheduledDto){
        return scheduledService.resetScheduled(scheduledDto);
    }

    @PostMapping("/resume")
    public Result resumeScheduled(@Validated(value = ScheduledDto.CheckJobInfo.class)@RequestBody ScheduledDto scheduledDto){
        return scheduledService.resumeScheduled(scheduledDto);
    }
}
