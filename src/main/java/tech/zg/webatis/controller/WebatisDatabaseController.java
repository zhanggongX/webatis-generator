package tech.zg.webatis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tech.zg.webatis.common.RestResult;
import tech.zg.webatis.entity.WebatisDatabaseEntity;
import tech.zg.webatis.exception.BaseException;
import tech.zg.webatis.pager.Pager;
import tech.zg.webatis.pager.PagerUtil;
import tech.zg.webatis.service.WebatisDatabaseService;

import java.util.Map;

/**
 * @author 张弓
 * @create 2018-04-08 19:31
 */
@Controller
@RequestMapping("/dbs")
public class WebatisDatabaseController {

    private static final Logger logger = LoggerFactory.getLogger(WebatisDatabaseController.class);

    @Autowired
    private WebatisDatabaseService webatisDatabaseService;

    @RequestMapping("/add")
    public ModelAndView add(ModelAndView mv) {
        mv.setViewName("/dbs/add");
        return mv;
    }

    @RequestMapping("/list")
    public ModelAndView list(ModelAndView mv) {
        mv.setViewName("/dbs/list");
        return mv;
    }

    @RequestMapping("/save")
    @ResponseBody
    public RestResult save(@RequestBody WebatisDatabaseEntity databaseEntity) {
        try {
            webatisDatabaseService.save(databaseEntity);
        } catch (BaseException e) {
            logger.error("保存失败:{}", e);
            RestResult.error("保存失败");
        }
        return RestResult.success();
    }

    @RequestMapping("/listByPager")
    @ResponseBody
    public RestResult listByPager(@RequestParam Map<String, Object> params) {
        Pager pager = PagerUtil.getPager(params);
        String dbsName = (String) params.get("dbsName");
        webatisDatabaseService.listByPager(pager, dbsName);
        RestResult restResult = RestResult.success();
        //为了兼容layui
        restResult.put("count", pager.getTotalCount());
        restResult.put("data", pager.getList());
        restResult.put("code", 0);
        return restResult;
    }

    @RequestMapping("deleted/{id}")
    @ResponseBody
    public RestResult deleted(@PathVariable Integer id){
        try {
            webatisDatabaseService.remove(id);
        } catch (BaseException e) {
            e.printStackTrace();
            logger.error("删除失败:{}", e);
            RestResult.error("删除失败");
        }
        return RestResult.success();
    }

}
