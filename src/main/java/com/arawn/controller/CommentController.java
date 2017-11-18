package com.arawn.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.arawn.entity.Blog;
import com.arawn.entity.Comment;
import com.arawn.service.BlogService;
import com.arawn.service.CommentService;
import com.arawn.util.ResponseUtil;

/**
 * ����Controller��
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Resource
	private CommentService commentService;
	
	@Resource
	private BlogService blogService;
	
	/**
	 * �������
	 * @param comment
	 * @param imageCode
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public void save(Comment comment,@RequestParam(value="imageCode") String imageCode, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		String sRand = (String)session.getAttribute("sRand");
		JSONObject result = new JSONObject();
		if(!imageCode.equals(sRand)) {
			result.put("success", false);
			result.put("errorInfo", "��֤����д����");
		} else {
			String userIp = request.getRemoteAddr();  // ��ȡ�û�IP
			comment.setUserIp(userIp);
			int resultTotal = commentService.add(comment);
			if(resultTotal>0) {
				Blog blog = blogService.findById(comment.getBlog().getId());
				blog.setReplyHit(blog.getReplyHit()+1);  // ���͵Ļظ�������1
				blogService.update(blog);
				result.put("success", true);
			} else {
				result.put("success", false);
			}
		}
		ResponseUtil.write(response, result);
	}
}
