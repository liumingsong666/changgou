package com.song.control;

import com.song.entity.Result;
import com.song.entity.UserFeignDto;
import com.song.entity.UserInfo;
import feign.Request;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-17 23:49
 * @Description:
 */

@FeignClient(name = "USER")
public interface UserRPC {

    @RequestMapping(value = "/select/nickname",method = RequestMethod.GET)
    @ResponseBody
    Result<UserFeignDto> loginByUsername(@RequestParam("nickName") String nickName);

    @RequestMapping(value = "/select/phone",method = RequestMethod.GET)
    @ResponseBody
    Result<UserFeignDto> loginByPhone(@RequestParam("phone") String phone);
}
