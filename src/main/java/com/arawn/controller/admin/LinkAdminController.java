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

import com.arawn.entity.Link;
import com.arawn.entity.PageBean;
import com.arawn.service.LinkService;
import com.arawn.util.ResponseUtil;

/**
 * 管理员友情链接Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/link")
public class LinkAdminController {

	@Resource
	private LinkService linkService;
	
	/**
	 * 分页查询友情链接信息
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
		List<Link> linkList = linkService.list(map);
		Long total = linkService.getTotal(map);
		JSONObject result = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(linkList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
	}
	
	/**
	 * 添加或者修改友情链接信息
	 * @param link
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public void save(Link link, HttpServletResponse response) throws Exception {
		int resultTotal = 0;
		if(link.getId()==null) {
			resultTotal = linkService.add(link);
		} else {
			resultTotal = linkService.update(link);
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
	 * 友情链接信息删除
	 * @param idStr
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public void delete(@RequestParam(value="idStr") String idStr, HttpServletResponse response) throws Exception {
		String[] idArr = idStr.split(",");
		JSONObject result = new JSONObject();
		for(int i=0; i<idArr.length; i++) {						
			linkService.delete(Integer.parseInt(idArr[i]));
		}
		result.put("success", true);
		ResponseUtil.write(response, result);
	}
}
