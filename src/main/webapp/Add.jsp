<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>添加商品</title>
<script type="text/javascript">
		
		function check(){
			var m = document.getElementsByTagName("input");
			for(var i=0;i<m.length;i++){
				if(m[i].value==""||m[i].value==null){
					alert("不能有空");
					return false;
				}
			}
			return true;
		}
</script>
</head>
<body>
	<form name="form" action="CommServlet?method=add" method="post" onSubmit="return check()">
		<div>输入基本信息</div>
		id ：<input type=text id="1" name="id" value=""><br/>
		类别id：<input type=text id="2" name="class_id"><br/>
		标题：<input type=text id="3" name="title"><br/>
		价格：<input type=text id="4" name="price"><br/>
		产地：<input type=text id="5" name="place"><br/>
		度数：<input type=text id="6" name="alcohol"><br/>
		简介：<input type=text id="7" name="content"><br/>
		<input type=submit value="添加">
	</form>
</body>
</html>