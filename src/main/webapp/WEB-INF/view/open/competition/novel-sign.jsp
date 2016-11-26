<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科幻小说-报名表</title>
<jsp:include page="../common/open_inc.jsp"></jsp:include>
<!-- <script type="text/javascript" src="js/Validform.js"></script> -->

<style type="text/css">  
div#roll{width:100px;height:100px; background-color:red; color:#fff; position:absolute;}  
</style>
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
               <!-- <img src="/images/sign-draw.png" width="769" height="45" alt="科幻小说报名"> -->
          </div>
       </div> 
       <!-- 中间内容 -->
      <div class="main">
          <div class="main-i w1348 m0">
               <!-- 报名表左侧 -->
               <div class="form-left fl mt50">
                    <form>
                        <ul  class="left-form">
                           <!-- 作品名称 -->
                            <li>
                                <span>作品名称</span>
                                <input type="text" style="width:560px;" />
                            </li>
                            <!-- 参照组别 -->
                             <li>
                                <span>参照组别</span>
                             </li>
                             <li>
                                <label class="ml50">（请在相应的类别中打√）</label>
                                    <input name="zb"   type="checkbox"  value="1"   /><label >微型小说组</label>
                                    <input name="zb"   type="checkbox"  value="2" />  <label >中篇小说组</label>
                                    
                             </li>
                             <!-- 参照年龄组 -->
                             <li>
                                <span>参照年龄组</span>
                             </li>
                              <li>
                                <label  class="ml50">（请在相应的类别中打√）</label>
                                    <input name="lb"   type="checkbox"  value="1"   /><label >小学</label>
                                    <input name="lb"   type="checkbox"  value="2" />  <label >中学</label>
                                    <input name="lb"   type="checkbox"  value="3" />  <label >大学</label>
                                    <input name="lb"   type="checkbox"  value="3" />  <label >社会人士（35岁以下）</label>
                             </li>
                             <!--  姓名  出生年月 -->
                             <li>
                                <span class="name">姓名</span>
                                <input type="text"  style="width:150px" />
                                 <span>出生年月</span>
                                 <input type="text"  style="width:150px" />
                            </li>
                            <li>  
                                <span class="name">性别</span>
                               <input name="sex" type="radio" />男
                               <input name="sex" type="radio" />女
                            </li>
                            <!-- 所在学校  -->
                            <li>
                                <span>所在学校</span>
                                <input type="text"  style="width:590px" />
                             </li>
                             <!-- 年级  -->
                            <li>
                                <span>年级(单位)</span>
                                <input type="text"  style="width:590px" />
                             </li>
                             <!-- 推荐单位 -->
                             <li>
                                <span class="dw">推荐单位（没有写无）</span>
                                <input type="text"  style="width:510px" />
                             </li>
                             <!-- 证件类型 -->
                              <li>
                                 <span>证件类型</span>
                                  <select>
                                               <option>身份证</option>
                                               <option>护照</option>
                                      </select>
                                   <span>证件号码</span>
                                     <input type="text"  style="width:380px" />

                             </li>
                              <!-- 联系方式 -->
                              <li>
                                <span>联系电话</span>
                                <input type="text"  style="width:150px" />
                                 <span>手机号码</span>
                                 <input type="text"  style="width:150px" />
                             </li>
                              <!-- 邮箱   邮编 -->
                              <li>
                                <span>邮箱</span>
                                <input type="text"  style="width:150px" />
                                 <span>邮编</span>
                                 <input type="text"  style="width:150px" />
                             </li>
                             <!-- 通讯地址 -->
                              <li>
                                <span>通讯地址</span>
                                <input type="text" style="width:560px;" />
                            </li>
                         </ul>   
                        <ul class="right-form">
                            <li>
                                <span>创意说明（不超过300字）</span>
                            </li>
                            <li>
                               <textarea cols="50" rows="28"></textarea>
                            </li>
                        </ul>
                        <div class="cb left-form1">
                           <p>
                             <a href="javascript:;" class="tj"><img src="/images/tj-bm.png"></a>
                             <a href="javascript:;"><img src="/images/qr.png"></a></p>

                        </div>
                     
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