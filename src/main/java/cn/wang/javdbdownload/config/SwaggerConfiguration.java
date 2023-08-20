//
package cn.wang.javdbdownload.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration implements WebMvcConfigurer {
    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        String groupName = "默认API";
        return new Docket(DocumentationType.SWAGGER_2)
                //.host("https://www.baidu.com")
                .apiInfo(apiInfo())
                .enable(true)
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.wang.javdbdownload.controller"))
                .paths(PathSelectors.any())
                .build()
                .enableUrlTemplating(false)
                ;
    }


    @Bean
    public Docket jmApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                //分组名称
                .groupName("jmApi")
                .select()
                //只显示admin路径下的页面
                .paths(Predicates.and(PathSelectors.regex("/jm/.*")))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //.title("swagger-bootstrap-ui-demo RESTful APIs")
                .description("# swagger-bootstrap-ui-demo RESTful APIs")
                .termsOfServiceUrl("http://www.xx.com/")
                //.contact("xx@qq.com")
                .version("")
                .build();
    }
}
