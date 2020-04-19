package com.song.control;

import com.alibaba.fastjson.JSONObject;
import com.changgou.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-17 23:49
 * @Description:
 */

@FeignClient(name = "user")
public interface UserRPC {

    @PostMapping("/user/login")
    UserInfo queryUser(@RequestParam("username") String username);
}
