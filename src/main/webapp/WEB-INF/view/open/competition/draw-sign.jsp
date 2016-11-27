<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科幻画-报名表</title>
<jsp:include page="../common/open_inc.jsp"></jsp:include>
<!-- <script type="text/javascript" src="js/Validform.js"></script> -->
<style type="text/css">  
div#roll{width:100px;height:100px; background-color:red; color:#fff; position:absolute;}  
</style>
<script type="text/javascript">
	$(document).ready(function(){
		<c:if test="${empty item }">
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
	});
	function apply(){
		var productionName=$("#productionName").val();
		if($.trim(productionName)==''){
			layer.tips('请填写作品名称', '#productionName',{tips:[2,tipsColor]});return;
		}
		if($("input[name=applyGroup]:checked").length==0){
			layer.tips('请选择参赛组别', '#applyGroupTips',{tips:[2,tipsColor]});return;
		}
		if($("input[name=applyYearGroup]:checked").length==0){
			layer.tips('请选择参赛年龄组', '#applyYearGroupTips',{tips:[2,tipsColor]});return;
		}
		if($("input[name=applyGroup]:checked").val()=='303002'&&$("input[name=applyYearGroup]:checked").val()=='304001'){
			layer.tips('电脑绘图组限中学、大学两个年龄段', '#applyYearGroupTips',{tips:[2,tipsColor]});return;
		}
		var realName=$("#realName").val();
		if($.trim(realName)==''){
			layer.tips('请填写姓名', '#realName',{tips:[2,tipsColor]});return;
		}
		var birthday=$("#birthday").val();
		if($.trim(birthday)==''){
			layer.tips('请选择出生年月', '#birthday',{tips:[2,tipsColor]});return;
		}
		var schoolName=$("#schoolName").val();
		if($.trim(schoolName)==''){
			layer.tips('请填写所在学校', '#schoolName',{tips:[2,tipsColor]});return;
		}
		var recommenedCompany=$("#recommenedCompany").val();
		if($.trim(recommenedCompany)==''){
			layer.tips('请填写推荐单位，没有写无', '#recommenedCompany',{tips:[2,tipsColor]});return;
		}
		var cardNumber=$("#cardNumber").val();
		if($.trim(cardNumber)==''){
			layer.tips('请填写证件号码', '#cardNumber',{tips:[2,tipsColor]});return;
		}
		if($("#cardType").val()=='308001'&&!IdentityCodeValid(cardNumber)){
			layer.tips('请填写真实的身份证号,最后一位是X的必须大写', '#cardNumber',{tips:[2,tipsColor]});return;
		}
		var telephone=$("#telephone").val();
		if($.trim(telephone)==''){
			layer.tips('请填写联系电话,没有填无', '#telephone',{tips:[2,tipsColor]});return;
		}
		var mobilePhone=$("#mobliePhone").val();
		if($.trim(mobilePhone)==''){
			layer.tips('请填写手机号码,没有填无', '#mobilePhone',{tips:[2,tipsColor]});return;
		}
		var email=$("#email").val();
		if($.trim(email)==''){
			layer.tips('请填写邮箱', '#email',{tips:[2,tipsColor]});return;
		}
		var postcode=$("#postcode").val();
		if($.trim(postcode)==''){
			layer.tips('请填写邮编', '#postcode',{tips:[2,tipsColor]});return;
		}
		var address=$("#address").val();
		if($.trim(address)==''){
			layer.tips('请填写通讯地址', '#address',{tips:[2,tipsColor]});return;
		}
		var ideaDesc=$("#ideaDesc").val();
		if($.trim(ideaDesc)==''){
			layer.tips('请填写创意说ing', '#ideaDesc',{tips:[2,tipsColor]});return;
		}
		$.ajax({
			url:"/userApply/userApplyCompetitionItem",
			type:"post",
			dataType:"json",
			data:$("#form").serialize(),
			success:function(data){
				console.log(data);
			},error:function(){
				
			}
		});
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
                                <input id="productionName" name="productionName" type="text" style="width:560px;" />
                            </li>
                            <!-- 参照组别 -->
                             <li>
                                <span>参赛组别</span>
                             </li>
                             <li>
                                <label class="ml50">（请在相应的类别中打√）</label>
                                    <input name="applyGroup"   type="checkbox"  value="303001"   /><label >手绘组</label>
                                    <input name="applyGroup"   type="checkbox"  value="303002" />  <label id="applyGroupTips">电脑绘图组（限中学、大学两个年龄段）</label>
                                    <!-- <span id="applyGroupTips"></span> -->
                             </li>
                             <!-- 参照年龄组 -->
                             <li>
                                <span>参赛年龄组</span>
                             </li>
                              <li>
                                <label  class="ml50">（请在相应的类别中打√）</label>
                                    <input name="applyYearGroup"   type="checkbox"  value="304001"   /><label >小学</label>
                                    <input name="applyYearGroup"   type="checkbox"  value="304002" />  <label >中学</label>
                                    <input name="applyYearGroup"   type="checkbox"  value="304003" />  <label id="applyYearGroupTips">大学</label>
                                     <!-- <span id="applyYearGroupTips"></span> -->
                             </li>
                             <!--  姓名  出生年月 -->
                             <li>
                                <span class="name">姓名</span>
                                <input id="realName" name="realName" type="text"  style="width:150px" />
                                 <span>出生年月</span>
                                 <input readonly="readonly" onclick='laydate({istime: true, format: "YYYY-MM-DD"})' type="text" name='birthday' id='birthday' style="width:150px" />
                            </li>
                            <li>  
                                <span class="name">性别</span>
                               <input value="男" name="sex" type="radio" checked="checked"/>男
                               <input value="女" name="sex" type="radio" />女
                            </li>
                            <!-- 所在学校 -->
                            <li>
                                <span>所在学校</span>
                                <input name='schoolName' id='schoolName' type="text"  style="width:590px" />
                             </li>
                             <!-- 推荐单位 -->
                             <li>
                                <span class="dw">推荐单位（没有写无）</span>
                                <input name="recommenedCompany" id="recommenedCompany" type="text"  style="width:510px" />
                             </li>
                             <!-- 证件类型 -->
                              <li>
                                 <span>证件类型</span>
                                  <select name="cardType" id='cardType'>
           		                      <option value='308001'>身份证</option>
                   		              <option value='308002'>护照</option>
                                  </select>
                                  <span>证件号码</span>
                                  <input type="text" name="cardNumber" id='cardNumber' style="width:380px" />

                             </li>
                              <!-- 联系方式 -->
                              <li>
                                <span>联系电话</span>
                                <input name="telephone" id="telephone" type="text"  style="width:150px" />
                                 <span>手机号码</span>
                                 <input name='mobilePhone' id='mobilePhone' type="text"  style="width:150px" />
                             </li>
                              <!-- 邮箱   邮编 -->
                              <li>
                                <span>邮箱</span>
                                <input name='email' id='email' type="text"  style="width:150px" />
                                 <span>邮编</span>
                                 <input name="postcode" id='postcode' type="text"  style="width:150px" />
                             </li>
                             <!-- 通讯地址 -->
                              <li>
                                <span>通讯地址</span>
                                <input name='address' id='address' type="text" style="width:560px;" />
                            </li>
                         </ul>   
                        <ul class="right-form">
                            <li>
                                <span>创意说明（不超过300字）</span>
                            </li>
                            <li>
                               <textarea id='idea_desc' name="idea_desc" cols="50" rows="28"></textarea>
                            </li>
                        </ul>
                        <div class="cb left-form1">
                           <p>
                             <a href="javascript:;" class="tj"><img src="/images/tj-bm.png"></a>
                             <a onclick="apply()" href="javascript:;"><img src="/images/qr.png"></a></p>
                        </div>
                        <input type='hidden' name="activityId" id="activityId" value="${item.activityId }"/>
                        <input type='hidden' name="competitionItemId" id="competitionItemId" value="${item.id }"/>
                        <input type='hidden' name="competitionType" id="competitionType" value="${item.type }"/>
                    </form>
               </div>
          </div>
      </div>
     <!-- 底部 -->
     <div class="footer">
        <div class="footer-i w1348 m0">
              Copyright © 2016-2017 Science  contest
        </div>
     </div>
	</body>
</html>