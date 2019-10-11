package com.layman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author 叶泽文
 * @Data 2019/10/11 10:42
 * @Version 3.0
 **/
@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping("/index")
    public String test() {
        return "index";
    }
}
