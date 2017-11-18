package com.arawn.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.arawn.entity.Blog;
import com.arawn.entity.BlogType;
import com.arawn.entity.Blogger;
import com.arawn.entity.Link;
import com.arawn.service.BlogService;
import com.arawn.service.BlogTypeService;
import com.arawn.service.BloggerService;
import com.arawn.service.LinkService;

@Component
public class InitComponent extends ContextLoaderListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		super.contextInitialized(sce);
		
		ServletContext application = sce.getServletContext();
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(application); 
		
		BloggerService bloggerService = (BloggerService)applicationContext.getBean("bloggerService");
		Blogger blogger = bloggerService.find();  // 获取博主信息
		blogger.setPassword(null);
		application.setAttribute("blogger", blogger);
		
		LinkService linkService = (LinkService)applicationContext.getBean("linkService");
		List<Link> linkList = linkService.list(null);  // 查询所有的友情链接信息
		application.setAttribute("linkList", linkList);
		
		BlogTypeService blogTypeService = (BlogTypeService)applicationContext.getBean("blogTypeService");
		List<BlogType> blogTypeCountList = blogTypeService.countList();  // 查询博客类别以及博客的数量
		application.setAttribute("blogTypeCountList", blogTypeCountList);
		
		BlogService blogService = (BlogService)applicationContext.getBean("blogService");
		List<Blog> blogCountList = blogService.countList();  // 根据日期年月分组查询
		application.setAttribute("blogCountList", blogCountList);
		
	}

}
