package com.song.service;

import com.song.entity.Result;
import com.song.dto.ScheduledDto;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-27 17:46
 * @Description:
 */

public interface ScheduledService {

    Result startScheduled(ScheduledDto scheduledDto);

    Result pauseScheduled(ScheduledDto scheduledDto);

    Result deleteScheduled(ScheduledDto scheduledDto);

    Result resetScheduled(ScheduledDto scheduledDto);

    Result resumeScheduled(ScheduledDto scheduledDto);
}
