package com.context;

import com.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public class BaseContextHandler {
    public static ThreadLocal<Map<String,Object>> threadLocal = new ThreadLocal<Map<String,Object>>();

    public static void set(String key,Object value){
       Map<String,Object> map =  threadLocal.get();
       if(map ==null){
            map = new HashMap<String,Object>();
            threadLocal.set(map);
       }
       map.put(key,value);
    }
    public static Object get(String key){
        Map<String,Object> map = threadLocal.get();
        if(map==null){
            map = new HashMap<String,Object>();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    public static String getUserId(){
        Object value = get(CommonConstants.CONTEXT_KEY_USER_ID);
        return returnObjectValue(value);
    }
    public static String getUsername(){
        Object value = get(CommonConstants.CONTEXT_KEY_USERNAME);
        return returnObjectValue(value);
    }
    public static void setUserId(String userId){
        set(CommonConstants.CONTEXT_KEY_USER_ID,userId);
    }
    public static void setUserName(String userName){
        set(CommonConstants.CONTEXT_KEY_USERNAME,userName);
    }
    private static String returnObjectValue(Object value) {
        return value==null?null: value.toString();
    }


    public static void  init(){
        Map<String,Object> map = threadLocal.get();
        if(map==null){
            map = new HashMap<String,Object>();
            threadLocal.set(map);
        }
    }

    public static void remove(){
        threadLocal.remove();
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class UnitTest{
        /*@Mock
        HttpServletRequest request;
        @Mock
        HttpServletResponse response;*/
       /* @Test
        public void testGetContext(){
            BaseContextHandler.s
            asserNotNull(context);
        }*/
        @Test
        public void testContextVariable() throws Exception{
           BaseContextHandler.set("test","map");
            new Thread(()->{
                BaseContextHandler.set("test","map");
                try {
                    Thread.currentThread().sleep(3000);
                }catch(Exception e){
                    e.printStackTrace();
                }
                Assert.assertEquals(BaseContextHandler.get("test"),"map");
                log.info("thread one done!");
            }).start();
            Assert.assertEquals(BaseContextHandler.get("test"),"map");

            new Thread(()->{
                BaseContextHandler.set("test1","map1");
                Assert.assertEquals(BaseContextHandler.get("test1"),"map1");
                log.info("thread two done!");
            }).start();
            Thread.currentThread().sleep(5000);
            log.info("main one deno");
        }

        @Test
        public void testSetUserInfo(){
            BaseContextHandler.setUserId("test");
            Assert.assertEquals(BaseContextHandler.get(CommonConstants.CONTEXT_KEY_USER_ID),"test");
            BaseContextHandler.setUserName("test");
            Assert.assertEquals(BaseContextHandler.get(CommonConstants.CONTEXT_KEY_USERNAME),"test");
        }

    }
}
