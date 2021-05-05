<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page language="java" import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>查询</title>
<script type="text/javascript">

</script>
</head>
<body>

	<form action="CommServlet?method=search&save=clear" method=post name=form>
		id ：<input type=text name="id"><br/>
		类别id：<input type=text name=class_id><br/>
		标题：<input type=text name=title><br/>
		价格：<input type=text name=price><br/>
		产地：<input type=text name=place><br/>
		度数：<input type=text name=alcohol><br/>
		价格范围：<input type=text name=prs>~<input type=text name=pre><br/>
		度数范围：<input type=text name=als>~<input type=text name=ale><br/>
		<input type="submit" value="搜索">
	</form>
	
</body>
</html>