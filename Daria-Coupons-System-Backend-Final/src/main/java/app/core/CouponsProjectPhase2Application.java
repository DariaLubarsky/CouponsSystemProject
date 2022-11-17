package app.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import app.core.filters.LoginFilterAdmin;
import app.core.util.JwtUtil;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CouponsProjectPhase2Application {

	public static void main(String[] args) {
		SpringApplication.run(CouponsProjectPhase2Application.class, args);

	}

	@Bean
	public FilterRegistrationBean<LoginFilterAdmin> filterRegistrationBean(JwtUtil jwtUtil) {
		FilterRegistrationBean<LoginFilterAdmin> regBean = new FilterRegistrationBean<>();
		LoginFilterAdmin loginFilter = new LoginFilterAdmin(jwtUtil);
		regBean.setFilter(loginFilter);
		regBean.addUrlPatterns("/api/admin/*");
		return regBean;
	}

	@Bean
	public Docket apoDocket() {
		return new Docket(DocumentationType.SWAGGER_2)

				.select()

				.apis(RequestHandlerSelectors.basePackage("app.core"))

				.build();
	}

}
