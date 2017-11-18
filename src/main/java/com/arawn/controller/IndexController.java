package com.arawn.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.arawn.entity.Blog;
import com.arawn.entity.PageBean;
import com.arawn.service.BlogService;
import com.arawn.util.PageUtil;
import com.arawn.util.StringUtil;

/**
 * 主页Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/")
public class IndexController {

	@Resource
	private BlogService blogService;
	
	/**
	 * 请求主页
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(@RequestParam(value="page",required=false) String page, @RequestParam(value="typeId",required=false) String typeId,
			@RequestParam(value="releaseDateStr",required=false) String releaseDateStr, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		if(StringUtil.isEmpty(page) || !StringUtil.isNumber(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("typeId", typeId);
		map.put("releaseDateStr", releaseDateStr);
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<Blog> blogList = blogService.list(map);
		for(Blog blog : blogList) {
			List<String> imageList = new LinkedList<String>();
			String blogInfo = blog.getContent();
			Document doc = Jsoup.parse(blogInfo);
			Elements jpgs = doc.select("img[src$=.jpg]");
			for(int i=0; i<jpgs.size(); i++) {
				Element jpg = jpgs.get(i);
				imageList.add(jpg.toString());
				if(i==2) {
					break;
				}
			}
			blog.setImageList(imageList);
		}
		mav.addObject("blogList", blogList);
		StringBuffer param = new StringBuffer();
		if(StringUtil.isNotEmpty(typeId)) {
			param.append("&typeId="+typeId);
		}
		if(StringUtil.isNotEmpty(releaseDateStr)) {
			param.append("&releaseDateStr="+releaseDateStr);
		}
		mav.addObject("pageCode", PageUtil.genPagination(request.getContextPath()+"/index.html", blogService.getTotal(map).intValue(), pageBean.getPage(), pageBean.getPageSize(), param.toString()));
		mav.addObject("mainTitle", "Java博客系统");
		mav.addObject("mainPage", "/foreground/blog/list.jsp");
		mav.setViewName("mainTemp");
		return mav;
	}
}
