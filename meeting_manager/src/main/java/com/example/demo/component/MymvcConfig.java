package com.example.demo.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.component.MyLocaleResolver;

/***
 * 自定义webmvc配置
 */
@Configuration
public class MymvcConfig implements WebMvcConfigurer {

	@Bean
	public LocaleResolver localResolver() {
		return new MyLocaleResolver();
	}
}
