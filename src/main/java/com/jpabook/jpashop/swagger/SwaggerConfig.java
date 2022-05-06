package com.jpabook.jpashop.swagger;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	@Value("${swagger-doc.app-name}")
	private String APP_NAME;
	@Value("${swagger-doc.version}")
	private String APP_VERSION;
	
	@Bean
	public Docket apiV1() {
		return new Docket(DocumentationType.OAS_30) // swagger v3.0
			.useDefaultResponseMessages(false) // 응답코드 제거, 컨트롤러상 명시
			.select() // ApiSelectorBuilder 생성
			.apis(RequestHandlerSelectors.basePackage("com.jpabook.jpashop.mvc")) // api 스펙기재된 패키지
			.paths(PathSelectors.ant("/**")) // request mapping 종류 = PathSelectors.any()
			.build() // 스웨거 빌드
			.apiInfo(apiInfo(APP_NAME, APP_VERSION));
	}
	
	private ApiInfo apiInfo(String title, String version) {
		return new ApiInfo(
			title,
			"Swagger로 생성한 API Docs",
			version,
			"www.example.com",
			new Contact("Contact Me", "www.example.com", "foo@example.com"),
			"Licenses",
			"www.example.com",
			new ArrayList<>());
	}
}
