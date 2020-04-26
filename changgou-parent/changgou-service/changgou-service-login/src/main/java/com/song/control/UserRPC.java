package com.song.control;

import com.song.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-17 23:49
 * @Description:
 */

@FeignClient(name = "USER")
public interface UserRPC {

    @GetMapping("/user/select")
    UserInfo queryUser(@RequestParam("username") String username);
}
