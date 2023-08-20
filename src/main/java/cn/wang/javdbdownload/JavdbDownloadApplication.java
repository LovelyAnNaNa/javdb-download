package cn.wang.javdbdownload;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"cn.wang.javdbdownload.jm.mapper"})
public class JavdbDownloadApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavdbDownloadApplication.class, args);
    }

}
