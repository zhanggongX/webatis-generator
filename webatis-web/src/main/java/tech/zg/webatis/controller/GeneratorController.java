package tech.zg.webatis.controller;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import tech.zg.webatis.common.RestResult;
import tech.zg.webatis.pager.Pager;
import tech.zg.webatis.pager.PagerUtil;
import tech.zg.webatis.service.GeneratorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyVetoException;
import java.io.IOException;
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
    public void list(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] tableNames = request.getParameterValues("tableName");

        byte[] data = generatorService.genCode(tableNames);

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
    @RequestMapping("/listTableByPager")
    @ResponseBody
    public RestResult listByPager(@RequestParam Map<String, Object> params){
        //todo 获取动态SqlSessionFactory
        /*PooledDataSource pooled = new PooledDataSource();
        pooled.setDriver("com.mysql.jdbc.Driver");
        pooled.setUrl("");
        pooled.setUsername("");
        pooled.setPassword("");
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("mysql", transactionFactory, pooled);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(String.class);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession sqlSession = factory.openSession();
        //sqlSession.select();*/

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("");
        dataSource.setPassword("");
        dataSource.setJdbcUrl("");
        try {
            dataSource.setDriverClass("");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        dataSource.setInitialPoolSize(1);
        dataSource.setMaxPoolSize(10);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        //jdbcTemplate.

        Pager pager = PagerUtil.getPager(params);
        String tableName = (String) params.get("tableName");
        generatorService.listByPager(pager, tableName);
        RestResult restResult = RestResult.success();
        //为了兼容layui
        restResult.put("count", pager.getTotalCount());
        restResult.put("data", pager.getList());
        restResult.put("code", 0);
        return restResult;
    }

    /*@RequestMapping("/getTables")
    @ResponseBody
    public RestResult getTables(){
        return RestResult.success();
    }*/

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
