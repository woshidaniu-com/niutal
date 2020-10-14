<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
<%@ include file="/ydyx/pagehead.ini"%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="telephone=no" name="format-detection" />
<title>在线咨询</title>
<meta name="keywords" content="我是大牛软件移动校园" />
<meta name="description" content="我是大牛软件移动校园" />
<script type="text/javascript" src="<%=jsPath %>/ydyx/js/wjdc/wj_model.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript" src="<%=jsPath %>/ydyx/js/wjdc/wj_cs.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript" src="<%=jsPath %>/ydyx/js/wjdc/wjdc.js?_rv_=<%=resourceVersion%>"></script>
</head>
<body>
    <header class="header">
    	<div class="header_back"><a href="javascript:history.go(-1);">返回</a></div>
        <div class="header_title">问卷调查</div>
    </header>
    -<form action="zfxg/wjdc/stgl_saveWjDa.html" method="post">
    <input type="hidden" id="wjid" name="wjModel.wjid" value="${wjModel.wjid}"/>
	<input type="hidden" id="wjlx" value="${wjModel.wjlx}"/>
	<input type="hidden" id="wjzf" value=""/>
	<input type="hidden" id="djrid" value="${wjModel.djrid}"/>
	<input type="hidden" id="wjzt" value="${wjModel.wjzt}"/>
	<input type="hidden" id="fblx" value="${wjModel.fblx}"/>
	<input type="hidden" id="djzt" value="${wjModel.djzt}"/>
	<input type="hidden" id="result" value="${result}"/>
	<input type="hidden" id="hidden_autoaddstbh" value="${wjModel.autoaddstbh}"/>
	<div class="mainframe" id="wrapper">
		<div class="mainframe1" id="wjview">
			<div class="line_h20 padding_l bg_white padding_tb10">${wjModel.wjmc}</div>
			<%--<div class="list_exam">  
				<h3 class="title_1">一、单项选择题（共10题）</h3>
				<div class="list_exam1">
					<h4 class="title_2">1、你支持取消食盐专营吗？</h4>
					<div class="list_exam_con">
						<input type="radio" name="exam1" value="a1" id="a1"><label for="a1">A. 支持</label>
						<input type="radio" name="exam1" value="b1" id="b1"><label for="b1">B. 反对</label>
						<input type="radio" name="exam1" value="c1" id="c1"><label for="c1">C. 中立</label>
						<input type="radio" name="exam1" value="d1" id="d1"><label for="d1">D. 无所谓</label>
					</div>
				</div>	
				<div class="list_exam1">
					<h4 class="title_2">2、铝塑复合管一般采用的连接方法是</h4>
					<div class="list_exam_con">
						<input type="radio" name="exam2" value="a2" id="a2"><label for="a2">A. 卡套式连接</label>
						<input type="radio" name="exam2" value="b2" id="b2"><label for="b2">B. 卡压连接</label>
						<input type="radio" name="exam2" value="c2" id="c2"><label for="c2">C. 热熔连接</label>
						<input type="radio" name="exam2" value="d2" id="d2"><label for="d2">D. 其他<input type="text" class="input margin_l"></label>
					</div>
				</div>
				<div class="list_exam1">
					<h4 class="title_2">3、铝塑复合管一般采用的连接方法是</h4>
					<div class="list_exam_con">
						<input type="checkbox" name="exam11" value="a11" id="a11"><label for="a11">A. 卡套式连接</label>
						<input type="checkbox" name="exam11" value="b11" id="b11"><label for="b11">B. 卡压连接</label>
						<input type="checkbox" name="exam11" value="c11" id="c11"><label for="c11">C. 热熔连接</label>
						<input type="checkbox" name="exam11" value="d11" id="d11"><label for="d11">D. 承插连接</label>
					</div>
				</div>
				<div class="list_exam1">
					<h4 class="title_2">4、铝塑复合管一般采用的连接方法是</h4>
					<div class="list_exam_con">
						<input type="checkbox" name="exam12" value="a12" id="a12"><label for="a12">A. 卡套式连接</label>
						<input type="checkbox" name="exam12" value="b12" id="b12"><label for="b12">B. 卡压连接</label>
						<input type="checkbox" name="exam12" value="c12" id="c12"><label for="c12">C. 热熔连接</label>
						<input type="checkbox" name="exam12" value="d12" id="d12"><label for="d12">D. 其他<input type="text" class="input margin_l"></label>
					</div>	
				</div>
				<h3 class="title_1">二、填空题（共5题）</h3>
				<div class="list_exam1">
					<h4 class="title_2">1、单行文本题目名称</h4>
					<div class="list_exam_con"><input type="text" class="input margin_tb10 w_b90"></div>
				</div>
				<div class="list_exam1">
					<h4 class="title_2">1、多行文本题目名称</h4>
					<div class="list_exam_con"><textarea name="" class="textarea margin_tb10 w_b90 padding_l padding_r"></textarea></div>
				</div>
			</div>
		--%></div>
    </div>
    </form>
	<div class="footer">
		<a id="but_tj" href="#" class="but_blue float_r margin_r margin_t" onclick="wjSubmitCheck()">问卷提交</a>
    </div>
</body>
</html>
<script type="text/javascript">
	if(jQuery("#djzt").val()=="已答卷"||jQuery("#wjzt").val()!="运行" || jQuery("#djrid").val() != "<s:property  value='#session.user.yhm' />"){
		jQuery("#but_tj").remove();
	}
	if(jQuery("#result").val()!=""){
		adderrorCall(jQuery("#result").val());
	}
</script>
