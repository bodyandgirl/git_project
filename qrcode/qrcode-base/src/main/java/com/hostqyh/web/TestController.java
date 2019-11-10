package com.hostqyh.web;

import com.google.zxing.Result;
import com.hostqyh.utils.QRCodeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/getHello")
    public String getHello(){
        return "hello";
    }

    /**
     * 生成二维码
     */
    @GetMapping("/genQRRCode")
    public void productcode() {
        System.out.println("hello World");
        QRCodeUtil.zxingCodeCreate("http://www.baidu.com", "D:/voice/picture/2018/",500,"F:/Intellij_IDEA/qrcode/qrcode-base/src/main/resources/static/images/5.jpg");
    }


    /**
     * 解析二维码
     */
    @GetMapping("/test")
    public void analysiscode() {
        Result result = QRCodeUtil.zxingCodeAnalyze("D:/voice/picture/2018/640.jpg");
        System.err.println("二维码解析内容："+result.toString());
    }
}
