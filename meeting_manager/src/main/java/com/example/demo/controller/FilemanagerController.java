package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.common.util.Editor;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Manager;
import com.example.demo.entity.Matter;
import com.example.demo.service.MatterService;


/*文件控制层*/
@Controller
@RequestMapping("filemanager") 
public class FilemanagerController {
	@Autowired
	Editor Editor; // 配置类(负责文件富文本框中内容的读写)

	@Resource(name = "MatterServiceImpl")
	MatterService matterService;

	// send file
	@GetMapping("/send")
	public String filemain(ModelMap model, HttpSession session) {
		if (null != session.getAttribute("Employee")) {
			Employee employee = (Employee) session.getAttribute("Employee");
			model.addAttribute("id", employee.getEmployeeAccount());
			model.addAttribute("deptno", employee.getDepartMent());
		} else {
			Manager manager = (Manager) session.getAttribute("Manager");
			model.addAttribute("id", manager.getManagerId());
			model.addAttribute("deptno", null);
		}
		return "/filemanager/sendfile";
	}

	@PostMapping("/fileup")
	public String fileup(@ModelAttribute Matter matter) {
		try {
			Editor.docFile(matter.getMattercontent(), matter.getMatterName());
		} catch (Exception e) {
			System.out.println("写入失败");
			e.printStackTrace();
		}
		matter.setMatterTime(new Date());
		matterService.save(matter);
		return "redirect:./send";
	}

	// look file
	@GetMapping("/look")
	public String lookmain(ModelMap model, HttpSession session) {
		String deptnoId = null;
		String publicId = "40";
		try {
			if (null != session.getAttribute("Employee")) {
				Employee employee = (Employee) session.getAttribute("Employee");
				deptnoId = employee.getDepartMent();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 得到当前部门所有文件
		List<Matter> deptnoFile = null;
		if (deptnoId != null) {
			deptnoFile = matterService.findBydeptnoId(deptnoId);
		}
		// 得到公网所有文件
		List<Matter> publicFile = matterService.findBydeptnoId(publicId);
		// 写入model
		model.addAttribute("deptnoFile", deptnoFile);
		model.addAttribute("publicFile", publicFile);
		return "/filemanager/file";
	}

	// 验证文件密码是否正确
	@PostMapping("verifypassword")
	@ResponseBody
	public String verifypassword(@RequestParam String ispassword, @RequestParam String matterid,HttpSession session) {
		Matter matter = matterService.findBypasswordAndmatterid(ispassword, Integer.parseInt(matterid));
		if (matter != null) { 					// 判断密码输入是否失败
			session.setAttribute("file", matter);
			return "yes";   //去显示页面
		}
		return "no";
	}
	
	
	/**
	 *密码成功,查看内容的界面
	 * */
	@GetMapping("/passwordsuccess")
	public String passwordSuccess(HttpSession session,ModelMap model) {
		Matter matter=(Matter) session.getAttribute("file");   //文件内容
		String userString=null; 							   //当前上传人id
		if(null!=session.getAttribute("Employee")) {           
			Employee employee=(Employee) session.getAttribute("Employee");
			userString=employee.getEmployeeAccount();
		}else {
			Manager manager=(Manager) session.getAttribute("Manager");
			userString=manager.getManagerId()+"";
		}
		if ((matter.getMatterUserid()+"").equals(userString)) {		//判断权限
			model.addAttribute("userid", "yes");					//给与权限	
		}else {
			model.addAttribute("userid", "no");
		}
		model.addAttribute("matter",matter);
		return "/filemanager/fileshow";
	}
	
	@PostMapping("/updatefile")
	@ResponseBody
	public String updateFile(@ModelAttribute Matter matter) {
		try {
			Editor.docFile(matter.getMattercontent(), matter.getMatterName());
		} catch (Exception e) {
			System.out.println("写入失败");
			e.printStackTrace();
		}
		matterService.save(matter);
		return "yes";
	}
}
