package com.TestingBoot.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;

import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;

import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2
@EnableSwagger2WebMvc
@EnableWebMvc
public class Swaggercxonfiguration {
	
		@Value("${enable.swagger.plugin:true}")
	    private boolean enableSwaggerPlugin;
	  
	    ApiInfo apiInfo() {

	        return new ApiInfoBuilder()
	            .title("Test Boot Service")
	            .description("A service where new things I experiment")
	            .license("MIT")
	            .licenseUrl("https://opensource.org/licenses/MIT")
	            .version("1.0.0")
//	            .contact(new Contact("Codeaches","https://codeaches.com", "pavan@codeaches.com"))
	            .build();
	       }

	    @Bean
	    public Docket customImplementation() {

	      return new Docket(DocumentationType.SWAGGER_2)
	          .useDefaultResponseMessages(false)
	          .select()
	          .apis(RequestHandlerSelectors.basePackage("com.TestingBoot"))
	          .paths(PathSelectors.any())
	          .build()
	          .enable(enableSwaggerPlugin)
	          .apiInfo(apiInfo());
	    }
	    
	    @Bean
	    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier, ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier, EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties, WebEndpointProperties webEndpointProperties, org.springframework.core.env.Environment environment) {
	            List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
	            Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
	            allEndpoints.addAll(webEndpoints);
	            allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
	            allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
	            String basePath = webEndpointProperties.getBasePath();
	            EndpointMapping endpointMapping = new EndpointMapping(basePath);
	            boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);
	            return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping);
	        }


	    private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, org.springframework.core.env.Environment environment, String basePath) {
	            return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
	        }

}
