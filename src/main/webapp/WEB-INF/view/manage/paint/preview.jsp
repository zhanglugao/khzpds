<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科幻作品大赛</title>
<jsp:include page="../common/manage_inc.jsp"></jsp:include>
<meta content='width=device-width,initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
<script type="text/javascript">

	$(document).ready(function(){
		if(typeof(parent)!="undefined"){
		    $("#iframe").css("height",parent.innerHeight+"px");
		    $("body").click(function(){
		       parent.layer.closeAll();
			});
		}
	});

</script>
</head>
<body>
	<iframe id="iframe" src="${filePath}" style="width:100%;">

	</iframe>
</body>
</html>