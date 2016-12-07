$(document).ready(function(){
	$.ajax({
		url:"/user/loginStatus?time="+new Date().getTime(),
		dataType:"json",
		success:function(data){
			if(data.result=='0'){
				//处理
				var userName=getCookie("userName");
				if(typeof(userName)=='undefined'||userName==null||userName==''){
					$("#loginDiv").css("visibility","visible");
				}else{
					userName=decodeURI(userName);
					var password=decodeURI(getCookie("password"));
					$.ajax({
						url:"/user/login",
						type:"post",
						data:"userName="+userName+"&password="+password,
						dataType:"json",
						success:function(data){
							if(data.status=='0'){
								//登陆后的处理
								//window.location.href=data.jump_url;
								$("#loginName").text(userName);
								$("#loginDiv2").css("display","block");
							}else if(data.status=='1'){
								//删了cookie 反正都是错的数据
								delCookie("userName");
								delCookie("password");
								$("#loginDiv").css("display","block");
							}else{
								$("#loginDiv").css("display","block");
							}
						},error:function(){
							$("#loginDiv").css("display","block");
						}
					});
				}
			}else {
				//登陆后的处理
				//$("#indexa").attr("href","/user/openIndex");
				$("#loginName").text(data.userName);
				$("#loginDiv2").css("display","block");
			}
		},error:function(){
			$("#loginDiv").css("display","block");
		}
	});
});