package org.fomky.ratpack.core.test;

import org.fomky.ratpack.core.utils.JSONUtil;
import org.fomky.ratpack.core.utils.ProtocstuffUtils;
import ratpack.http.HttpMethod;
import ratpack.http.client.ReceivedResponse;
import ratpack.test.MainClassApplicationUnderTest;
import ratpack.test.http.TestHttpClient;

/**
 * @apiNote Created by fomky on 17-5-13.
 */
public abstract class CoreTest {

    public abstract Class getMainClass();

    public <T, R> R protoPost(String url, T obj, Class<R> rClass) throws Exception {
        return request(url, obj, rClass, HttpMethod.POST);
    }

    public <T, R> R protoGet(String url, T obj, Class<R> rClass) throws Exception {
        return request(url, obj, rClass, HttpMethod.GET);
    }

    public <T, R> R requestJson(String url, T obj, Class<R> rClass, HttpMethod method) throws Exception {
        ReceivedResponse receivedResponse = requestJson(url,obj,method);
        byte[] data = receivedResponse.getBody().getBytes();
        return ProtocstuffUtils.byte2Bean(data, rClass);
    }

    public <T, R> R jsonPost(String url, T obj, Class<R> rClass) throws Exception {
        return requestJson(url, obj, rClass, HttpMethod.POST);
    }

    public <T, R> R pjsonGet(String url, T obj, Class<R> rClass) throws Exception {
        return requestJson(url, obj, rClass, HttpMethod.GET);
    }

    public <T, R> R request(String url, T obj, Class<R> rClass, HttpMethod method) throws Exception {
        ReceivedResponse receivedResponse = requestProto(url,obj,method);
        byte[] data = receivedResponse.getBody().getBytes();
        return ProtocstuffUtils.byte2Bean(data, rClass);
    }
    protected <T> ReceivedResponse requestJson(String url, T Obj, HttpMethod method) throws Exception {
        return TestHttpClient
                .testHttpClient(new MainClassApplicationUnderTest(getMainClass()))
                .request(url,
                        requestSpec -> requestSpec
                                .method(method)
                                .headers(header -> header
                                        .add("Content-type", "application/json")
                                )
                                .body(body -> body
                                        .text(JSONUtil.toJSON(Obj))
                                )
                );
    }

    public <T> ReceivedResponse requestProto(String url, T obj, HttpMethod method) throws Exception {
        return TestHttpClient
                .testHttpClient(new MainClassApplicationUnderTest(getMainClass()))
                .request(url,
                        requestSpec -> requestSpec
                                .method(method)
                                .headers(header -> header
                                        .add("Content-type", "application/x-protobuf")
                                )
                                .body(body -> {
                                    if(obj!=null) body.bytes(ProtocstuffUtils.bean2Byte(obj, obj.getClass()));
                                })
                );
    }


}
