package com.rest;

import com.entry.JsonObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
    @RequestMapping("/getData")
    public List<JsonObject> getData(){
        List<JsonObject> datas = new ArrayList<>();
        JsonObject j1 = new JsonObject();
        j1.setName("Hello World");
        j1.setNodeId("1111");
        JsonObject j2 = new JsonObject();
        j2.setName("你好世界");
        j2.setNodeId("2222");
        datas.add(j1);
        datas.add(j2);
        return datas;
    }
}
