<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科幻画-报名表</title>
<link href="/webuploader/webuploader.css" rel="stylesheet" type="text/css">
<jsp:include page="../common/open_inc.jsp"></jsp:include>
<!-- <script type="text/javascript" src="js/Validform.js"></script> -->
<script type="text/javascript" src="/webuploader/webuploader.nolog.min.js"></script>
<style type="text/css">
.graph{
position:relative;
background-color:#F0EFEF;
border:1px solid #cccccc;
padding:2px;
font-size:13px;
font-weight:700;
}
.graph .orange, .green, .blue, .red, .black{
position:relative;
text-align:left;
color:#ffffff;
height:18px;
line-height:18px;
font-family:Arial;
display:block;
}
.graph .orange{background-color:#ff6600;}
.graph .green{background-color:#66CC33;}
.graph .blue{background-color:#3399CC;}
.graph .red{background-color:red;}
.graph .black{background-color:#555;}
</style>
<script type="text/javascript">
	var regBox = {
        regEmail : /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/,//邮箱
        regName : /^[a-z0-9_-]{3,16}$/,//用户名
        regMobile : /^0?1[3|4|5|7|8][0-9]\d{8}$/,//手机
        regTel : /^0[\d]{2,3}-[\d]{7,8}$/,
        regPostCode:/^[1-9][0-9]{5}$/
    }
 
    var mobile = '18758089867';
    var tel = '0575-7678899';
    var mflag = regBox.regMobile.test(mobile);
    var tflag = regBox.regTel.test(tel);
	$(document).ready(function(){
		<c:if test="${empty item && empty applyInfo}">
			layer.alert("没有可供报名的本类型比赛项目");
			$("form").css("display","none");
		</c:if>
		$("input[name=applyGroup]").click(function(){
			$("input[name=applyGroup]:checked").prop("checked",false);
			$(this).prop("checked",true);
		});
		$("input[name=applyYearGroup]").click(function(){
			$("input[name=applyYearGroup]:checked").prop("checked",false);
			$(this).prop("checked",true);
		});
		if($("#activityId").val()==''){
			$("#activityId").val("${item.activityId }");
		}
		if($("#competitionItemId").val()==''){
			$("#competitionItemId").val("${item.id }");
		}
		if($("#competitionType").val()==''){
			$("#competitionType").val("${item.type }");
		}
		
		if("${applyInfo.applyGroup}"!=''){
			$("input[name=applyGroup][value=${applyInfo.applyGroup}]").prop("checked",true);
		}
		if("${applyInfo.applyYearGroup}"!=''){
			$("input[name=applyYearGroup][value=${applyInfo.applyYearGroup}]").prop("checked",true);
		}
		if("${applyInfo.sex}"!=''){
			$("input[name=sex][value=${applyInfo.sex}]").prop("chceked",true);
		}
		
		if("${applyInfo.filePath}"!=''){
			$("#filePathHidden").val("${applyInfo.filePath}");
			$("#fileNameHidden").val("${applyInfo.fileName}");
			$("#fileNameDiv").html("${applyInfo.fileName}");
			$("#uploadStateDiv").html("${applyInfo.fileName}上传成功");
		}
	});
	function apply(){
		var productionName=$("#productionName").val();
		if($.trim(productionName)==''){
			$("#productionName").focus();
			layer.tips('请填写作品名称', '#productionName',{tips:[2,tipsColor]});return;
		}
		if($("input[name=applyGroup]:checked").length==0){
			document.getElementById("applyGroupTips").scrollIntoView();
			layer.tips('请选择参赛组别', '#applyGroupTips',{tips:[2,tipsColor]});return;
		}
		if($("input[name=applyYearGroup]:checked").length==0){
			document.getElementById("applyYearGroupTips").scrollIntoView();
			layer.tips('请选择参赛年龄组', '#applyYearGroupTips',{tips:[2,tipsColor]});return;
		}
		if($("input[name=applyGroup]:checked").val()=='303002'&&$("input[name=applyYearGroup]:checked").val()=='304001'){
			$("#applyYearGroupTips").focus();
			layer.tips('电脑绘图组限中学、大学两个年龄段', '#applyYearGroupTips',{tips:[2,tipsColor]});return;
		}
		var realName=$("#realName").val();
		if($.trim(realName)==''){
			$("#realName").focus();
			layer.tips('请填写姓名', '#realName',{tips:[2,tipsColor]});return;
		}
		var birthday=$("#birthday").val();
		if($.trim(birthday)==''){
			$("#birthday").focus();
			layer.tips('请选择出生年月', '#birthday',{tips:[2,tipsColor]});return;
		}
		var schoolName=$("#schoolName").val();
		if($.trim(schoolName)==''){
			$("#schoolName").focus();
			layer.tips('请填写所在学校', '#schoolName',{tips:[2,tipsColor]});return;
		}
		var recommenedCompany=$("#recommenedCompany").val();
		if($.trim(recommenedCompany)==''){
			$("#recommenedCompany").focus();
			layer.tips('请填写推荐单位，没有写无', '#recommenedCompany',{tips:[2,tipsColor]});return;
		}
		var cardNumber=$("#cardNumber").val();
		if($.trim(cardNumber)==''){
			$("#cardNumber").focus();
			layer.tips('请填写证件号码', '#cardNumber',{tips:[2,tipsColor]});return;
		}
		if($("#cardType").val()=='308001'&&!IdentityCodeValid(cardNumber)){
			$("#cardNumber").focus();
			layer.tips('请填写真实的身份证号,最后一位是X的必须大写', '#cardNumber',{tips:[2,tipsColor]});return;
		}
		var telephone=$("#telephone").val();
		if($.trim(telephone)==''){
			$("#telephone").focus();
			layer.tips('请填写联系电话', '#telephone',{tips:[2,tipsColor]});return;
		}
		var mflag = regBox.regMobile.test(telephone);
	    var tflag = regBox.regTel.test(telephone);
	    if (!mflag&&!tflag) {
	    	$("#telephone").focus();
	    	layer.tips('联系电话格式填写有误 例010-88888888', '#telephone',{tips:[2,tipsColor]});return;
	    }
		var mobilePhone=$("#mobilePhone").val();
		if($.trim(mobilePhone)==''){
			$("#mobilePhone").focus();
			layer.tips('请填写手机号码 ', '#mobilePhone',{tips:[2,tipsColor]});return;
		}
		mflag = regBox.regMobile.test(mobilePhone);
		if(!mflag){
			$("#mobilePhone").focus();
			layer.tips('手机号码格式填写有误', '#mobilePhone',{tips:[2,tipsColor]});return;
		}
		var email=$("#email").val();
		if($.trim(email)==''){
			$("#email").focus();
			layer.tips('请填写邮箱', '#email',{tips:[2,tipsColor]});return;
		}
		var emailFlag=regBox.regEmail.test(email);
		if(!emailFlag){
			$("#email").focus();
			layer.tips('邮箱格式填写有误', '#email',{tips:[2,tipsColor]});return;
		}
		var postcode=$("#postcode").val();
		if($.trim(postcode)==''){
			$("#postcode").focus();
			layer.tips('请填写邮编', '#postcode',{tips:[2,tipsColor]});return;
		}
		var postcodeFlag=regBox.regPostCode.test(postcode);
		if(!postcodeFlag){
			$("#postcode").focus();
			layer.tips('邮编格式填写有误', '#postcode',{tips:[2,tipsColor]});return;
		}
		var address=$("#address").val();
		if($.trim(address)==''){
			$("#address").focus();
			layer.tips('请填写通讯地址', '#address',{tips:[2,tipsColor]});return;
		}
		var ideaDesc=$("#ideaDesc").val();
		if($.trim(ideaDesc)==''){
			$("#ideaDesc").focus();
			layer.tips('请填写创意说明', '#ideaDesc',{tips:[2,tipsColor]});return;
		}
		if($("#fileNameHidden").val()==''){
			layer.tips('请选择作品', '#chooseProduct',{tips:[1,tipsColor]});return;
		}
		$.ajax({
			url:"/userApply/userApplyCompetitionItem",
			type:"post",
			dataType:"json",
			data:$("#form").serialize(),
			success:function(data){
				if(data.status=='0'){
					layer.msg("报名成功,2秒后回到首页",{icon:1});
					$("#applyDIv").css("display","none");
					x=2;
					x1=window.setInterval(changeSuccess,1000); 
				}else{
					layer.alert(data.error_desc);
				}
			},error:function(){
				layer.alert(errorText);
			}
		});
	}
	
	var x=2;
	var x1;
	function changeSuccess(){
		if(x<=0){
			window.location.href='/user/openIndex';
		}else{
			x=x-1;
		}
	}
	
	function IdentityCodeValid(code) {
        var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
        var tip = "";
        var pass= true;
        
        if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
            tip = "身份证号格式错误";
            pass = false;
        }
        
       else if(!city[code.substr(0,2)]){
            tip = "地址编码错误";
            pass = false;
        }
        else{
            //18位身份证需要验证最后一位校验位
            if(code.length == 18){
                code = code.split('');
                //∑(ai×Wi)(mod 11)
                //加权因子
                var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
                //校验位
                var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
                var sum = 0;
                var ai = 0;
                var wi = 0;
                for (var i = 0; i < 17; i++)
                {
                    ai = code[i];
                    wi = factor[i];
                    sum += ai * wi;
                }
                var last = parity[sum % 11];
                if(parity[sum % 11] != code[17]){
                    tip = "校验位错误";
                    pass =false;
                }
            }
        }
        //if(!pass) alert(tip);
        return pass;
    }
	
	var uploader=null;
	function openChoose(){
		if(uploader==null){
			initUploader();
		}
		layer.open({
			type: 1,
			content:$("#uploadDiv"),
			shadeClose: true,//开启遮罩关闭
			title:false,
			area: ['400px', '300px']
		});
	}
	var fileId=null;
	function initUploader(){
		uploader=WebUploader.create({
		   swf: '/webuploader/Uploader.swf',
		   server: '/productUpload/fileUpload',
		   threads:"1",
		   pick: {
		       id: '#picker',
		       multiple:false
		   },
		   formData:{uploadType:"draw"},
		   resize: false,
		   chunked:false,
		   accept: {
		       title: 'Images',
		       extensions: 'jpg,jpeg',
		       mimeTypes: 'image/jpg,image/jpeg'
		   },
		   duplicate:true,
		   auto:false
		});
		uploader.on("error",function(type){
			if(type=='Q_TYPE_DENIED'){
				layer.alert("文件类型错误");
			}
			if(type=='Q_EXCEED_NUM_LIMIT'){
				layer.alert("文件数量超出限制");
			}
			if(type=='Q_EXCEED_SIZE_LIMIT'){
				layer.alert("文件大小超出限制");
			}
		});
		uploader.on( 'fileQueued', function( file ) {
			if(fileId!=null){
				uploader.removeFile(fileId);
			}
			fileId=file.id;
			$("#fileNameDiv").html(file.name);
			$("#uploadStateDiv").html("");
			$("#uploadButton").removeAttr("disabled");
		});
		uploader.on("uploadBeforeSend",function(object,data,headers){
			var guid=WebUploader.Base.guid();
			data.guid=guid;
			object.file.guid=guid;
		});
		uploader.on("uploadAccept",function(object,ret){
			/* if(ret._raw=='fail'){
				return false;
			} */
			var data=eval("("+ret._raw+")");
			if(data.status=='0'){
				$("#filePathHidden").val(data.filePath);
			}else{return false;}
		});
		uploader.on( 'uploadProgress', function( file, percentage ) {
			 var $li = $( '#fileProgressDiv' ),
			    $percent = $li.find('#percentbar');
			    // 避免重复创建
			    if ( !$percent.length ) {
			        $percent = $('<div class="graph"><span id="percentbar" class="orange" style="width:0%;">0%</span></div>').appendTo( $li ).find('#percentbar');
	/* 		        $percent = $('<div class="progress progress-striped active">' +
			          '<div class="progress-bar" role="progressbar" style="width: 0%">' +
			          '</div>' +
			        '</div>').appendTo( $li ).find('.progress-bar'); */
			    }
			    $percent.css( 'width', percentage * 100 + '%' );
			    $percent.html( (percentage * 100 +"").substring(0,5)+ '%' );
		});
		uploader.on("uploadError",function(file,reason){
			$("#uploadStateDiv").html(file.name+'上传失败，请重试或联系管理员');
		});
		uploader.on("uploadSuccess",function(file,response){
			$("#uploadStateDiv").html(file.name+'上传成功');
			//$("#filePathHidden").val(file.guid);
			$("#fileNameHidden").val(file.name);
			layer.closeAll();
			layer.msg("作品上传成功，请点击确认报名完成报名",{icon:1});
		});
	}
	function onlySave(){
		$.ajax({
			url:"/userApply/userApplyCompetitionItem?onlySave=1",
			type:"post",
			dataType:"json",
			data:$("#form").serialize(),
			success:function(data){
				if(data.status=='0'){
					layer.msg("保存成功",{icon:1});
					if($("#id").val()==''){
						$("#id").val(data.id);
					}
					//$("#applya").css("display","none");
					//x=2;
					//x1=window.setInterval(changeSuccess,1000); 
				}else{
					layer.alert(data.error_desc);
				}
			},error:function(){
				layer.alert(errorText);
			}
		});
	}
</script>
</head>
<body>
     <!-- 头部 -->
     <div class="head">
          <img src="/images/sj-top.png" class="sj-top">
          <div class="head-i w1348 m0">
              
              <div class="nav fr mt15">
                   <p id='loginDiv' class="head-login fl" style="visibility:hidden">
                       <a href="login.html">登   录</a>
                       <a href="register.html">注  册</a>
                  </p>
              </div>
          </div>
     </div>
     <!--头部banner-->
       <div class="headbanner">
          <div class="headbanner-i w1348 m0">
               <img src="/images/sign-draw.png" width="769" height="45" alt="科幻画报名">
          </div>
       </div> 
       <!-- 中间内容 -->
      <div class="main">
          <div class="main-i w1348 m0">
               <!-- 报名表左侧 -->
               <div class="form-left fl mt50">
                    <form id='form'>
                        <ul  class="left-form">
                           <!-- 作品名称 -->
                            <li>
                                <span>作品名称</span>
                                <input <c:if test="${!empty ifReadonly }"> readonly="readonly" </c:if> value="${applyInfo.productionName }" id="productionName" name="productionName" type="text" style="width:560px;" />
                            </li>
                            <!-- 参照组别 -->
                             <li>
                                <span>参赛组别</span>
                             </li>
                             <li>
                                <label class="ml50">（请在相应的类别中打√）</label>
                                    <input <c:if test="${!empty ifReadonly }"> disabled="disabled" </c:if> name="applyGroup"   type="checkbox"  value="303001"   /><label >手绘组</label>
                                    <input <c:if test="${!empty ifReadonly }"> disabled="disabled" </c:if> name="applyGroup"   type="checkbox"  value="303002" />  <label id="applyGroupTips">电脑绘图组（限中学、大学两个年龄段）</label>
                                    <!-- <span id="applyGroupTips"></span> -->
                             </li>
                             <!-- 参照年龄组 -->
                             <li>
                                <span>参赛年龄组</span>
                             </li>
                              <li>
                                <label  class="ml50">（请在相应的类别中打√）</label>
                                    <input <c:if test="${!empty ifReadonly }"> disabled="disabled" </c:if> name="applyYearGroup"   type="checkbox"  value="304001"   /><label >小学</label>
                                    <input <c:if test="${!empty ifReadonly }"> disabled="disabled" </c:if> name="applyYearGroup"   type="checkbox"  value="304002" />  <label >中学</label>
                                    <input <c:if test="${!empty ifReadonly }"> disabled="disabled" </c:if> name="applyYearGroup"   type="checkbox"  value="304003" />  <label id="applyYearGroupTips">大学</label>
                                     <!-- <span id="applyYearGroupTips"></span> -->
                             </li>
                             <!--  姓名  出生年月 -->
                             <li>
                                <span class="name">姓名</span>
                                <input <c:if test="${!empty ifReadonly }"> readonly="readonly" </c:if> value="${applyInfo.realName }" id="realName" name="realName" type="text"  style="width:150px" />
                                 <span>出生年月</span>
                                 <input <c:if test="${!empty ifReadonly }"> disabled="disabled" </c:if> value="${birthday }" readonly="readonly" onclick='laydate({istime: true, format: "YYYYMM"})' type="text" name='birthday1' id='birthday' style="width:150px" />
                            </li>
                            <li>  
                                <span class="name">性别</span>
                               <input <c:if test="${!empty ifReadonly }"> disabled="disabled" </c:if> value="男" name="sex" type="radio" checked="checked"/>男
                               <input <c:if test="${!empty ifReadonly }"> disabled="disabled" </c:if> value="女" name="sex" type="radio" />女
                            </li>
                            <!-- 所在学校 -->
                            <li>
                                <span>所在学校</span>
                                <input <c:if test="${!empty ifReadonly }"> readonly="readonly" </c:if> value="${applyInfo.schoolName }"  name='schoolName' id='schoolName' type="text"  style="width:590px" />
                             </li>
                             <!-- 推荐单位 -->
                             <li>
                                <span class="dw">推荐单位（没有写无）</span>
                                <input <c:if test="${!empty ifReadonly }"> readonly="readonly" </c:if> value="${applyInfo.recommenedCompany }"  name="recommenedCompany" id="recommenedCompany" type="text"  style="width:510px" />
                             </li>
                             <!-- 证件类型 -->
                              <li>
                                 <span>证件类型</span>
                                  <select <c:if test="${!empty ifReadonly }"> disabled="disabled" </c:if> name="cardType" id='cardType'>
           		                      <option value='308001'>身份证</option>
                   		              <option value='308002'>护照</option>
                                  </select>
                                  <span>证件号码</span>
                                  <input <c:if test="${!empty ifReadonly }"> readonly="readonly" </c:if>  value="${applyInfo.cardNumber }"  type="text" name="cardNumber" id='cardNumber' style="width:380px" />

                             </li>
                              <!-- 联系方式 -->
                              <li>
                                <span>联系电话</span>
                                <input <c:if test="${!empty ifReadonly }"> readonly="readonly" </c:if> value="${applyInfo.telephone }"  name="telephone" id="telephone" type="text"  style="width:150px" />
                                 <span>手机号码</span>
                                 <input <c:if test="${!empty ifReadonly }"> readonly="readonly" </c:if> value="${applyInfo.mobilePhone }" name='mobilePhone' id='mobilePhone' type="text"  style="width:150px" />
                             </li>
                              <!-- 邮箱   邮编 -->
                              <li>
                                <span>邮箱</span>
                                <input <c:if test="${!empty ifReadonly }"> readonly="readonly" </c:if> value="${applyInfo.email }" name='email' id='email' type="text"  style="width:150px" />
                                 <span>邮编</span>
                                 <input <c:if test="${!empty ifReadonly }"> readonly="readonly" </c:if> value="${applyInfo.postcode }"  name="postcode" id='postcode' type="text"  style="width:150px" />
                             </li>
                             <!-- 通讯地址 -->
                              <li>
                                <span>通讯地址</span>
                                <input <c:if test="${!empty ifReadonly }"> readonly="readonly" </c:if> value="${applyInfo.address }"  name='address' id='address' type="text" style="width:560px;" />
                            </li>
                         </ul>   
                        <ul class="right-form">
                            <li>
                                <span>创意说明（不超过300字）</span>
                            </li>
                            <li>
                               <textarea <c:if test="${!empty ifReadonly }"> readonly="readonly" </c:if> id='ideaDesc' name="ideaDesc" cols="50" rows="28">${applyInfo.ideaDesc}</textarea>
                            </li>
                        </ul>
                        <div class="cb left-form1" id='applyDIv'>
                           <p>
                           	 <a id='onlysavea' onclick="onlySave()" href="javascript:;" class="bc"><img src="/images/bc-btn.png" alt="保存报名表"></a>
                             <a onclick='openChoose()' id='chooseProduct' href="javascript:;" class="tj"><img src="/images/tj-bm.png"></a>
                             <a id="applya"  <c:if test="${!empty ifReadonly }"> style="display:none" </c:if> onclick="apply()" href="javascript:;"><img src="/images/qr.png"></a></p>
                        </div>
                        <input type='hidden' name="activityId" id="activityId" value="${applyInfo.activityId }"/>
                        <input type='hidden' name="competitionItemId" id="competitionItemId" value="${applyInfo.competitionItemId}"/>
                        <input type='hidden' name="competitionType" id="competitionType" value="${applyInfo.competitionType }"/>
                        <input type='hidden' name="fileNameHidden" id="fileNameHidden" />
                        <input type='hidden' name="filePathHidden" id="filePathHidden" />
                        <input type='hidden' name="id" id="id" value="${applyInfo.id }"/>
                       <%--  <input type='hidden' name="activityId" id="activityId" value="${item.activityId }"/>
                        <input type='hidden' name="competitionItemId" id="competitionItemId" value="${item.id }"/>
                        <input type='hidden' name="competitionType" id="competitionType" value="${item.type }"/> --%>
                    </form>
               </div>
          </div>
      </div>
     <!-- 底部 -->
     <!-- <div class="footer">
        <div class="footer-i w1348 m0">
              Copyright © 2016-2017 Science  contest
        </div>
     </div> -->
     <div id='uploadDiv' style="display:none">
     	<div id='picker' style="margin-left:30px;margin-top:30px;">添加作品</div>
     	<div style="margin-left:30px;margin-top:10px;">
     		请选择jpeg格式文件
     	</div>
     	<div id="fileNameDiv" style="margin-left:30px;margin-top:20px;"></div>
     	<div id="fileProgressDiv" style="margin-left:30px;margin-top:20px;"></div>
     	<div id='uploadStateDiv' style="margin-left:30px;margin-top:20px;"></div>
     	<div style="margin-left:30px;margin-top:20px;" > 
     		<input id='uploadButton' disabled="disabled" style="width:100px;height:40px;background-color:#00b6ed;" onclick="uploader.upload()" type="button" value="确认上传"/>
     	</div>
     </div>
	</body>
</html>