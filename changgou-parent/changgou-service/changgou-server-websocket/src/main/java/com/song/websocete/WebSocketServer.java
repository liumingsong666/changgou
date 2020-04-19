package com.song.websocete;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-16 13:57
 * @Description:
 */
@Slf4j
@ServerEndpoint("/websocket/{userId}")
public class WebSocketServer {

    private static AtomicInteger count = new AtomicInteger(0);
    private static Map<String,WebSocketServer> map = new ConcurrentHashMap<>(100);

    private Session session;

    private String userId;

    @OnOpen
    public void open(Session session , @PathParam("userId") String userId){
        this.session=session;

        this.userId=userId;

        WebSocketServer webSocketServer = map.putIfAbsent(userId, this);

        if(webSocketServer !=this){
            int i = count.incrementAndGet();
            log.info("当前在线人数为：{}",i);
            return;
        }
        return;

    }

    @OnClose
    public void close() {
        boolean remove = map.remove(userId, this);
        if(remove){
            int i = count.decrementAndGet();
            log.info("当前人数为：{}",i);
            return;
        }
        log.info("移除失败");
    }

    @OnError
    public void error(Throwable throwable){
        throwable.printStackTrace();
    }

    @OnMessage
    public void sendMessage(@PathParam("userId") String userId, String message) throws IOException {

        if(StringUtils.isEmpty(message)){
            send("发送信息为空");
            return;
        }
        JSONObject jsonObject = JSONObject.parseObject(message);
        String toUserId = jsonObject.getString("toUserId");
        WebSocketServer webSocketServer = map.get(toUserId);

        if(Objects.isNull(webSocketServer)){
            send("没有该好友");
            return;
        }
        webSocketServer.send(jsonObject.getString("contentType"));
    }

    public void send(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static void sendAll(String message){
        map.entrySet().stream().forEach(entry -> {
            try {
                entry.getValue().send(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 定制发送个人的接口
     * @param message
     * @param userId
     */
    public static String send(String message,String userId) throws IOException {
        WebSocketServer webSocketServer = map.get(userId);

        if(Objects.nonNull(webSocketServer)){
            webSocketServer.send(message);
            return "发送成功";
        }
        return "没有该用户";
    }

}
