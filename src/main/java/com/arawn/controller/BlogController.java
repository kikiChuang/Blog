package com.arawn.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.arawn.entity.Blog;
import com.arawn.lucene.BlogIndex;
import com.arawn.service.BlogService;
import com.arawn.service.CommentService;
import com.arawn.util.PageUtil;
import com.arawn.util.StringUtil;

/**
 * 博客Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

	@Resource
	private BlogService blogService;
	
	@Resource
	private CommentService commentService;
	
	private BlogIndex blogIndex = new BlogIndex();
	
	/**
	 * 请求博客详细信息
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/detail/{id}")
	public ModelAndView details(@PathVariable("id") Integer id, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		Blog blog = blogService.findById(id);
		String keyWordStr = blog.getKeyWord();
		if(StringUtil.isNotEmpty(keyWordStr)) {
			String[] arr = keyWordStr.split(" ");
			mav.addObject("keyWords", StringUtil.filterWhite(Arrays.asList(arr)));
		} else {
			mav.addObject("keyWords", null);
		}
		blog.setClickHit(blog.getClickHit()+1);
		blogService.update(blog);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("blogId", blog.getId());
		map.put("state", 1);
		mav.addObject("commentList", commentService.list(map));
		mav.addObject("blog", blog);
		mav.addObject("pageCode", this.getUpAndDownBlogCode(blogService.getLastBlog(id), blogService.getNextBlog(id), request.getContextPath()));
		mav.addObject("mainTitle", blog.getTitle()+"_Java博客系统");
		mav.addObject("mainPage", "/foreground/blog/view.jsp");
		mav.setViewName("mainTemp");
		return mav;
	}
	
	/**
	 * 获取上一篇博客和下一篇博客
	 * @param lastBlog
	 * @param nextBlog
	 * @param projectContext
	 * @return
	 */
	private String getUpAndDownBlogCode(Blog lastBlog, Blog nextBlog, String projectContext) {
		StringBuffer pageCode = new StringBuffer();
		if(lastBlog==null || lastBlog.getId()==null) {
			pageCode.append("<p>上一篇：没有了</p>");
		} else {
			pageCode.append("<p>上一篇：<a href='"+projectContext+"/blog/detail/"+lastBlog.getId()+".html'>"+lastBlog.getTitle()+"</a></p>");
		}
		
		if(nextBlog==null || nextBlog.getId()==null) {
			pageCode.append("<p>下一篇：没有了</p>");
		} else{
			pageCode.append("<p>下一篇：<a href='"+projectContext+"/blog/detail/"+nextBlog.getId()+".html'>"+nextBlog.getTitle()+"</a></p>");
		}
		return pageCode.toString();
	}
	
	/**
	 * 根据关键字查询相关博客信息
	 * @param q
	 * @return
	 */
	@RequestMapping("/search")
	public ModelAndView search(@RequestParam(value="q") String q, @RequestParam(value="page",required=false) String page, HttpServletRequest request) throws Exception {
		q = HtmlUtils.htmlEscape(q);
		
		if(StringUtil.isEmpty(page)) {
			page = "1";
		}
		int pageSize = 10;
		ModelAndView mav = new ModelAndView();
		mav.addObject("mainTitle", "搜索关键字'"+q+"'的结果_Java博客系统");
		mav.addObject("mainPage", "/foreground/blog/result.jsp");
		List<Blog> blogList = blogIndex.searchBlog(q);
		int toIndex = blogList.size()>Integer.parseInt(page)*pageSize? Integer.parseInt(page)*pageSize:blogList.size();
		mav.addObject("blogList", blogList.subList((Integer.parseInt(page)-1)*pageSize, toIndex));
		mav.addObject("pageCode", PageUtil.genUpAndDownPageCode(Integer.parseInt(page), blogList.size(), q, pageSize, request.getContextPath()));
		mav.addObject("q", q);
		mav.addObject("resultTotal", blogList.size());
		mav.setViewName("mainTemp");
		return mav;
	}
	
	
}
