package com.song.control;

import com.song.dto.AddCartDto;
import com.song.entity.Result;
import com.song.service.OrderCartService;
import com.song.vo.OrderCartVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: mingsong.liu
 * @date: 2020-05-09 13:23
 * @description: 购物车
 */

@RestController
@Api("购物车接口")
public class OrderCartController {

    @Autowired
    private OrderCartService orderCartService;

    @PostMapping("/cartupdate")
    @ApiOperation(value = "修改购物车",httpMethod = "POST" )
    public Result<Void> cartAdd(@RequestBody @Validated AddCartDto addCartDto){
        return orderCartService.cartUpdate(addCartDto);
    }

    @GetMapping("cartshow")
    @ApiOperation(value = "获取购物车信息",httpMethod = "GET" )
    public Result<List<OrderCartVo>> cartShow(){
        return orderCartService.cartShow();
    }

}
