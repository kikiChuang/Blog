package com.arawn.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * Response工具类
 * @author Administrator
 *
 */
public class ResponseUtil {

	/**
	 * 向页面输出数据
	 * @param response
	 * @param o
	 * @throws Exception
	 */
	public static void write(HttpServletResponse response, Object o) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(o.toString());
		out.flush();
		out.close();
	}
}
