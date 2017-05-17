package org.fomky.ratpack.core.utils.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by chenjz on 2016/8/16.
 * 设定分享时间间隔
 */
public class ShareTimeSetting {
    public static final int TIME_SPACE = 1800;//一个小时
    public static final Jedis jedis = new Jedis();

    //分享成功一次之后加上时间
    public static void setUserTimeSpace(long id){
        String  uid = "user_"+id;
        jedis.setex(uid,TIME_SPACE,"1");//设定初始值
    }
    //用户注册成功时初始化他们的分享时间
    public static void setUserKey(long id){
        String  uid = "user_"+id;
        jedis.set(uid,"1");
    }
    //拿取值
    public static String getUser(long id){
        String  uid = "user_"+id;
        return jedis.get(uid);
    }
}
