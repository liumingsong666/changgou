package com.changgou.service;

import com.song.entity.Result;
import com.changgou.goods.pojo.Brand;

import java.util.List;

public interface BrandService {
    Result insert(Brand brand);

    Result queryById(Integer id);

    Result<List<Brand>> queryAll(Brand brand);

    Result delete(Integer id);

    Result update(Integer id, Brand brand);

    Result queryPage(Integer page, Integer pageSize);

    Result queryPageByCondition(Integer page, Integer pagesize, Brand brand);
}
