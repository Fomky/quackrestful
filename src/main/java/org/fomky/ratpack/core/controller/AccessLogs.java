package org.fomky.ratpack.core.controller;

import org.fomky.ratpack.core.entity.Res;
import ratpack.error.ClientErrorHandler;
import ratpack.error.ServerErrorHandler;
import ratpack.error.internal.ErrorHandler;
import ratpack.handling.Chain;
import ratpack.handling.RequestLogger;
import ratpack.handling.RequestOutcome;

import static ratpack.jackson.Jackson.json;

public abstract class AccessLogs implements CoreHandle {
    @Override
    public void execute(Chain chain) throws Exception {
        chain
                .all(RequestLogger.of(this::access))
                .register(registry -> registry
                        .add(ServerErrorHandler.class,error())
                        .add(ClientErrorHandler.class,client())
                );
    }

    public abstract void access(RequestOutcome outcome);

    public ServerErrorHandler error(){
        return  (context, throwable) -> {
            if (throwable.getMessage().contains("SessionKey[name='USERID'")) {
                //未登录返回
                context.render(json(new Res(100, "提示用户登录！")));
            } else {
                context.render(json(new Res(500, throwable.getMessage())));
            }
        };
    }
    public ClientErrorHandler client(){
        return (context, i) -> context.render(json(i));
    }
}
