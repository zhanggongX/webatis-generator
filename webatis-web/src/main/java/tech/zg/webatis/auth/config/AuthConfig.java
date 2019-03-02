package tech.zg.webatis.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.zg.webatis.auth.service.AuthService;

/**
 * 权限配置
 * <p>
 *
 * @author: 张弓
 * @date: 2019/2/23
 * @version: 1.0.0
 */
@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {

    /**
     * spring security 获取权限信息的服务
     */
    @Autowired
    private AuthService authService;

    /**
     * 权限信息的配置
     * <p>
     *
     * @param http
     * @throws Exception
     * @author: 张弓
     * @date: 2019/2/23
     * @version: 1.0.0
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // 配置页面要admin用户才可以访问
                // .antMatchers("/config").hasRole("admin")
                // 其他URL只需要验证用户即可访问
                .anyRequest().authenticated()
                // 指定登录页，登录失败页，成功默认页。
                // .and().formLogin().loginPage("/login").failureUrl("/login").defaultSuccessUrl("/index").permitAll()
                // .and().formLogin().loginPage("/login").failureUrl("/login").permitAll()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/index").loginProcessingUrl("/loginCheck").permitAll()
                // frame禁用去掉
                .and().headers().frameOptions().disable()
                // 登出默认页
                .and().logout().logoutSuccessUrl("/login").permitAll().invalidateHttpSession(true);
        //.and().logout().permitAll();

        http.csrf().disable();

    }

    /**
     * 配置获取用户信息的服务
     * <p>
     *
     * @param auth
     * @throws Exception
     * @author: 张弓
     * @date: 2019/2/23
     * @version: 1.0.0
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(charSequence.toString());
            }
        });
    }

    /**
     * 静态资源放行
     * <p>
     *
     * @param web
     * @throws Exception
     * @author: 张弓
     * @date: 2019/2/23
     * @version: 1.0.0
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/layui/**", "/fslayui/**", "/images/**");
    }
}
