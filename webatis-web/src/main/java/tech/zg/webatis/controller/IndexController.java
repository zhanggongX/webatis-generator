package tech.zg.webatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
* 类说明
* <p>
* @author ：zhanggong
* @version : 1.0.0
* @date ：2018/4/24
*/
@Controller
public class IndexController {

    @RequestMapping("/")
    public ModelAndView index(ModelAndView mv){
        mv.setViewName("/index");
        return mv;
    }


}