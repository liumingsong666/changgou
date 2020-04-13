package com.song.controller.api;

import com.changgou.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Auther: mingsong.liu
 * @Date: 2020/4/13 21:54
 * @Description:
 */

@FeignClient(value = "GOODS",fallback = GoodsRPCFallBack.class)
public interface GoodsRPC {

    @PostMapping("/brand/{id}")
    Result getGoods(@PathVariable("id") Integer id);
}
