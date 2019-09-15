package com.utils;


import java.util.HashMap;

public class ResponseResult<K,V> extends HashMap<K,V> {
    private String code;
    private String message;
    public ResponseResult(){
        code = "0";
        message= "成功";
    }
    public static ResponseResult success(Object o){
       ResponseResult r =  new ResponseResult();
       r.message = String.valueOf(o);
       return r;
    }
    public static ResponseResult error(Object o){
        ResponseResult r =  new ResponseResult();
        r.code = "444444";
        r.message = String.valueOf(o);
        return r;
    }
}
