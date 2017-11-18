package com.arawn.util;

/**
 * 分页工具类
 * @author Administrator
 *
 */
public class PageUtil {

	/**
	 * 生成分页代码
	 * @param targetUrl 目标地址
	 * @param totalNum 总记录数
	 * @param currentPage 当前页
	 * @param pageSize 每页大小
	 * @param param 查询的参数
	 * @return
	 */
	public static String genPagination(String targetUrl, int totalNum, int currentPage, int pageSize, String param) {
		int totalPage=totalNum%pageSize==0? totalNum/pageSize:totalNum/pageSize+1;
		if(totalPage==0) {
			return "<font color='red'>未查询到数据！</font>";
		}
		StringBuffer pageCode = new StringBuffer();
		pageCode.append("<li><a href='"+targetUrl+"?page=1"+param+"'>首页</a></li>");
		if(currentPage>1) {
			pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage-1)+param+"'>上一页</a></li>");			
		} else {
			pageCode.append("<li class='disabled'><a href='javascript:void(0)'>上一页</a></li>");		
		}
		
		// 默认显示5页
		int startPage = 0;
		int endPage = 0;
		int showPage = 5;
				
		if(totalPage<=showPage) {
			startPage = 1;
			endPage = totalPage;
		} else {
			startPage = currentPage-2;
			endPage = currentPage+2;
				
			if(startPage<1) {
				startPage = 1;
				endPage = showPage;
			}
			if(endPage>totalPage) {
				endPage = totalPage;
				startPage = totalPage-(showPage-1);
			}
		}
			
		for(int i=startPage;i<=endPage;i++) {
			if(i==currentPage) {
				pageCode.append("<li class='active'><a href='"+targetUrl+"?page="+i+param+"'>"+i+"</a></li>");	
			} else {
				pageCode.append("<li><a href='"+targetUrl+"?page="+i+param+"'>"+i+"</a></li>");	
			}
		}
		if(currentPage<totalPage) {
			pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage+1)+param+"'>下一页</a></li>");		
		} else {
			pageCode.append("<li class='disabled'><a href='javascript:void(0)'>下一页</a></li>");	
		}
		pageCode.append("<li><a href='"+targetUrl+"?page="+totalPage+param+"'>尾页</a></li>");
		return pageCode.toString();
	}
	
	/**
	 * 获取上一页，下一页代码
	 * @param page
	 * @param totalNum
	 * @param q
	 * @param pageSize
	 * @param projectContext
	 * @return
	 */
	public static String genUpAndDownPageCode(int page, int totalNum, String q, int pageSize, String projectContext) {
		int totalPage=totalNum%pageSize==0? totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode = new StringBuffer();
		if(totalPage==0) {
			return "<font color='red'>未查询到数据！</font>";
		}
		pageCode.append("<nav>");
		pageCode.append("<ul class='pager'>");
		if(page>1) {
			pageCode.append("<li><a href='"+projectContext+"/blog/search.html?page="+(page-1)+"&q="+q+"'>上一页</a></li>");
		} else {
			pageCode.append("<li class='disabled'><a href='javascript:void(0)'>上一页</a></li>");
		}
		
		if(page<totalPage) {
			pageCode.append("<li><a href='"+projectContext+"/blog/search.html?page="+(page+1)+"&q="+q+"'>下一页</a></li>");
		} else {
			pageCode.append("<li class='disabled'><a href='javascript:void(0)'>下一页</a></li>");
		}
		pageCode.append("</ul>");
		pageCode.append("</nav>");
		return pageCode.toString();
	}
}
