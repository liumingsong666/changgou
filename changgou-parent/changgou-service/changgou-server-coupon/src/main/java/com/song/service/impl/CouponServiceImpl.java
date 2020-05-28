package com.song.service.impl;

import com.song.entity.Coupon;
import com.song.entity.CouponTemp;
import com.song.entity.Result;
import com.song.entity.StatusCode;
import com.song.idutil.IPIdGenerator;
import com.song.mapper.CouponMapper;
import com.song.mapper.CouponTempMapper;
import com.song.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author: mingsong.liu
 * @date: 2020-05-25 09:49
 * @description:
 */

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponTempMapper couponTempMapper;

    private final IPIdGenerator ipIdGenerator = new IPIdGenerator();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> createCoupons(String tempId) {
        CouponTemp couponTemp = couponTempMapper.queryByTempId(tempId);

        if(Objects.isNull(couponTemp)){
            return Result.fail(StatusCode.ERROR,"无可用的优惠券模板");
        }
        Integer couponNum = couponTemp.getCouponNum();
        if(couponNum<0){
            return Result.fail(StatusCode.ERROR,"生成的模板总数异常");
        }
        Set<Coupon> set = new HashSet<>(couponNum);
        for (Integer i = 0; i < couponNum; i++) {

            //todo 添加优惠券
            //set.add()
        }
        return null;
    }
}
