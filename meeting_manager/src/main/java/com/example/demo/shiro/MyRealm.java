package com.example.demo.shiro;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.example.demo.entity.Employee;
import com.example.demo.entity.Manager;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.ManagerService;

public class MyRealm extends AuthorizingRealm {

	/*** 授权 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("授权");
		return null;
	}

	/* 因为可能存在两张表中 */
	@Resource(name = "ManagerServiceImpl")
	private ManagerService managerService;

	@Resource(name = "EmployeeServiceImpl")
	private EmployeeService employeeService;

	/*** 认证 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("认证");
		ByteSource salt=ByteSource.Util.bytes("123");		//自己定义是个变量
		//编写shiro判断器
		UsernamePasswordToken Token=(UsernamePasswordToken) token;
		String usernameString=Token.getUsername();
		Manager manager=managerService.findbymanagerName(usernameString);
		Employee employee;
		if(null!=manager) {
			return new SimpleAuthenticationInfo(manager,manager.getManagerPassword(),salt, "MyRealm");
		}else {
			//查找用户
			employee=employeeService.findByName(usernameString);
			if(null==employee) {
				return null;
			}else {
				//判断密码
				return new SimpleAuthenticationInfo(employee, employee.getEmployeePassword(), salt,"MyRealm");
			}
			
		}
		
	}

}
