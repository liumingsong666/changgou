package com.song.service;

import com.song.entity.Result;

/**
 * @author: mingsong.liu
 * @date: 2020-05-25 09:49
 * @description: 操作优惠券
 */

public interface CouponService {

    Result<Void> createCoupons(String tempId);
}
