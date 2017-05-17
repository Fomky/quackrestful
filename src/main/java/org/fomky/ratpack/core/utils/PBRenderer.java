package org.fomky.ratpack.core.utils;

import ratpack.handling.Context;
import ratpack.render.RendererSupport;

/**
 * 功能 : 数据 Response 返回时候, 解析成PB 并且进行RSA 加密
 * 注意 : 如果换成 HTTPS 协议可取消RSA 加密
 * @author Created by Fomky on 2017/3/310:51.
 */
public class PBRenderer<T> extends RendererSupport<T> {

    @Override
    public void render(Context ctx, T t) throws Exception {
        //PB 转换 -
        byte[] bytes = ProtocstuffUtils.bean2Byte(t,t.getClass());
        //TODO 处理加密 - 二进制数据
        ctx.getResponse().send(bytes);
    }

}
