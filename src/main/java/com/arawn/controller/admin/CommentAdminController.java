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

import com.arawn.entity.Comment;
import com.arawn.entity.PageBean;
import com.arawn.service.CommentService;
import com.arawn.util.ResponseUtil;

/**
 * 管理员评论Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/comment")
public class CommentAdminController {

	@Resource
	private CommentService commentService;
	
	/**
	 * 分页查询评论信息
	 * @param page
	 * @param rows
	 * @param state
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public void list(@RequestParam(value="page") String page, @RequestParam(value="rows") String rows, @RequestParam(value="state",required=false) Integer state, HttpServletResponse response) throws Exception {
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("state", state); // 评论状态
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<Comment> commentList = commentService.list(map);
		Long total = commentService.getTotal(map);
		JSONObject result = new JSONObject();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray = JSONArray.fromObject(commentList, jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
	}
	
	/**
	 * 评论信息审核
	 * @param idStr
	 * @param state
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/review")
	public void review(@RequestParam(value="idStr") String idStr, @RequestParam(value="state") Integer state, HttpServletResponse response) throws Exception {
		String[] idArr = idStr.split(",");
		for(int i=0; i<idArr.length; i++) {
			Comment comment = new Comment();
			comment.setId(Integer.parseInt(idArr[i]));
			comment.setState(state);
			commentService.update(comment);
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
	}
	
	/**
	 * 评论信息删除
	 * @param idStr
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public void delete(@RequestParam(value="idStr") String idStr, HttpServletResponse response) throws Exception {
		String[] idArr = idStr.split(",");
		for(int i=0; i<idArr.length; i++) {
			commentService.delete(Integer.parseInt(idArr[i]));
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
	}
}
