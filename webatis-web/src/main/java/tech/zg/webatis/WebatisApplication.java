package tech.zg.webatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 * <p>
 *
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
@SpringBootApplication(scanBasePackages = "tech.zg.webatis")
@MapperScan(basePackages = {"tech.zg.webatis.mapper"}, sqlSessionFactoryRef = "masterSqlSessionFactory")
public class WebatisApplication {

    /**
     * 我是man，我最牛逼
     * <p>
     *
     * @param args
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/4/28
     */
    public static void main(String[] args) {
        SpringApplication.run(WebatisApplication.class, args);
    }
}
