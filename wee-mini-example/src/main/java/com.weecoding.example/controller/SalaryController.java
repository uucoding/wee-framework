package com.weecoding.example.controller;

import com.weecoding.example.service.SalaryService;
import com.weecoding.framework.beans.Autowired;
import com.weecoding.framework.web.mvc.Controller;
import com.weecoding.framework.web.mvc.RequestMapping;
import com.weecoding.framework.web.mvc.RequestParam;

/**
 * 测试 @Controller、@RequestMapping, @Autowired
 * @author : wee
 * @Date 2019-06-13  21:32
 */
@Controller
public class SalaryController {

    @Autowired
    SalaryService salaryService;

    @RequestMapping("/getSalary")
    public Integer getSalary(@RequestParam("name") String name, @RequestParam("money") String money) {
        System.out.println(name + " : " + money);
        return salaryService.calculate(Integer.parseInt(money));
    }

}
