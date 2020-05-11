package com.song.service;

import com.song.dao.BrandMapper;
import com.song.entity.Result;
import com.song.entity.StatusCode;
import com.changgou.goods.pojo.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    @Transactional
    public Result update(Integer id, Brand brand) {
        brand.setId(id);
        brandMapper.updateByPrimaryKeySelective(brand);
        return new Result(true,StatusCode.OK,"更新信息成功");
    }

    @Override
    public Result queryPage(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Brand> brands = brandMapper.selectAll();
        PageInfo<Brand> brandPageInfo = new PageInfo<>(brands);
        return new Result(true,StatusCode.OK,"查询成功",brandPageInfo.getList());
    }

    @Override
    public Result queryPageByCondition(Integer page, Integer pagesize, Brand brand) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if(!Objects.isNull(brand)){
            if(!StringUtils.isEmpty(brand.getName())){
                criteria.andLike("name","%"+brand.getName()+"%");
            }
            if(!StringUtils.isEmpty(brand.getLetter())){
                criteria.andEqualTo("letter",brand.getLetter());
            }
        }
        PageHelper.startPage(page,pagesize,"letter desc");
        List<Brand> brands = brandMapper.selectByExample(example);
        PageInfo<Brand> brandPageInfo = new PageInfo<>(brands);
        return new Result(true,StatusCode.OK,"查询成功",brandPageInfo);
    }

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public Result delete(Integer id) {

        //编程式事务，解决显示事务占用连接 TODO
        int i = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {

                return brandMapper.deleteByPrimaryKey(id);
            }
        });

        if(i>0){
            return new Result(true,StatusCode.OK,"删除成功");
        }
        return new Result(false,StatusCode.ERROR,"删除失败");
    }

    @Override
    public Result queryAll(Brand brand) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if(!Objects.isNull(brand)){
            if(!StringUtils.isEmpty(brand.getName())){
                criteria.andLike("name","%"+brand.getName()+"%");
            }
            if(!StringUtils.isEmpty(brand.getLetter())){
                criteria.andEqualTo("letter",brand.getLetter());
            }
        }
        List<Brand> brands = brandMapper.selectByExample(example);
        return new Result(true,StatusCode.OK,"查询成功", brands);
    }

    @Override
    public Result queryById(Integer id) {
        if(id==1) {
            throw new RuntimeException("测试");
        }
        Brand brand = brandMapper.selectByPrimaryKey(id);
        return new Result(true, StatusCode.OK, "查询成功", brand);
    }

    @Override
    public Result insert(Brand brand) {
        brandMapper.insertSelective(brand);
        return new Result(true, StatusCode.OK,"插入成功");
    }
}
