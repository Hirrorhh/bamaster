package com.bamaster.core.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-11-27
 *
 * @create 2017-11-27 15:50
 *
 * @desc 配置swagger2信息
 */
@Configuration      //让Spring来加载该类配置
@EnableWebMvc       //启用Mvc，非springboot框架需要引入注解@EnableWebMvc
@EnableSwagger2     //启用Swagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).select()
                //扫描指定包中的swagger注解
                //.apis(RequestHandlerSelectors.basePackage("com.xia.controller"))
                //扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("备案大师 RESTful APIs")
                .description("备案大师 RESTful 风格的接口文档,按照控制器controller进行划分,可在此平台上直接进行接口测试")
                .termsOfServiceUrl("没有网站,所以写了汉字!")
                .contact("Hirror")
                .version("1.0.0")
                .build();
    }
}