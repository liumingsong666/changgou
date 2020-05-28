package com.song.control;

import com.song.entity.CouponTemp;
import com.song.entity.Result;
import com.song.service.CouponTempService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: mingsong.liu
 * @date: 2020-05-24 20:42
 * @description: 优惠券模块接口
 */

@Api("优惠券模板接口")
@RestController
@RequestMapping("/coupontemp")
public class CouponTempController {

    @Autowired
    private CouponTempService couponTempService;

    @PostMapping("/add")
    @ApiOperation("添加优惠券模板")
    public Result<Void> addCouponTemp(@RequestBody @Validated CouponTemp couponTemp){

        return couponTempService.addCouponTemp(couponTemp);

    }

    @PostMapping("/update")
    @ApiOperation("修改优惠券模板")
    public Result<Void> updateCouponTemp(@RequestBody @Validated CouponTemp couponTemp){
        return couponTempService.updateCouponTemp(couponTemp);
    }

    @GetMapping("/stop")
    @ApiOperation("修改优惠券模板")
    public Result<Void> stopCouponTemp(@RequestParam("couponTempId") String couponTempId){
        return couponTempService.stopCouponTemp(couponTempId);
    }

    @GetMapping("/alltemp")
    @ApiOperation("获取所有的优惠券")
    public Result<List<CouponTemp>> getAllTemp(){
        return couponTempService.getAllTemp();
    }

    @GetMapping("/usabletemp")
    @ApiOperation("获取可用的优惠券模板")
    public Result<List<CouponTemp>> getUsableTemp(){
        return couponTempService.getUsableTemp();
    }
}
