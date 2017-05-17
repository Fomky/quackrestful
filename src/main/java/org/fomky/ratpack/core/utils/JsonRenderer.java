package org.fomky.ratpack.core.utils;

import ratpack.handling.Context;
import ratpack.render.RendererSupport;

/**
 * @author Created by Fomky on 2017/3/1814:06.
 */
public class JsonRenderer<T> extends RendererSupport<T> {

    @Override
    public void render(Context ctx, T t) throws Exception {
        String json = JSONUtil.toJSON(t);
        ctx.getResponse().send(json);
    }
}
