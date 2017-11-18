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
 * ����ԱϵͳController��
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
	 * ˢ��ϵͳ����
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/refreshSystem")
	public void refreshSystem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext application = RequestContextUtils.getWebApplicationContext(request).getServletContext();
		Blogger blogger = bloggerService.find();  // ��ȡ������Ϣ
		blogger.setPassword(null);
		application.setAttribute("blogger", blogger);
		
		List<Link> linkList = linkService.list(null);  // ��ѯ���е�����������Ϣ
		application.setAttribute("linkList", linkList);
		
		List<BlogType> blogTypeCountList = blogTypeService.countList();  // ��ѯ��������Լ����͵�����
		application.setAttribute("blogTypeCountList", blogTypeCountList);
		
		List<Blog> blogCountList = blogService.countList();  // �����������·����ѯ
		application.setAttribute("blogCountList", blogCountList);
		
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
	}
}
