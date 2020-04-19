package com.song.websocete;

import com.changgou.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-16 14:37
 * @Description:
 */

@RestController
@Slf4j
public class WebSocketController {

    @PostMapping("/websocket/sendAll")
    public Result sendAll(@NotBlank @RequestParam("message") String message, HttpServletResponse response) throws IOException {
        log.info("群发消息：{}",message);
        WebSocketServer.sendAll(message);
        return new Result(true,HttpStatus.OK.value(),"发送成功");
    }

    @PostMapping("/websocket/send")
    public Result send(@NotBlank @RequestParam("userId") String userId , @NotBlank @RequestParam("message") String message) throws IOException {
        String send = WebSocketServer.send(message, userId);
        return new Result(true,HttpStatus.OK.value(),send);
    }
}
