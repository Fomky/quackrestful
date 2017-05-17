package org.fomky.ratpack.core.entity;

/**
 * @author Created by Fomky on 2017/4/1310:42.
 */
public class BaseReq<T> {
    private Integer start = 0;
    private Integer size = 20;
    private Integer id;
    private T data;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
