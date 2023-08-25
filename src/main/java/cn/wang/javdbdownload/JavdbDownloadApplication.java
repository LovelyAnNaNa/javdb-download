package cn.wang.javdbdownload;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = {"cn.wang.javdbdownload.jm.mapper"})
@EnableCaching
public class JavdbDownloadApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavdbDownloadApplication.class, args);
    }

}
