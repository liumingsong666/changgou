package com.song.service;

import com.song.entity.CouponTemp;
import com.song.entity.Result;

import java.util.List;

/**
 * @author: mingsong.liu
 * @date: 2020-05-24 20:46
 * @description: 操作优惠券模板
 */

public interface CouponTempService {

    Result addCouponTemp(CouponTemp couponTemp);

    Result<Void> updateCouponTemp(CouponTemp couponTemp);

    Result<Void> stopCouponTemp(String couponTempId);

    Result<List<CouponTemp>> getAllTemp();

    Result<List<CouponTemp>> getUsableTemp();

}
