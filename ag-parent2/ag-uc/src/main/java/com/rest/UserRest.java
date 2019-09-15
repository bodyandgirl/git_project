package com.rest;

import com.biz.ArticleBiz;
import com.entity.Article;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserRest extends BaseController<ArticleBiz,Article> {



    /*@Value("${language: 你好，世界}")
    private String hello;
    @Autowired
    private ArticleBiz article;

    @RequestMapping("/hello")
    public String getUserInfo(@PathVariable String id){
        return hello;
    }

    @RequestMapping("/{id}")
    public Article test(@PathVariable Integer id){
        System.out.println(article.selectByPrimaryKey(id));
        return article.selectByPrimaryKey(id);
    }*/
}
