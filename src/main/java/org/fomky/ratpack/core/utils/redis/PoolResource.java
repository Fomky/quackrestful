package org.fomky.ratpack.core.utils.redis;

import java.util.function.Function;

/**
 * Reids 链接源
 * @author Created by chenxx on 2016/5/25.
 */
public abstract class PoolResource<T> {
    NodeType type = NodeType.Single;
    abstract T resource();
    abstract void returnResourceObject(T conn);

    /**
     * 执行命令
     * @param func
     * @param <B>
     * @return
     */
    public <B> B cmd(Function<T,B> func){
        T conn = null;
        try{
            conn = resource();
            return func.apply(conn);
        }finally {
            returnResourceObject(conn);
        }
    }
}
