package com.hostqyh.web;

import com.alibaba.fastjson.JSONObject;
import com.hostqyh.utils.MessageSourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Source;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class WeCatController {
    @Autowired
    private MessageSourceUtil messageSourceUtil;

    /**
     * 用户信息临时保存处
     */
    private static Object quert;

    /**
     * 微信登录页
     * @return
     */
    @GetMapping("/wxLogin")
    public String login(){
        return "index";
    }

    @RequestMapping(value = "/wxLoginPage",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> wxLoginPage(HttpServletRequest request) throws Exception{
        String sessionId = request.getSession().getId();
        //设置redirect_uri 和state=sessionId以及测试号信息 ， 返回授权url
        String uri = this.getAuthorizationUrl("pc",sessionId);
        Map<String,String> data = new HashMap<>();
        //用来生成前端二维码
        data.put("uri",uri);
        // 生成二维码清除上一个用户的数据
        quert = null;
        return data;
    }

    /**
     * 扫描 二维码授权成功取到code 设置回调方法
     * @param code
     * @param state
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping("/pcAuth")
    @ResponseBody
    public String pcCallback(String code, String state, HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String result = this.getAccessToken(code);
        System.out.println(result);
        log.error(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        System.out.println(jsonObject);
        log.error(jsonObject.toJSONString());
        // String refresh_token = jsonObject.getString("refresh_token");
        String access_token = jsonObject.getString("access_token");
        String openId = jsonObject.getString("openId");
        //授权成功---》根据token和openid获取微信用户信息，
        JSONObject infoJson = this.getUserInfo(access_token , openId);
        if(infoJson  != null){
            infoJson.put("openId",openId);
        }
        //登录成功保存用户数据
        quert = infoJson;
        return "登录成功";
    }

    /**
     * 检测登录状态（获取用户信息） 每秒被调用一次
     * @param 登录成功 ，立马得到用户信息返回前台 ， 并取消监听
     * @param session
     * @return
     */
    @RequestMapping("/getInfoJson")
    @ResponseBody
    public String getInfoJson(HttpSession session){
        System.out.println("666");
        if(quert == null){
            return "no";
        }
        return quert.toString();
    }

    /**
     * 获取生成二维码url连接
     * @author 豪
     * @param appid 公众号唯一标识
     * @param redirect_uri 授权后重写向的连接地址
     * @param response_type 返回类型填写code
     * @param scope 应用授权作用域 ， snsapi_base , snsapi_userinfo
     * @param state 非必传 ， 重定向后带上state参数 ， 开发者填写a-zA-Z0-9的参数值，最多128字节
     * @param wechat_redirect :无论直接打开还是做页面302重定向的时候，必须带此参数
     * @param type
     * @param state
     * @return
     */
    public String getAuthorizationUrl(String type,String state){
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
        String callbackUrl = "";
        Object urlState = "";
        try{
            if("pc".equals(type)){
                callbackUrl = URLEncoder.encode(messageSourceUtil.getMessage(MessageSourceUtil.weCatRedirectUrlKey)+"/pcAuth","utf-8");
                urlState = state;
            }else if("mobile".equals(type)){
                callbackUrl = URLEncoder.encode(messageSourceUtil.getMessage(MessageSourceUtil.weCatRedirectUrlKey)+"/pcAuth","utf-8");
                urlState = System.currentTimeMillis();
            }
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        // 权限snsapi_userinfo snsapi_base
        String scope = "snsapi_userinfo";
        url = String.format(url,messageSourceUtil.getMessage(MessageSourceUtil.weCatAppIdKey),callbackUrl,scope,urlState);
        return url;
    }

    /**
     * 获取 token , openId (token 有效期2小时
     *
     * @date
     * @param code
     * @return
     */
    public String getAccessToken(String code){
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
        url = String.format(url,
                messageSourceUtil.getMessage(MessageSourceUtil.weCatAppIdKey),
                messageSourceUtil.getMessage(MessageSourceUtil.weCatRedirectUrlKey),code);
        UriComponentsBuilder builder =UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();
        String resp = getRestTemplate().getForObject(uri,String.class);
        if(resp.contains("openid")){
            JSONObject jsonObject = JSONObject.parseObject(resp);
            String access_token = jsonObject.getString("access_token");
            String openId = jsonObject.getString("openId");
            JSONObject res = new JSONObject();
            res.put("access_token",access_token);
            res.put("openId",openId);
            res.put("refresh_token",jsonObject.getString("refresh_token"));
            return res.toJSONString();
        }else{
            return null;
        }
    }

    /**
     * 微信接口中 ， token和 openId 是一起返回， 故此方法不需要实现
     * @param accessToken
     * @return
     */
    public String getOpenId(String accessToken){
        return null;
    }

    /**
     * 获取用户信息
     * @param accessToken
     * @param openId
     * @return
     */
    public JSONObject getUserInfo(String accessToken , String openId){
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
        url = String.format(url,accessToken,openId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();
        String resp = getRestTemplate().getForObject(uri,String.class);
        if(resp.contains("errcode")){
            return null;
        }else{
            JSONObject data = JSONObject.parseObject(resp);
            JSONObject result = new JSONObject();
            result.put("id",data.getString("unionid"));
            result.put("sex",data.getString("sex"));
            result.put("nickName",data.getString("nickname"));
            result.put("avatar",data.getString("headimgurl"));
            return result;
        }
    }

    /**
     * 微信的token只有2小时的有效期， 过时需要重新获取，所以官方提供了根据refresh_token
     * 刷新获取token的方法，本项目仅仅是获取用户，信息，并将信息存入库，所以两个小时已经足够了
     * @param refresh_token
     * @return
     */
    public String refreshToken(String refresh_token){
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";
        url = String.format(url,messageSourceUtil.getMessage(MessageSourceUtil.weCatAppIdKey),refresh_token);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();
        ResponseEntity<JSONObject> resp = getRestTemplate().getForEntity(uri,JSONObject.class);
        JSONObject jsonObject = resp.getBody();
        String access_token = jsonObject.getString("access_token");
        return access_token;
    }

    /**
     *
     * @return
     */
    public static RestTemplate getRestTemplate(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(120000);
        List<HttpMessageConverter<?>> messageConverters = new LinkedList<>();
        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        messageConverters.add(new ResourceHttpMessageConverter());
        messageConverters.add(new SourceHttpMessageConverter<Source>());
        messageConverters.add(new AllEncompassingFormHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = new RestTemplate(messageConverters);
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }
}
