package com.song.entity;

public class StatusCode {

    public static final Integer OK=2000;
    public static final Integer ERROR=2001;     //失败
    public static final Integer LOGINERROR=2002;  //用户名密码错误
    public static final Integer ACCESSERROR=2003; //权限不足
    public static final Integer REMOTEERROR=2004; //远程调用失败
    public static final Integer REPEERROR=2005; //重复操作
    public static final Integer NOTFOUNDERROR=2006; //没有对应的抢购数据
}
