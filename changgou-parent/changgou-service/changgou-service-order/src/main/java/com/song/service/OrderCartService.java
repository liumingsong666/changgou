package com.song.service;

import com.song.dto.AddCartDto;
import com.song.entity.Result;
import com.song.vo.OrderCartVo;

import java.util.List;

/**
 * @author: mingsong.liu
 * @date: 2020-05-09 13:31
 * @description:
 */

public interface OrderCartService {

    Result<List<OrderCartVo>> cartShow();

    Result<Void> cartUpdate(AddCartDto addCartDto);
}
