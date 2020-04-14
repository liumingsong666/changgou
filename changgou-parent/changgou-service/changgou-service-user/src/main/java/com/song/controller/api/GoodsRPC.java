package com.song.controller.api;

import com.changgou.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Auther: mingsong.liu
 * @Date: 2020/4/13 21:54
 * @Description:
 */

@FeignClient(name = "GOODS",fallback = GoodsRPCFallBack.class)
public interface GoodsRPC {

    @GetMapping(value = "/brand/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result getGoods(@PathVariable("id") Integer id);
}
