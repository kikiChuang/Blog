package com.arawn.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.arawn.entity.BlogType;
import com.arawn.entity.PageBean;
import com.arawn.service.BlogService;
import com.arawn.service.BlogTypeService;
import com.arawn.util.ResponseUtil;

/**
 * 管理员博客类别Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/blogType")
public class BlogTypeAdminController {

	@Resource
	private BlogTypeService blogTypeService;
	
	@Resource
	private BlogService blogService;
	
	/**
	 * 分页查询博客类别信息
	 * @param page
	 * @param rows
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public void list(@RequestParam(value="page") String page, @RequestParam(value="rows") String rows, HttpServletResponse response) throws Exception {
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<BlogType> blogTypeList = blogTypeService.list(map);
		Long total = blogTypeService.getTotal(map);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(blogTypeList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
	}
	
	/**
	 * 添加或者修改博客类别信息
	 * @param blogType
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public void save(BlogType blogType, HttpServletResponse response) throws Exception {
		int resultTotal = 0;
		if(blogType.getId()==null) {
			resultTotal = blogTypeService.add(blogType);
		} else {
			resultTotal = blogTypeService.update(blogType);
		}
		JSONObject result = new JSONObject();
		if(resultTotal>0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
	}
	
	/**
	 * 博客类别信息删除
	 * @param idStr
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public void delete(@RequestParam(value="idStr") String idStr, HttpServletResponse response) throws Exception {
		String[] idArr = idStr.split(",");
		JSONObject result = new JSONObject();
		for(int i=0; i<idArr.length; i++) {
			if(blogService.getBlogByTypeId(Integer.parseInt(idArr[i]))>0) {
				result.put("exist", true);
			} else {				
				blogTypeService.delete(Integer.parseInt(idArr[i]));
			}
		}
		result.put("success", true);
		ResponseUtil.write(response, result);
	}
}
