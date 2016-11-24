$(document).ready(function(){
	$.ajax({
		url:"/user/loginStatus",
		success:function(data){
			if(data=='0'){
				//处理
				var userName=getCookie("userName");
				if(typeof(userName)=='undefined'||userName==null||userName==''){
				}else{
					userName=BASE64.decoder(userName);
					var password=BASE64.decoder(getCookie("password"));
					$.ajax({
						url:"/user/login",
						type:"post",
						data:"userName="+userName+"&password="+password,
						dataType:"json",
						success:function(data){
							if(data.status=='0'){
								//登陆后的处理
							}else if(data.status=='1'){
								//删了cookie 反正都是错的数据
								delCookie("userName");
								delCookie("password");
							}
						}
					});
				}
			}else {
				//登陆后的处理
			}
		}
	});
});