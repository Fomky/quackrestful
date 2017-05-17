package org.fomky.ratpack.core.utils.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPool;

/**
 * @author Created by chenxx on 2016/5/25.
 */
public class ResourceBuilders {
    public static JedisPoolResource createSingle(HostAndPort hostAndPort) {
        return new JedisPoolResource(new JedisPool(hostAndPort.getHost(), hostAndPort.getPort()));
    }
    public static JedisPoolResource createSingle(HostAndPort hostAndPort,GenericObjectPoolConfig config) {
        if (config == null) config = new GenericObjectPoolConfig();
        return new JedisPoolResource(new JedisPool(config,hostAndPort.getHost(), hostAndPort.getPort()));
    }

    public static JedisPoolResource createSingle(HostAndPort hostAndPort, String passwd) {
        return createSingle(hostAndPort, passwd, null);
    }

    public static JedisPoolResource createSingle(HostAndPort hostAndPort, String passwd, GenericObjectPoolConfig config) {
        if (config == null) config = new GenericObjectPoolConfig();
        JedisPool pool = new JedisPool(config, hostAndPort.getHost(), hostAndPort.getPort(), 6000, passwd);
        return new JedisPoolResource(pool);
    }

}
