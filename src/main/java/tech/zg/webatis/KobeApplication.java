package tech.zg.webatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * <p>
 * @author: 张弓
 * @date:
 * @version: 1.0.0
 */
@SpringBootApplication(scanBasePackages = "tech.zg.kobe")
@EnableAutoConfiguration
@MapperScan(basePackages = {"tech.zg.kobe.mapper"}, sqlSessionFactoryRef = "masterSqlSessionFactory")
public class KobeApplication {

    public static void main(String[] args) {
        SpringApplication.run(KobeApplication.class, args);
    }
}
