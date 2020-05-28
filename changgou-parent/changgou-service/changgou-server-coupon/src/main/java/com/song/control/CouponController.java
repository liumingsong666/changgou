package com.song.control;

import com.song.entity.Result;
import com.song.service.CouponService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: mingsong.liu
 * @date: 2020-05-25 09:44
 * @description:
 */

@RestController
@RequestMapping("/coupon")
@Api("优惠券操作接口")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/createcoupons")
    public Result<Void> createCoupons(String tempId){
        return couponService.createCoupons(tempId);
    }

}
