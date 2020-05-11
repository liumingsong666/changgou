package com.song.service.impl;

import com.google.common.collect.Lists;
import com.song.dao.ProductInfoMapper;
import com.song.dto.AddCartDto;
import com.song.entity.ProductInfo;
import com.song.entity.Result;
import com.song.interceptor.TokenInterceptor;
import com.song.service.OrderCartService;
import com.song.vo.OrderCartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @author: mingsong.liu
 * @date: 2020-05-09 13:31
 * @description:
 */

@Service
public class OrderCartServiceImpl implements OrderCartService {

    private final String REDIS_ORDER_CART = "CHANGGOU:ORDER:CART:";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public Result<List<OrderCartVo>> cartShow() {
        String username = TokenInterceptor.contextUsername.get();
        //获取用户购物车的所有商品id，存的是id，方便添加个数
        Set<Integer> keys = redisTemplate.opsForHash().keys(REDIS_ORDER_CART + username);
        if(CollectionUtils.isEmpty(keys)){
            return Result.success("购物车为空",null);
        }
        //获取数据库的商品信息
        List<ProductInfo> productInfos = productInfoMapper.queryByIds(keys);
        if(keys.size()!= productInfos.size()){
            return Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(),"获取商品信息失败");
        }

        List<OrderCartVo> lists = Lists.newArrayList();
        for (ProductInfo productInfo : productInfos) {
            Integer count = (Integer) redisTemplate.opsForHash().get(REDIS_ORDER_CART + username, productInfo.getProductId());
            OrderCartVo orderCartVo = new OrderCartVo(productInfo, count);
            lists.add(orderCartVo);
        }

         return Result.success(lists);
    }

    @Override
    public Result<Void> cartUpdate(AddCartDto addCartDto) {
        String username = TokenInterceptor.contextUsername.get();
        Long increment = redisTemplate.opsForHash().increment(REDIS_ORDER_CART + username, addCartDto.getProductId(), addCartDto.getCount());
        if(increment<=0){
            redisTemplate.opsForHash().delete(REDIS_ORDER_CART + username, addCartDto.getProductId());
        }
        return Result.success("修改购物车成功");
    }


}
