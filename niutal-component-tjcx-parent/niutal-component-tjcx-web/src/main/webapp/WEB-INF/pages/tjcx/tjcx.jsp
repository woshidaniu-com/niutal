<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comm/json/json.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comm/jquery/dragsort/jquery.dragsort-0.5.1.min.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js?_rv_=<%=resourceVersion%>"></script>
<%-- 		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script> --%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js?_rv_=<%=resourceVersion%>"></script>
<%-- 		<script type="text/javascript" src="<%=systemPath %>/js/jquery/jquery.form.js?_rv_=<%=resourceVersion%>"></script> --%>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/textClue.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/select.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjcxUtils.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjcxPublic.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjcx.js?_rv_=<%=resourceVersion%>"></script>
		
		<style>		
			.tjxm .tjxm_1 .more a {
				width: 25px;
			}	
		</style>				
	</head>
	<body>
	<s:form namespace="/zfxg/tjcx" action="tjcx_tjcxlb" method="post" id="form1">
     	 <input type="hidden" id="gnmk" name="gnmk" value="${gnmk}"/>
    	<input type="hidden" id="xmdm" name="xmdm" value="${xmdm}"/>
      	<input type="hidden" id="gltj" name="gltj" value="${gltj}"/>
     	<input type="hidden" id="bbhxl" name="bbhxl" value=""/>
     	<input type="hidden" id="bbzxl" name="bbzxl" value=""/>
     	<input type="hidden" id="tsx" name="tsx" value=""/>
     	<input type="hidden" id="gltjmc" name="gltjmc" value=""/>
       	<input type="hidden" id="fhbs" name="fhbs" value="${tjlbFhbs}"/>
      	<input type="hidden" id="kzlx" name="kzlx" value="1"/>
     	<input type="hidden" id="zdxsms" name="zdxsms" value="1"/>
       	<input type="hidden" id="czy" name="czy" value="${czy}"/>
    </s:form>
    
    <input type="hidden" id="currentMenu" name="currentMenu" value="${TJCX_CURRENT_MENU}"/>
    
	<div class="tab_cur" style="display: none;">
		<p class="location">
			<em>您的当前位置:</em><a>统计分析-统计查询</a>
		</p>
	</div>

<div class="tjxm" style="width:100%">
	<h3 class="tjxm_title"><span>统计项目</span><span id="version" style="float:right;padding-right: 5px;color:#61604d;">2.2.0</span></h3>
    <div class="tjxm_nav">
    	<ul id="tjxmUl"></ul>
    </div>
    <div class="tjxm_1" id="cxkz1" style="display:none;width:100%;">
    	<p>查询快照</p>
      	<ul id = "cxkzUl">
        </ul>
        <p class="more">
	        <a href="javascript:;" title="删除" onclick="cxkzScBtn(this);" class="del">删除</a>
	        <a href="javascript:;" title="设置"  name="cxkzszA"  onclick="cxkzSzBtn();">设置</a>
        </p>
    </div>
	<div class="clearall"></div>
    <div class="tjxm_2" style="display:none;">
    	<h4 class="tjxm_2_title">
    		<span>过滤条件</span>
    		<span class="text1">(条件块内的各字段为“并且”关系，条件块间为“或者”关系)</span>
    		<a href="javascript:;" onclick="addCxzdTjk();">增加条件块</a></h4>
        <div class="gltj" id="cxzdDiv" style="width:100%;border:0;padding-top:5px;padding-bottom:5px;padding-left:0;padding-right:0;">
        </div>
    </div>	
    
  	<div class="clearall"></div>

    <div class="tjxm_4" style="display:none;"><a href="javascript:;" class="bc" onclick="cxkzWindow();">保存快照</a><a href="javascript:;" class="tj" id="cxBtn" onclick="tjcx();">查  询</a></div>
</div>
		<div class="formbox">
			<table id="tabGrid"></table>
			<div id="pager"></div>
		</div>
</body>
</html>