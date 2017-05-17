package org.fomky.ratpack.core.utils;

import org.fomky.ratpack.core.entity.Res;
import ratpack.error.ServerErrorHandler;
import ratpack.handling.Chain;

import static ratpack.jackson.Jackson.json;

/**
 * @author Created by Fomky on 2017/3/409:50.
 */
public class ChainUtil {

    private static Chain addErrorSupport(Chain chain) throws Exception {
        return chain
                .register(registry -> registry
                        .add(ServerErrorHandler.class, (context, throwable) -> {
                            if (throwable.getMessage().contains("SessionKey[name='USERID'")) {
                                //未登录返回
                                context.render(json(new Res(100, "提示用户登录！")));
                            } else {
                                context.render(json(new Res(500, throwable.getMessage())));
                            }
                        })
                );
    }

    public static Chain addAllSupport(Chain chain) throws Exception {
        return addErrorSupport(chain);
    }

}
