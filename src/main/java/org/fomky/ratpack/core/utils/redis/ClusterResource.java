package org.fomky.ratpack.core.utils.redis;

import redis.clients.jedis.JedisCluster;

/**
 * @author Created by chenxx on 2016/5/26.
 */
public class ClusterResource extends PoolResource<JedisCluster>{
    private JedisCluster cluster;

    public ClusterResource() {
    }

    public ClusterResource(JedisCluster cluster) {
        this.cluster = cluster;
    }

    @Override
    public JedisCluster resource() {
        return cluster;
    }

    @Override
    public void returnResourceObject(JedisCluster conn) {
        //不用操作
    }
}
