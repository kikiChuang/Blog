package com.arawn.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.arawn.entity.Blogger;
import com.arawn.service.BloggerService;
import com.arawn.util.CryptographyUtil;

/**
 * 博主Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/blogger")
public class BloggerController {

	@Resource
	private BloggerService bloggerService;
	
	/**
	 * 博主登录
	 * @param blogger
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String login(Blogger blogger, HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(blogger.getUserName(), CryptographyUtil.md5(blogger.getPassword(), CryptographyUtil.SALT));
		try {			
			subject.login(token);
			return "redirect:/admin/main.jsp";
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("blogger", blogger);
			request.setAttribute("errorInfo", "用户名或密码错误！");
			return "login";
		}
	}
	
	/**
	 * 关于博主
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/aboutMe")
	public ModelAndView abountMe() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("mainTitle", "关于博主_Java博客系统");
		mav.addObject("mainPage", "/foreground/blogger/info.jsp");
		mav.setViewName("mainTemp");
		return mav;
	}
}
