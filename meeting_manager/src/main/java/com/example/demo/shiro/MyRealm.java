package com.example.demo.shiro;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import com.example.demo.entity.Employee;
import com.example.demo.entity.SysPermission;
import com.example.demo.entity.SysRole;
import com.example.demo.service.EmployeeService;

public class MyRealm extends AuthorizingRealm {

	/*** 授权 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("授权");
		// 给资源进行授权
		SimpleAuthorizationInfo simpleInfo = new SimpleAuthorizationInfo();

		// 给资源赋予权限
		// 获取当前登录用户
		Subject subject = SecurityUtils.getSubject();
		// 通过getPrincipal拿到的就是下边的认证登录逻辑中的返回时候的第一个参数user
		Employee employee = (Employee) subject.getPrincipal();
		
		// 通过循环赋予用户权限
		for (SysRole role : employee.getRoleList()) {
			simpleInfo.addRole(role.getRole()); // 添加角色
			for (SysPermission p : role.getPermissions()) { // 添加权限
				simpleInfo.addStringPermission(p.getPermission());
			}
		}
		return simpleInfo;
	}

	@Resource(name = "EmployeeServiceImpl")
	private EmployeeService employeeService;

	/*** 认证 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("认证");
		ByteSource salt = ByteSource.Util.bytes("123"); // 自己定义是个变量
		// 编写shiro判断器
		UsernamePasswordToken Token = (UsernamePasswordToken) token;
		String usernameString = Token.getUsername();
		Employee employee;
		// 查找用户
		employee = employeeService.findByName(usernameString);
		if (null == employee) {
			return null;
		} else {
			// 判断密码
			System.out.println(employee.getEmployeePassword());
			return new SimpleAuthenticationInfo(employee, employee.getEmployeePassword(), salt, "MyRealm");
		}

	}

}
