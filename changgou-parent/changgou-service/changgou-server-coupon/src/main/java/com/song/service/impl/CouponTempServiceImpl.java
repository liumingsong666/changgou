package com.song.service.impl;

import com.song.entity.CouponTemp;
import com.song.entity.Result;
import com.song.entity.StatusCode;
import com.song.mapper.CouponTempMapper;
import com.song.service.CouponTempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author: mingsong.liu
 * @date: 2020-05-24 20:47
 * @description:
 */

@Service
public class CouponTempServiceImpl implements CouponTempService {
    
    @Autowired
    private CouponTempMapper couponTempMapper;

    @Override
    public Result addCouponTemp(CouponTemp couponTemp) {

        if(couponTempMapper.insert(couponTemp)>0){
            return Result.success("模板生成成功，等待审核",null);
        }
        return Result.fail(StatusCode.ERROR,"创建优惠券失败");
    }

    @Override
    public Result<Void> updateCouponTemp(CouponTemp couponTemp) {
        return Result.fail(StatusCode.ERROR,"修改功能暂未开放");
    }

    @Override
    public Result<Void> stopCouponTemp(String couponTempId) {
        if(couponTempMapper.stopTempById(couponTempId)>0){
            return Result.success("优惠券停止使用",null);
        }
        return Result.fail(StatusCode.ERROR,"优惠券停止使用失败");
    }

    @Override
    public Result<List<CouponTemp>> getAllTemp() {
        List<CouponTemp> couponTemps = couponTempMapper.queryAll();
        if(Objects.isNull(couponTemps)){
            return Result.success(new ArrayList<>());
        }
        return Result.success(couponTemps);
    }

    @Override
    public Result<List<CouponTemp>> getUsableTemp() {
        List<CouponTemp> couponTemps =couponTempMapper.getUsableTemp();
        if(Objects.isNull(couponTemps)){
            return Result.success(new ArrayList<>());
        }
        return Result.success(couponTemps);
    }
}
