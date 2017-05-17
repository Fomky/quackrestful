package org.fomky.ratpack.core.handler;

import org.fomky.ratpack.core.entity.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ratpack.error.internal.ErrorHandler;
import ratpack.handling.Context;


/**
 * @author Created by Fomky on 2017/3/315:27.
 */
@Component
public class DefaultErrorHandler implements ErrorHandler {
    private Logger logger = LoggerFactory.getLogger(DefaultErrorHandler.class);
    @Override
    public void error(Context context, int statusCode) throws Exception {
    }

    @Override
    public void error(Context context, Throwable throwable) throws Exception {
        logger.error(throwable.getMessage(),throwable);
        throwable.printStackTrace();
        context.render(new Res(500,"Caught by error Handler: " + throwable.getMessage()));
    }
}
