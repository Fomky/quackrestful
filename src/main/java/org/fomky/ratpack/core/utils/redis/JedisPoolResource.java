package org.fomky.ratpack.core.utils.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Created by chenxx on 2016/5/25.
 */
public class JedisPoolResource extends PoolResource<Jedis> {
    private JedisPool pool;

    public JedisPoolResource() {
    }

    public JedisPoolResource(JedisPool pool) {
        this.pool = pool;
    }

    @Override
    public Jedis resource() {
        return pool.getResource();
    }

    @Override
    public void returnResourceObject(Jedis conn) {
        conn.close();
    }

    public JedisPool getPool() {
        return pool;
    }

    public void setPool(JedisPool pool) {
        this.pool = pool;
    }
}
