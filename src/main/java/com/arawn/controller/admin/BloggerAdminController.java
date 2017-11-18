package com.arawn.controller.admin;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.arawn.entity.Blogger;
import com.arawn.service.BloggerService;
import com.arawn.util.CryptographyUtil;
import com.arawn.util.DateUtil;
import com.arawn.util.ResponseUtil;

/**
 * 管理员博主Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/blogger")
public class BloggerAdminController {

	@Resource
	private BloggerService bloggerService;
	
	/**
	 * 查询博主信息
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/find")
	public void find(HttpServletResponse response) throws Exception {
		Blogger blogger = bloggerService.find();
		JSONObject result = JSONObject.fromObject(blogger);
		ResponseUtil.write(response, result);
	}
	
	/**
	 * 修改博主信息
	 * @param imageFile
	 * @param blogger
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public void save(@RequestParam(value="imageFile") MultipartFile imageFile, Blogger blogger, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(!imageFile.isEmpty()) {
			String filePath = request.getServletContext().getRealPath("/static/userImages");
			String imageName = DateUtil.getCurrentDateStr()+"."+imageFile.getOriginalFilename().split("\\.")[1];
			imageFile.transferTo(new File(filePath, imageName));
			blogger.setImageName(imageName);
		}
		int resultTotal = bloggerService.update(blogger);
		StringBuffer result = new StringBuffer();
		if(resultTotal>0) {
			result.append("<script type='text/javascript'>alert('修改成功！');</script>");
		} else {
			result.append("<script type='text/javascript'>alert('修改失败！');</script>");
		}
		ResponseUtil.write(response, result);
	}
	
	/**
	 * 修改博主密码
	 * @param newPassword
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/modifyPassword")
	public void modifyPassword(@RequestParam(value="newPassword") String newPassword, HttpServletResponse response) throws Exception {
		Blogger blogger = new Blogger();
		blogger.setPassword(CryptographyUtil.md5(newPassword, CryptographyUtil.SALT));
		int resultTotal = bloggerService.update(blogger);
		JSONObject result = new JSONObject();
		if(resultTotal>0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
	}
	
	/**
	 * 注销
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/logout")
	public String logout() throws Exception {
		SecurityUtils.getSubject().logout();
		return "redirect:/login.jsp";
	}
}
