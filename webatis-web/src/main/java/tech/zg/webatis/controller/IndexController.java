package tech.zg.webatis.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import tech.zg.webatis.common.RestResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 类说明
 * <p>
 *
 * @author ：zhanggong
 * @version : 1.0.0
 * @date ：2018/4/24
 */
@Controller
public class IndexController {

    /**
     * 首页
     * <p>
     *
     * @param mv
     * @return ModelAndView
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/4/28
     */
    @RequestMapping("/")
    public ModelAndView defult(ModelAndView mv) {
        mv.setViewName("index");
        return mv;
    }

    /**
     * 首页
     * <p>
     *
     * @param mv
     * @return ModelAndView
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/4/28
     */
    @RequestMapping("/index")
    public ModelAndView index(ModelAndView mv) {
        mv.setViewName("index");
        return mv;
    }

    /**
     * 登录
     * <p>
     *
     * @param mv
     * @return ModelAndView
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/4/28
     */
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, ModelAndView mv) {
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            if(param.equals("error")){
                mv.addObject("error", "密码错误");
            }
        }
        mv.setViewName("login");
        return mv;
    }

    @RequestMapping("/userName")
    @ResponseBody
    public RestResult userName() {
        RestResult result = RestResult.success();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        result.put("userName", userDetails.getUsername());
        return result;
    }
}