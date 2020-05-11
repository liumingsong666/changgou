package com.song.controller;

import com.song.entity.Constant;
import com.song.entity.Result;
import com.song.controller.api.GoodsRPC;
import com.song.entity.User;
import com.song.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * 用户表(User)表控制层
 *
 * @author mingsong.liu
 * @since 2020-04-13 00:28:07
 */
@Api(tags = "USER")
@RestController
//@RequestMapping("/user")
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
    public Result<User> selectOne(@ApiParam(value = "ID") @PathVariable("id") Integer id ,HttpServletRequest request) {
        String token = request.getHeader(Constant.token.TOKEN_AUTHOR);
        Result goods = goodsRPC.getGoods(id);
        log.info("rpc的结果：{} , token: {}", goods.getData(),token);
        return this.userService.queryById(id);

    }


    @GetMapping("/select/nickname")
    @ApiOperation(value = "根据用户名查询用户登录")
    public Result<User> loginByNickName(@NotBlank @RequestParam("username")String nickName ){

        return userService.queryByNickName(nickName);
    }

    @GetMapping("/select/phone")
    @ApiOperation("根据手机号查询用户登录")
    public Result<User> loginByPhone(@NotBlank(message = "手机号不能为空") @RequestParam String phone){
        return userService.queryByPhone(phone);
    }

    @PostMapping("/select/userinfo")
    public Result<User> queryByUser(@RequestBody @NonNull User user){
        return userService.queryByUser(user);
    }
}