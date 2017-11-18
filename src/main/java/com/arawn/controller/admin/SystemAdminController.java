package com.arawn.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.arawn.entity.Blog;
import com.arawn.entity.BlogType;
import com.arawn.entity.Blogger;
import com.arawn.entity.Link;
import com.arawn.service.BlogService;
import com.arawn.service.BlogTypeService;
import com.arawn.service.BloggerService;
import com.arawn.service.LinkService;
import com.arawn.util.ResponseUtil;

/**
 * 管理员系统Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/system")
public class SystemAdminController {

	@Resource
	private BloggerService bloggerService;
	
	@Resource
	private LinkService linkService;
	
	@Resource
	private BlogTypeService blogTypeService;
	
	@Resource
	private BlogService blogService;
	
	/**
	 * 刷新系统缓存
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/refreshSystem")
	public void refreshSystem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext application = RequestContextUtils.getWebApplicationContext(request).getServletContext();
		Blogger blogger = bloggerService.find();  // 获取博主信息
		blogger.setPassword(null);
		application.setAttribute("blogger", blogger);
		
		List<Link> linkList = linkService.list(null);  // 查询所有的友情链接信息
		application.setAttribute("linkList", linkList);
		
		List<BlogType> blogTypeCountList = blogTypeService.countList();  // 查询博客类别以及博客的数量
		application.setAttribute("blogTypeCountList", blogTypeCountList);
		
		List<Blog> blogCountList = blogService.countList();  // 根据日期年月分组查询
		application.setAttribute("blogCountList", blogCountList);
		
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
	}
}
