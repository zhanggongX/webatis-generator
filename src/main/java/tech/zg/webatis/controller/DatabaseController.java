package tech.zg.webatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import tech.zg.webatis.common.RestResult;
import tech.zg.webatis.entity.DatabaseEntity;

/**
 * @author 张弓
 * @create 2018-04-08 19:31
 */
@Controller
@RequestMapping("/dbs")
public class DatabaseController {

    @RequestMapping("/add")
    public ModelAndView add(ModelAndView mv){
        mv.setViewName("/dbs/add");
        return mv;
    }

    @RequestMapping("/save")
    @ResponseBody
    public RestResult save(@RequestBody DatabaseEntity databaseEntity){
        return RestResult.success();
    }

}
