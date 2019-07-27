package com.example.demo.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {

	/**
	 * 创建shiroFilterFactoryBean
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(
			@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);
	   /**Shiro内置过滤器，可以实现权限相关的拦截器 常用的过滤器： 
		  anon: 无需认证（登录）可以访问 
		  authc: 必须认证才可以访问
		  user:如果使用rememberMe的功能可以直接访问 
		  perms： 该资源必须得到资源权限才可以访问 
		  role: 该资源必须得到角色权限才可以访问*/
		Map<String,String> filterMap=new LinkedHashMap<String,String>();
		//1.拦截所有界面(登录地址和首页除外)
		//1.1设置静态资源的拦截状态(默认是拦截的)
		filterMap.put("/images/**", "anon");//img
	    filterMap.put("/css/**", "anon");//css
	    filterMap.put("/js/**", "anon");//js
	    
		filterMap.put("/manager/","anon");
		filterMap.put("/manager/index","anon");
		filterMap.put("/manager/yuyue","anon");
		filterMap.put("/**","anon");			//需要放在最后
		
		//2.修改调整的登录界面
		shiroFilterFactoryBean.setLoginUrl("/manager/index");
		
		//3.授权过滤器(如果拦截直接去未授权页面)
		filterMap.put("add", "perms[employee:add]");
		filterMap.put("view","perms[employee:view]");
		filterMap.put("del", "perms[employee:del]");
		filterMap.put("fileview","perms[file:view]");
		
		//3.设置未授权提示页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		
		//end设置拦截器
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		
		return shiroFilterFactoryBean;
	}

	/**
	 * 创建DefaultWebSecurityManager
	 */
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getdeDefaultWebSecurityManager(@Qualifier("MyRealm") MyRealm myRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myRealm);
		return securityManager;
	}

	/**
	 * 创建Realm
	 **/
	@Bean(name = "MyRealm")
	public MyRealm getRealm() {
		MyRealm userRealm = new MyRealm();
		return userRealm;
	}

	/* 盐加密 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher(); // 使用md5,算法进行加密
		hashedCredentialsMatcher.setHashAlgorithmName("md5"); // 设置加密次数
		hashedCredentialsMatcher.setHashIterations(1024);
		return hashedCredentialsMatcher;
	}

}
