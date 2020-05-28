package com.song.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mingsong.liu
 * @Date: 2020-04-24 14:48
 * @Description: swagger配置，不能和spring-boot-devtools 热部署一起使用，有bug
 */
//@EnableSwaggerBootstrapUI
@Component
@Primary
public class SwaggerProvider implements SwaggerResourcesProvider {

    @Autowired
    private ZuulProperties zuulProperties;


    private SwaggerResource createResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation("/" + location + "/v2/api-docs");
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        zuulProperties.getRoutes().values().stream()
                .forEach(route -> resources
                        .add(createResource(route.getServiceId(), route.getId(), "1.0")));
        return resources;
    }
}
