package com.song.controller;

import com.changgou.entity.Result;
import com.song.controller.api.GoodsRPC;
import com.song.entity.User;
import com.song.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

/**
 * 用户表(User)表控制层
 *
 * @author mingsong.liu
 * @since 2020-04-13 00:28:07
 */
@Api(tags = "用户表(User)")
@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    /**
     * 服务对象
     */
    //@Autowired
    private final UserService userService;

    //@Autowired
    private final GoodsRPC goodsRPC;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "根据id查询 用户表")
    @GetMapping("/select/{id}")
    public User selectOne(@ApiParam(value = "ID") @PathVariable("id") Integer id) {
        Result goods = goodsRPC.getGoods(id);
        log.info("rpc的结果：{}", goods.getData());
        return this.userService.queryById(id);

    }


    @PostMapping("/login")
    @ApiOperation(value = "根据用户名查询 用户表")
    public User login(@NotBlank @RequestParam("username")String username ){

        return userService.queryBuUsername(username);
    }

}