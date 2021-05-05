<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page language="java" import="java.util.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script src="https://how2j.cn/study/js/jquery/2.0.0/jquery.min.js"></script>
<link href="https://how2j.cn/study/css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
<script src="https://how2j.cn/study/js/bootstrap/3.3.6/bootstrap.min.js"></script>

<script>
$(function(){
    $("a").addClass("btn btn-default btn-xs");
     
});
 
</script>
</head>
<body>
<div id="MainArea">
    <table style="width:500px; margin:44px auto" class="table table-striped table-bordered table-hover  table-condensed" align='center' border='1' cellspacing='0'>
        <!-- 表头-->
        <thead>
            <tr align="center" valign="middle" id="TableTitle">
				<td>ID</td>
				<td>类别ID</td>
				<td>标题</td>
				<td>价格</td>
				<td>产地</td>
				<td>酒精度</td>
				<td>简介</td>
			</tr>
		</thead>	
		<!--显示数据列表 -->
        <tbody id="TableData"> 		
				<c:choose>
					<c:when test="${not empty result.list}">
						<c:forEach var="result" items="${result.list}">
							<tr class="TableDetail1">
								<td>${result.id}</td>
								<td>${result.class_id}</td>
								<td>${result.title}</td>
								<td>${result.price}</td>
								<td>${result.place}</td>
								<td>${result.alcohol}</td>
								<td>${result.content}</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="TableDetail1">
							<td colspan="5" style="text-align:center">没有你要找的数据，请先添加记录再查看！</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
    </table>
     <!-- 其他功能超链接 -->
		<nav id="TableTail" align="center">
			<div class="FunctionButton"><a href="${pageContext.request.contextPath }//Add.jsp">添加</a></div>
			<ul class="pager">
				<li><a href="${pageContext.request.contextPath }/CommServlet?currentPage=1&method=search">首页</a></li>
				<li><a href="${pageContext.request.contextPath }/CommServlet?currentPage=${result.PageDate.currentPage-1}&method=search">上一页</a></li>
				<li><a>${result.PageDate.currentPage}/${result.PageDate.totalPage}</a></li>
				<li><a href="${pageContext.request.contextPath }/CommServlet?currentPage=${result.PageDate.currentPage+1}&method=search">下一页</a></li>
				<li><a href="${pageContext.request.contextPath }/CommServlet?currentPage=${result.PageDate.totalPage}&method=search">末页</a></li>
			</ul>
	    </nav>	
</div>
</body>
</html>