package com.imooc.activitiweb.util;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//路径映射，发布不同操作系统路径不同
@Configuration
public class PathMapping implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //String[] resourceLocation = new String[]{"file:D:\\WangJianIDEA_Test\\activiti-imooc\\src\\main\\resources\\resources\\bpmn\\","classpath:/resources/"};
        registry.addResourceHandler("/**").addResourceLocations("classpath:/resources/");//默认也有这个路径映射
        registry.addResourceHandler("/bpmn/**").addResourceLocations(GlobalConfig.BPMN_PathMapping);


        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\resources\\upload\\";
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            registry.addResourceHandler("/upload/**").
                    addResourceLocations("file:" + path);
        } else {//linux和mac系统 可以根据逻辑再做处理
            registry.addResourceHandler("/upload/**").
                    addResourceLocations("file:" + path);
        }
    }
}
