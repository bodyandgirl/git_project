package com.web;

import com.WebService;
import com.entity.OrderRecord;
import com.entity.common.ResponseBody;
import com.service.IOrderRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloWorldController {

    @Autowired
    private WebService webService;

    @Autowired
    private IOrderRecordService orderRecord;

    @GetMapping("/getHello")
    public ResponseBody<Void> getHello(){
        String message = "Hello World";
        return new ResponseBody<Void>().toBuilder().status(HttpStatus.OK).build();
    }

    @GetMapping("/test")
    public ResponseBody<Void> testHello(){
        String message = webService.getHello();
        return new ResponseBody<Void>().toBuilder()
                .status(HttpStatus.OK)
                .message(message)
                .build();
    }

    @GetMapping("/testMq")
    public ResponseBody<Object> getList(){
        return new ResponseBody<>().toBuilder()
                                    .status(HttpStatus.OK)
                                    .data( orderRecord.getAllList()  )
                                    .build();
    }
}
