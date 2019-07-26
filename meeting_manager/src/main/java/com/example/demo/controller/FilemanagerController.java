package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.common.util.Editor;
import com.example.demo.common.util.NumberPage;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Manager;
import com.example.demo.entity.Matter;
import com.example.demo.service.MatterService;

/*文件控制层*/
@Controller
@RequestMapping("/filemanager")
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
		//文件密码加密
		matter.setMatterPassword((new SimpleHash("MD5",matter.getMatterPassword(),ByteSource.Util.bytes("123"),1024)).toString());
		matterService.save(matter);
		return "redirect:./send";
	}

	// look file
	@GetMapping(value= {"/look"})
	public String lookmain(@RequestParam(defaultValue="0",name="id")Integer id,@RequestParam(defaultValue="1") Integer deptno,ModelMap model, HttpSession session) {
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
		
		Integer deptnoCountInteger=null;	 				//部门总条数
		Integer publicCountInteger=null;					//公网总条数
		if(deptnoId!=null) {
			deptnoCountInteger=matterService.count(deptnoId);		
		}
		if(publicId!=null) {
			publicCountInteger=matterService.count(publicId);		
		}
		
		Integer deptnoPage=null;									//部门总页数
		if(deptnoCountInteger!=null) {
			deptnoPage=deptnoCountInteger/NumberPage.Nnumberpage;	
			if(deptnoCountInteger%NumberPage.Nnumberpage!=0) {
				deptnoPage++;
			}
		}
		Integer publicPage=null;									//公网总页数
		if(publicCountInteger!=null) {
			 publicPage=publicCountInteger/NumberPage.Nnumberpage;	
			if(publicCountInteger%NumberPage.Nnumberpage!=0) {
				publicPage++;
			}
		}
		
		if(deptnoPage==null) {
			model.addAttribute("deptnoPage",0);					//部门总页数
		}else {
			model.addAttribute("deptnoPage",deptnoPage);		

		}
		if(publicPage==null) {
			model.addAttribute("publicPage",0);				 	//公共总页数
		}else {
			model.addAttribute("publicPage",publicPage);		

		}
		model.addAttribute("id",id);					   		 //当前页数

		// 得到当前部门所有文件
		Page<Matter>  deptnoFile = null;
		List<Matter>  deptnoList = null;
		
		// 得到公网所有文件
		Page<Matter>  publicFile =null;
		List<Matter>  publicList =null;
		if (deptnoId != null) {						//部门文件
			if(deptno==0) {
				deptnoFile =matterService.Paging(id,NumberPage.Nnumberpage,Integer.parseInt(deptnoId));
			}else {
				deptnoFile =matterService.Paging(0,NumberPage.Nnumberpage,Integer.parseInt(deptnoId));
			}
		}
		
		if(deptno==1) {								//公共文件
			 publicFile =  matterService.Paging(id, NumberPage.Nnumberpage,Integer.parseInt(publicId));
		}else {
			 publicFile =  matterService.Paging(0, NumberPage.Nnumberpage,Integer.parseInt(publicId));
		}
		// 写入model
		model.addAttribute("deptnoFile", deptnoFile);		//部门文件(分页)
		model.addAttribute("publicFile", publicFile);		//公共文件(分页)
		model.addAttribute("deptnoList", deptnoList);		//部门文件(不分页)
		model.addAttribute("publicList", publicList);		//公共文件(不分页)
		model.addAttribute("deptno",deptno);				//首页是哪个nav
		
		return "/filemanager/file1";
	}

	/** 验证文件密码是否正确 */
	@PostMapping("verifypassword")
	@ResponseBody
	public String verifypassword(@RequestParam String ispassword, @RequestParam String matterid, HttpSession session) {
		Matter matter = matterService.findBypasswordAndmatterid(ispassword, Integer.parseInt(matterid));
		if (matter != null) { // 判断密码输入是否失败
			session.setAttribute("file", matter);
			return "yes"; // 去显示页面
		}
		return "no";
	}

	/** 无密码 */
	@PostMapping("nopassword")
	@ResponseBody
	public String nopassword(@RequestParam String matterid, HttpSession session) {
		Matter matter = matterService.findBymatterId(Integer.parseInt(matterid));
		if (matter != null) { // 判断密码输入是否失败
			session.setAttribute("file", matter);
			return "yes"; // 去显示页面
		}
		return "no";
	}

	/**
	 *查看内容的界面
	 */
	@GetMapping("/passwordsuccess")
	public String passwordSuccess(HttpSession session, ModelMap map) {
		Matter matter = (Matter) session.getAttribute("file"); // 文件内容
		String userString = null; 							   // 当前上传人id
		String sub_deptno = null;							   //当前上传人的部门
		if (null != session.getAttribute("Employee")) {
			Employee employee = (Employee) session.getAttribute("Employee");
			userString = employee.getEmployeeAccount();
			sub_deptno=employee.getDepartMent();
		} else {
			Manager manager = (Manager) session.getAttribute("Manager");
			userString = manager.getManagerId() + "";
			sub_deptno=null;
		}
		map.addAttribute("matter", matter); // 存储在model
		map.addAttribute("Sub_department",sub_deptno); //当前登陆人的部门
		if ((matter.getMatterUserid() + "").equals(userString)) { // 判断权限
			return "/filemanager/fileshowAll";
		} else {
			return "/filemanager/fileshow";
		}
	}

	/**
	 * 更新文件内容
	 */
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
	
	/**
	 * 下载文件,默认存放桌面
	 * */
	@GetMapping("downfile/{id}")
	@ResponseBody
	public String downfile(@PathVariable Integer id) {
		Matter matter=matterService.findBymatterId(id);
		Editor.downfile(matter.getMatterName());
		return "成功";
	}
}
