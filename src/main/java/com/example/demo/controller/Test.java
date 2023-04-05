package com.example.demo.controller;

import com.example.demo.databind.FormConvertBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author wanghg
 */
@RestController
@RequestMapping("/ggh")
public class Test {
    @PostMapping("/tre")
    @ResponseBody
    public String test(@Validated ProjectBean obj){
        return obj.toString();
    }


    @PostMapping("/trr")
    @ResponseBody
    public String test1(@Validated @RequestBody ProjectBean obj){
        return obj.toString();
    }


    @PostMapping("/trx")
    @ResponseBody
    public String test2(@Validated @FormConvertBean ProjectBean params){
        return params.toString();
    }
}
