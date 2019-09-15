package com.vo;

public class ObjectRestResponse<T> extends BaseResponse {
    boolean rel;
    //T result;
    T data;



    public boolean isRel() {
        return rel;
    }

    public void setRel(boolean rel) {
        this.rel = rel;
    }

    /*public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }*/
    public ObjectRestResponse rel(boolean rel){
        this.setRel(rel);
        return this;
    }
    /*public ObjectRestResponse result(T result){
        this.setResult(result);
        return this;
    }*/
    public ObjectRestResponse data(T data){
        this.setData(data);
        return this;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
