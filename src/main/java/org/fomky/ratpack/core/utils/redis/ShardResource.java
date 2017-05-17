package org.fomky.ratpack.core.utils.redis;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @author Created by chenxx on 2016/5/26.
 */
public class ShardResource extends PoolResource<ShardedJedis> {
    ShardedJedisPool pool;

    public ShardResource() {
    }

    public ShardResource(ShardedJedisPool pool) {
        this.pool = pool;
    }

    @Override
    public ShardedJedis resource() {
        return pool.getResource();
    }

    @Override
    public void returnResourceObject(ShardedJedis conn) {
        if (conn != null) conn.close();
    }
}
