<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="data_list">
	<div class="data_list_title">
		<img src="${pageContext.request.contextPath }/static/images/user_icon.png" />
		博主信息
	</div>
	<div class="user_image">
		<img src="${pageContext.request.contextPath }/static/userImages/${blogger.imageName}" />
	</div>
	<div class="nickName">${blogger.nickName }</div>
	<div class="userSign">(${blogger.sign })</div>
</div>

<div class="data_list">
	<div class="data_list_title">
		<img src="${pageContext.request.contextPath }/static/images/byType_icon.png" />
		按日志类别
	</div>
	<div class="datas">
		<ul>
			<c:forEach var="blogTypeCount" items="${blogTypeCountList }">
				<li><span><a href="${pageContext.request.contextPath }/index.html?typeId=${blogTypeCount.id}">${blogTypeCount.typeName }(${blogTypeCount.blogCount })</a></span></li>
			</c:forEach>
		</ul>
	</div>
</div>

<div class="data_list">
	<div class="data_list_title">
		<img src="${pageContext.request.contextPath }/static/images/byDate_icon.png" />
		按日志日期
	</div>
	<div class="datas">
		<ul>
			<c:forEach var="blogCount" items="${blogCountList }">
				<li><span><a href="${pageContext.request.contextPath }/index.html?releaseDateStr=${blogCount.releaseDateStr }">${blogCount.releaseDateStr }(${blogCount.blogCount })</a></span></li>
			</c:forEach>
		</ul>
	</div>
</div>

<div class="data_list">
	<div class="data_list_title">
		<img src="${pageContext.request.contextPath }/static/images/link_icon.png" />
		友情链接
	</div>
	<div class="datas">
		<ul>
			<c:forEach var="link" items="${linkList }">
				<li><span><a href="${link.linkUrl }" target="_blank">${link.linkName }</a></span></li>
			</c:forEach>
		</ul>
	</div>
</div>