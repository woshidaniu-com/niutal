<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
<link href="<%=systemPath%>/css/globalweb/page.css?_rv_=<%=resourceVersion%>" rel="stylesheet" type="text/css" />
<link href="<%=systemPath%>/css/jquery/listnav/listnav.css?_rv_=<%=resourceVersion%>" rel="stylesheet" type="text/css" />
<link href="<%=systemPath%>/css/jquery/listnav/listnav_ui.css?_rv_=<%=resourceVersion%>" rel="stylesheet" type="text/css" />
<script src="<%=systemPath%>/js/jquery/listnav/uiwidget.listnav-1.0.0.js?_rv_=<%=resourceVersion%>" type="text/javascript"></script>
<script src="<%=systemPath%>/js/globalweb/comp/xtgl/dataRangeSetting.js?_rv_=<%=resourceVersion%>" type="text/javascript"></script>
<script src="<%=systemPath%>/js/globalweb/comm/json.js?_rv_=<%=resourceVersion%>" type="text/javascript"></script>
<style type="text/css">

.currentDiv {
	background: #fff;
	border: #adc6e7 1px solid;
	padding: 0 9px;
	color: #333333;
}

.dataRangeBox{
    clear:both;
	text-align: left;
	min-height: 50px;
	width:893px;
	height:410px;
	border: 1px solid #ccc;
	word-wrap: break-word;
	overflow-x:hidden;
	overflow-y:auto;
	margin:2px;
}

.lisnavBox{
	clear: both;
}

.lisnavBox li{
	min-height: 30px;
	line-height: 30px;
	list-style: none;
	list-style-type: none;
	overflow: hidden;
	-webkit-border-radius: 4px;
	border-radius: 4px;
	-moz-background-clip: padding;
	-webkit-background-clip: padding-box;
	background-clip: padding-box;
	width:290px;
	float: left;
	border-right: 1px solid #ccc;
	border-bottom: 1px solid #ccc;
	
}
.lisnavBox li:hover {
	cursor: pointer;
	background-color: #eff1f9;
	-webkit-box-shadow: 0px 0px 2px 0px #bababa;
	box-shadow: 0px 0px 5px 0px #BBB;
}

.lisnavBox li p{
	background: none;
	padding-bottom: 0;
	margin: 0;
}


.lisnavBox li span{
	width:200px;
}


.lisnavBox li  input{
	text-align: left;
	vertical-align: middle;
}

.ln-letters a {
	font-size: 1.0em;
	width:auto;
	display: block;
	float: left;
	padding: 2px 9px;
	border: 1px solid silver;
	border-right: none;
	text-decoration: none;
}

</style>
</head>

<body>
	<s:hidden id="js_id" name="js_id"/>
	<s:iterator value="zghList">
		<input type="hidden" name="zgh" value="<s:property/>" />
	</s:iterator>
	<div class="position_xxxx after">
		<ul class="list_xxxx" id="sjfwdxUl">

		</ul>
	</div>

	<div >
		<p><font color="red">注：数据范围为叠加控制，如：选中某一学院，无须在专业中勾选该学院下的相应专业</font></p>
	</div>	
	<div id="sjfwlb">
			<!-- 全校 -->
		<div  id="sjfw_1" style="display: none;">
			<div class="dataRangeBox" >
				<ul class="lisnavBox" id="sjfwdx_listul_1">
					<li>
						<label><input type="checkbox"><span>全校</span></label>
					</li>
				</ul>
				<br/><br/>
				<p style="text-align: left;border-bottom: 1px solid #ccc;padding: 5px;">
					<span style="clear: both;color:red;"><b>注意：</b></span>
					1.用户将拥有全校所有学院（部门）、专业、班级的数据权限
				</p>
			</div>
		</div>
		<div style="clear: both;"></div>

	</div>
	<div>
	    <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
	    	<tfoot>
				<tr>
					<td colspan="2">
					<div class="bz">
						<label><input type="checkbox" onclick="checkAll(this);"><span>全选</span></label>
					</div>
						<div class="btn">
							<button onclick="saveSjfw();"  type="button"> 保存 </button>
							<button onclick="iFClose();" type="button"> 关闭 </button>
						</div>
					</td>
				</tr>
			</tfoot>
	 	</table>		
 	</div>
</body>
</html>
