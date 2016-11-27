$(document).ready(function(){
	$.ajax({
		url:"/user/loginStatus",
		success:function(data){
			if(data=='0'){
				//处理
				var userName=getCookie("userName");
				console.log(userName);
				if(typeof(userName)=='undefined'||userName==null||userName==''){
					console.log("cookie中没有数据");
					$("#loginDiv").css("visibility","visible");
				}else{
					console.log("cookie取出的userName 未解码:"+userName);
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
								console.log("通过cookie登陆成功了");
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
				console.log("登陆了");
			}
		},error:function(){
			$("#loginDiv").css("visibility","visible");
		}
	});
});