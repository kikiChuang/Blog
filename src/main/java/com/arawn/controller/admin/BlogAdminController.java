package com.arawn.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import com.arawn.entity.Blog;
import com.arawn.entity.PageBean;
import com.arawn.lucene.BlogIndex;
import com.arawn.service.BlogService;
import com.arawn.util.ResponseUtil;
import com.arawn.util.StringUtil;

/**
 * 管理员博客Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {

	@Resource
	private BlogService blogService;
	
	private BlogIndex blogIndex = new BlogIndex();
	
	/**
	 * 添加或者修改博客信息
	 * @param blog
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public void save(Blog blog, HttpServletResponse response) throws Exception {
		blog.setSummary(HtmlUtils.htmlEscape(blog.getSummary()));
		blog.setContentNoTag(HtmlUtils.htmlEscape(blog.getContentNoTag()));
		
		int resultTotal = 0;
		if(blog.getId()==null) {
			resultTotal = blogService.add(blog);
			blogIndex.addIndex(blog);
		} else {
			resultTotal = blogService.update(blog);
			blogIndex.updateIndex(blog);
		}
		JSONObject result = new JSONObject();
		if(resultTotal>0) {
			result.put("success", true);
		} else{
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
	}
	
	/**
	 * 分页查询博客信息
	 * @param page
	 * @param rows
	 * @param s_blog
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public void list(@RequestParam(value="page") String page, @RequestParam(value="rows") String rows, Blog s_blog, HttpServletResponse response) throws Exception {
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("title", StringUtil.formatLike(s_blog.getTitle()));
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<Blog> blogList = blogService.list(map);
		Long total = blogService.getTotal(map);
		JSONObject result = new JSONObject();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray = JSONArray.fromObject(blogList, jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
	}
	
	/**
	 * 博客信息删除
	 * @param idStr
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public void delete(@RequestParam(value="idStr") String idStr, HttpServletResponse response) throws Exception {
		String[] idArr = idStr.split(",");
		for(int i=0; i<idArr.length; i++) {
			blogService.delete(Integer.parseInt(idArr[i]));
			blogIndex.deleteIndex(idArr[i]);
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
	}
	
	/**
	 * 通过id查找实体
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/findById")
	public void findById(@RequestParam(value="id") String id, HttpServletResponse response) throws Exception {
		Blog blog = blogService.findById(Integer.parseInt(id));
		JSONObject result = JSONObject.fromObject(blog);
		ResponseUtil.write(response, result);
	}
}
