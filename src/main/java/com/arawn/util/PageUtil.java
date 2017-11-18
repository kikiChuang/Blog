package com.arawn.util;

/**
 * ��ҳ������
 * @author Administrator
 *
 */
public class PageUtil {

	/**
	 * ���ɷ�ҳ����
	 * @param targetUrl Ŀ���ַ
	 * @param totalNum �ܼ�¼��
	 * @param currentPage ��ǰҳ
	 * @param pageSize ÿҳ��С
	 * @param param ��ѯ�Ĳ���
	 * @return
	 */
	public static String genPagination(String targetUrl, int totalNum, int currentPage, int pageSize, String param) {
		int totalPage=totalNum%pageSize==0? totalNum/pageSize:totalNum/pageSize+1;
		if(totalPage==0) {
			return "<font color='red'>δ��ѯ�����ݣ�</font>";
		}
		StringBuffer pageCode = new StringBuffer();
		pageCode.append("<li><a href='"+targetUrl+"?page=1"+param+"'>��ҳ</a></li>");
		if(currentPage>1) {
			pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage-1)+param+"'>��һҳ</a></li>");			
		} else {
			pageCode.append("<li class='disabled'><a href='javascript:void(0)'>��һҳ</a></li>");		
		}
		
		// Ĭ����ʾ5ҳ
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
			pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage+1)+param+"'>��һҳ</a></li>");		
		} else {
			pageCode.append("<li class='disabled'><a href='javascript:void(0)'>��һҳ</a></li>");	
		}
		pageCode.append("<li><a href='"+targetUrl+"?page="+totalPage+param+"'>βҳ</a></li>");
		return pageCode.toString();
	}
	
	/**
	 * ��ȡ��һҳ����һҳ����
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
			return "<font color='red'>δ��ѯ�����ݣ�</font>";
		}
		pageCode.append("<nav>");
		pageCode.append("<ul class='pager'>");
		if(page>1) {
			pageCode.append("<li><a href='"+projectContext+"/blog/search.html?page="+(page-1)+"&q="+q+"'>��һҳ</a></li>");
		} else {
			pageCode.append("<li class='disabled'><a href='javascript:void(0)'>��һҳ</a></li>");
		}
		
		if(page<totalPage) {
			pageCode.append("<li><a href='"+projectContext+"/blog/search.html?page="+(page+1)+"&q="+q+"'>��һҳ</a></li>");
		} else {
			pageCode.append("<li class='disabled'><a href='javascript:void(0)'>��һҳ</a></li>");
		}
		pageCode.append("</ul>");
		pageCode.append("</nav>");
		return pageCode.toString();
	}
}
