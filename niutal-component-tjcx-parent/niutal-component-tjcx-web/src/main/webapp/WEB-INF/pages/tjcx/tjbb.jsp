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
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/textClue.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/select.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjcxUtils.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjcxPublic.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjbb.js?_rv_=<%=resourceVersion%>"></script>
		<style>		
			.tjxm .tjxm_1 .more a {
				width: 25px;
			}	
		</style>		
	</head>
	<body>
	<s:form namespace="/zfxg/tjcx" action="tjbb_tjlb" method="post" id="form1" target="_blank">
        <input type="hidden" id="gnmk" name="gnmk" value="${gnmk}"/>
    	<input type="hidden" id="xmdm" name="xmdm" value=""/>
     	<input type="hidden" id="gltj" name="gltj" value=""/>
     	<input type="hidden" id="gltjmc" name="gltjmc" value=""/>
     	<input type="hidden" id="bbhxl" name="bbhxl" value=""/>
     	<input type="hidden" id="bbzxl" name="bbzxl" value=""/>
     	<input type="hidden" id="tsx" name="tsx" value=""/>
     	<input type="hidden" id="kzlx" name="kzlx" value="2"/>
     	<input type="hidden" id="zdxsms" name="zdxsms" value="1"/>
        <input type="hidden" id="czy" name="czy" value="${czy}"/>
    </s:form>
    
    <input type="hidden" id="currentMenu" name="currentMenu" value="${TJCX_CURRENT_MENU}"/>
    
	<div class="tab_cur" style="display: none;">
		<p class="location">
			<em>您的当前位置:</em><a>统计分析-统计报表</a>
		</p>
	</div>

<div class="tjxm" style="width:100%;">
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
	        <a href="javascript:;" title="设置" onclick="cxkzSzBtn();"  name="cxkzszA" >设置</a>
        </p>
    </div>
	<div class="clearall"></div>
    <div class="tjxm_2" style="display:none;">
    	<h4 class="tjxm_2_title">
	    	<span>过滤条件</span>
	    	<span class="text1">(条件块内的各字段为“并且”关系，条件块间为“或者”关系)</span>
	    	<a href="javascript:;" onclick="addCxzdTjk();">增加条件块</a>
    	</h4>
        <div class="gltj" id="cxzdDiv" style="width:100%;border:0;padding-top:5px;padding-bottom:5px;padding-left:0;padding-right:0;">
        </div>
    </div>

	<div class="clearall"></div>
    
    <div class="tjxm_3" style="display:none;">
    	<h4 class="tjxm_3_title">
	    	<span class="title">报表列</span>
	    	<span class="text1">(从下面字段列表拖到已选横向列或纵向列中)</span>
    	</h4>
        <div class="gltj" id="bblDiv">
        </div>
    </div>
    
  	<div class="clearall"></div>
    
    <div class="tjxm_3" style="display:none;">
    	<h4 class="tjxm_3_title"><span class="title">统计项</span></h4>
        <div class="gltj" id="tjxDiv">
        	<table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
        		<tbody id="tjxTbody">
        		</tbody>
        	</table>
        </div>
    </div>

    <div class="tjxm_4" style="display:none;"><a href="javascript:;" class="bc" onclick="cxkzWindow();">保存快照</a><a href="javascript:;" class="tj" id="tjBtn" onclick="tjlb();">统  计</a></div>
</div>

</body>
</html>