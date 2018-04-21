package tech.zg.webatis.controller;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import tech.zg.webatis.bean.TableBean;
import tech.zg.webatis.common.RestResult;
import tech.zg.webatis.pager.Pager;
import tech.zg.webatis.pager.PagerUtil;
import tech.zg.webatis.service.GeneratorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 数据表controller
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
@Controller
@RequestMapping("/gen")
public class GeneratorController {

    Logger LOGGER = LoggerFactory.getLogger(GeneratorController.class);

    @Autowired
    private GeneratorService generatorService;

    /**
     * 生成代码
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param request
     * @param response
     */
    @RequestMapping("/genCode")
    public void list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] tableNames = request.getParameterValues("tableName");
        String dbId = request.getParameter("databaseId");

        byte[] data = generatorService.genCode(Integer.valueOf(dbId), tableNames);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }

    /**
     * ajax表数据获奖
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param params 参数
     * @return RestResult
     */
    @RequestMapping("/listTable/{dbId}")
    @ResponseBody
    public RestResult list(@PathVariable Integer dbId, @RequestParam Map<String, Object> params){
        String tableName = (String) params.get("tableName");
        List<TableBean> tableBeanList = null;
        try {
            tableBeanList = generatorService.list(dbId, tableName);
        } catch (Exception e) {
            LOGGER.error("获取数据库的表数据失败,请检查数据库的配置信息!", e);
            return RestResult.error("获取数据库的表数据失败,请检查数据库的配置信息!");
        }
        //为了兼容layui
        RestResult restResult = RestResult.success().put("data", tableBeanList).put("code", 0);
        return restResult;
    }

    /**
     * 进入列表页
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param mv
     * @return ModelAndView
     */
    @RequestMapping("/tableList")
    public ModelAndView list(ModelAndView mv){
        mv.setViewName("/gen/tableList");
        return mv;
    }
}
