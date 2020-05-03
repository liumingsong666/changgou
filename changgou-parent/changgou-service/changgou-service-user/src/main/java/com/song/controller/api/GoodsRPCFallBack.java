package com.song.controller.api;

import com.song.entity.Result;
import com.song.entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * @Auther: mingsong.liu
 * @Date: 2020/4/13 22:04
 * @Description:
 */

@Component
public class GoodsRPCFallBack implements GoodsRPC{

    @Override
    public Result getGoods(Integer id) {
        return new Result(false,StatusCode.ERROR,"fallback返回值");
    }
}
