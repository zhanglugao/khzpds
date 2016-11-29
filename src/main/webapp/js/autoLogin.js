$(document).ready(function(){
	$.ajax({
		url:"/user/loginStatus",
		success:function(data){
			if(data=='0'){
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
								window.location.href=data.jump_url;
							}else if(data.status=='1'){
								//删了cookie 反正都是错的数据
								delCookie("userName");
								delCookie("password");
								$("#loginDiv").css("visibility","visible");
							}else{
								$("#loginDiv").css("visibility","visible");
							}
						},error:function(){
							$("#loginDiv").css("visibility","visible");
						}
					});
				}
			}else {
				//登陆后的处理
			}
		},error:function(){
			$("#loginDiv").css("visibility","visible");
		}
	});
});