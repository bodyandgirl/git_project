package com.hostqyh.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageSourceUtil {

    public static final String qqAppIdKey = "constants.qqAppId";
    public static final String qqAppSecretKey = "constants.qqAppSecret";
    public static final String qqRedirectUrlKey = "constants.qqRedirectUrl";
    public static final String weCatAppIdKey = "constants.weCatAppId";
    public static final String weCatAppSecretKey = "constants.weCatAppSecret";
    public static final String weCatRedirectUrlKey = "constants.weCatRedirectUrl";

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key , String ...args){
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }
}
