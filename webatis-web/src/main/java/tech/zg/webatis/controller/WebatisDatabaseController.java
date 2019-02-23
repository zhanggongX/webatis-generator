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

import java.util.List;
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

    /**
     * 进入新增数据库配置页
     * <p>
     *
     * @param mv
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/4/28
     */
    @RequestMapping("/add")
    public ModelAndView add(ModelAndView mv) {
        mv.addObject( "webatisDatabase", new WebatisDatabaseEntity());
        mv.addObject("opType", "save");
        mv.setViewName("dbs/dbsAdd");
        return mv;
    }

    /**
     * 进入数据库列表页
     * <p>
     *
     * @param mv
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/4/28
     */
    @RequestMapping("/list")
    public ModelAndView list(ModelAndView mv) {
        mv.setViewName("dbs/dbsList");
        return mv;
    }

    /**
     * 新增数据库配置
     * <p>
     *
     * @param databaseEntity
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/4/28
     */
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

    /**
     * 更新数据库配置
     * <p>
     *
     * @param databaseEntity
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/4/28
     */
    @RequestMapping("/update")
    @ResponseBody
    public RestResult update(@RequestBody WebatisDatabaseEntity databaseEntity) {
        try {
            webatisDatabaseService.update(databaseEntity);
        } catch (BaseException e) {
            logger.error("更新失败:{}", e);
            RestResult.error("更新失败");
        }
        return RestResult.success();
    }

    /**
     * 进入更新数据库配置页
     * <p>
     *
     * @param id
     * @param mv
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/4/28
     */
    @RequestMapping("/details/{id}")
    public ModelAndView details(@PathVariable Integer id, ModelAndView mv) {
        WebatisDatabaseEntity webatisDatabaseEntity = null;
        try {
            webatisDatabaseEntity = webatisDatabaseService.get(id);
        } catch (BaseException e) {
            logger.error("保存失败:{}", e);
            RestResult.error("保存失败");
        }
        mv.addObject("webatisDatabase", webatisDatabaseEntity);
        mv.addObject("opType", "update");
        mv.setViewName("dbs/dbsAdd");
        return mv;
    }

    /**
     * 分页查询数据库
     * <p>
     *
     * @param params
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/4/28
     */
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

    /**
     * 删除数据配置
     *
     * @param id
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/4/28
     */
    @RequestMapping("/deleted/{id}")
    @ResponseBody
    public RestResult deleted(@PathVariable Integer id) {
        try {
            webatisDatabaseService.remove(id);
        } catch (BaseException e) {
            e.printStackTrace();
            logger.error("删除失败:{}", e);
            RestResult.error("删除失败");
        }
        return RestResult.success();
    }
    
    /**
     * 查询数据库配置列表
     * <p>
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/4/28
     */
    @RequestMapping("/listAll")
    @ResponseBody
    public RestResult listAll() {
        List<WebatisDatabaseEntity> webatisDatabaseEntityList = webatisDatabaseService.list();
        return RestResult.success().put("webatisDatabases", webatisDatabaseEntityList);
    }

}
