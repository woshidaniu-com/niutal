<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<link href="<%=systemPath%>/css/globalweb/page.css?_rv_=<%=resourceVersion%>" rel="stylesheet" type="text/css" />
<link href="<%=systemPath%>/css/jquery/listnav/listnav.css?_rv_=<%=resourceVersion%>" rel="stylesheet" type="text/css" />
<link href="<%=systemPath%>/css/jquery/listnav/listnav_ui.css?_rv_=<%=resourceVersion%>" rel="stylesheet" type="text/css" />
<script src="<%=systemPath%>/js/jquery/listnav/uiwidget.listnav-1.0.0.js?_rv_=<%=resourceVersion%>" type="text/javascript"></script>
<script src="<%=systemPath%>/js/globalweb/comp/xtgl/dataRangeSetting.js?_rv_=<%=resourceVersion%>" type="text/javascript"></script>
<style type="text/css">
.demo_data2 {
	border: 1px solid #DEDEDE;
	display: inline;
	float: left;
	height: 75px;
	margin: 4px 5px 0;
	padding: 8px;
	width: 155px;
}
.currentDiv {
	background: #fff;
	border: #adc6e7 1px solid;
	padding: 0 9px;
	color: #333333;
}

.dataRangeBox1{
    clear:both;
	text-align: left;
	min-height: 50px;
	width:893px;
	height:435px;
	border: 1px solid #ccc;
	word-wrap: break-word;
	overflow-x:hidden;
	margin:2px;
}

.dataRangeBox1 p{
	line-height: 30px;
}


.dataRangeBox{
    clear:both;
	text-align: left;
	min-height: 50px;
	width:893px;
	height:435px;
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
.lisnavBox li table{
	text-align: left;
}
.lisnavBox li table th{
	font-family: 'Lucida Grande', 'Helvetica Neue', Helvetica, Arial, sans-serif;
	text-align: left;
	vertical-align: bottom;
	width: 225px;
	border-right: 1px solid #ccc;
}
.lisnavBox li table td{
	text-align: left;
	vertical-align: bottom;
	font-family: 'Lucida Grande', 'Helvetica Neue', Helvetica, Arial, sans-serif;	
}
.lisnavBox li  input{
	text-align: left;
	vertical-align: middle;
}
.lisnavBox_bj li{
	float: left;
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
<s:set name="njdmsJson" value="@com.woshidaniu.service.common.engine.CommonSqlEngine@getAllNjJson()" />
<s:set name="njdms" value="@com.woshidaniu.service.common.engine.CommonSqlEngine@getAllNj()" />
<s:set name="bmdms" value="@com.woshidaniu.service.common.engine.CommonSqlEngine@getAllXy()" />
<s:set name="jxbmdms" value="@com.woshidaniu.service.common.engine.CommonSqlEngine@getAllJxXy()" />
<s:set name="zydms" value="@com.woshidaniu.service.common.engine.CommonSqlEngine@getAllZy()" />
<s:set name="bmlxs" value="@com.woshidaniu.service.common.engine.CommonSqlEngine@getAllBmLx()" />
<s:set name="xqdms" value="@com.woshidaniu.service.common.engine.CommonSqlEngine@getAllXq()" />
<script type="text/javascript">
//取得的全局年级json
var njdmsJson = ${njdmsJson};
</script>
<body>
	<s:hidden id="js_id" name="js_id"/>
	<s:iterator value="zghList">
		<input type="hidden" name="zgh" value="<s:property/>" />
	</s:iterator>
	<div class="position_xxxx after">
		<s:if test="lists!=null">
			<ul class="list_xxxx" id="displayBox">
				<s:iterator value="lists" status="st">
					<li name="${bm}">
						<a <s:if test="bm=='niutal_XTGL_BMDMB'">class="currentDiv"</s:if> href="javascript:void(0);" bm="${bm}">按${zwmc }</a>
					</li>
				</s:iterator>
			</ul>
		</s:if>
	</div>
	<!-- 全校 -->
	<div class="dataRangeDetail" name="SCHOOL" style="display: none;">
    	<!-- 数据范围box -->
		<div id="dataRangeBox" class="dataRangeBox1" name="SCHOOL">
			<ul class="lisnavBox">
				<li style="text-align: left;border-bottom: 1px solid #ccc;">
					<input id="checkAll_qx" type="checkbox" value="-2" name="xxdm_" > <span>全校</span>
				</li>
			</ul>
			<p style="text-align: left;border-bottom: 1px solid #ccc;padding: 5px;">
				<span style="clear: both;color:red;"><b>注意：</b></span>
				1.用户将拥有全校所有学院（部门）、专业、班级的数据权限
			</p>
		</div>
	</div>
	<!-- 学院/部门列表 -->
	<div class="dataRangeDetail" name="niutal_XTGL_BMDMB" style="display: block;clear: both;">
		<!-- 筛选条件 -->
		<div class="searchtab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
		    	<tbody>
			     	<tr>
			     		<th width="100px">机构类别：</th>
			     		<td>
			     			<select name="bmdm_bmlb" id="bmdm_bmlb" style="width:150px" class="standard" >
			     				<option value="全部">全部</option>
			     				<s:iterator value="bmlxs">
			     					<option value="<s:property value="key"/>"><s:property value="value"/></option>
			     				</s:iterator>
			     			</select>
			     		</td>
			     		<th width="100px">年级：</th>
			     		<td>
			     			<select name="bmdm_njdm_id" id="bmdm_njdm_id" style="width:150px" class="standard" >
			     				<option value="全部">全部</option>
			     				<s:iterator value="njdms">
			     					<option value="<s:property value="njdm_id"/>"><s:property value="njmc"/></option>
			     				</s:iterator>
			     			</select>
			     		</td>
			      	</tr>
		    	</tbody>
	    	</table>
    	</div>
		<!-- 数据范围box -->
		<div id="dataRangeBox" class="dataRangeBox" name="niutal_XTGL_BMDMB"></div>
	</div>
	<!-- 专业列表 -->
	<div class="dataRangeDetail" name="niutal_XTGL_ZYDMB"  style="display: none;">
		<!-- 筛选条件 -->
		<div class="searchbox" style="border: 0px;">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
		    	<tbody>
			     	<tr>
			     		<th>学院：</th>
			     		<td>
			     			<select  name="bmdm_zydm_id" id="bmdm_zydm_id" style="width:150px" class="standard" >
			     				<option value="全部">全部</option>
			     				<s:iterator value="jxbmdms">
			     					<option value="<s:property value="bmdm_id"/>"><s:property value="bmmc"/></option>
			     				</s:iterator>
			     			</select>
			     		</td>
			     		<th>年级：</th>
			     		<td>
							<select name="njdm_zydm_id" id="njdm_zydm_id" style="width:150px" class="standard" >
			     				<option value="全部">全部</option>
			     				<s:iterator value="njdms">
			     					<option value="<s:property value="njdm_id"/>"><s:property value="njmc"/></option>
			     				</s:iterator>
			     			</select>
			     		</td>
			      	</tr>
		    	</tbody>
	    	</table>
    	</div>
		<!-- 数据范围box -->
		<div id="dataRangeBox" class="dataRangeBox"></div>
	</div>
	<!-- 班级列表 -->
	<div class="dataRangeDetail" name="niutal_XTGL_BJDMB" style="display: none;">
		<!-- 筛选条件 -->
		<div class="searchbox" style="border: 0px;">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
		    	<tbody>
			     	<tr>
			     		<th>学院：</th>
			     		<td>
			     			<select name="bjdm_bmdm_id" id="bjdm_bmdm_id" style="width:150px" class="standard" >
			     				<option value="全部">全部</option>
			     				<s:iterator value="jxbmdms">
			     					<option value="<s:property value="bmdm_id"/>"><s:property value="bmmc"/></option>
			     				</s:iterator>
			     			</select>
			     		</td>
			     		<th>专业：</th>
			     		<td>
			     			<select name="bjdm_zydm_id" id="bjdm_zydm_id" style="width:150px" class="standard" >
			     				<option value="全部">全部</option>
			     				<s:iterator value="zydms">
			     					<option value="<s:property value="zydm_id"/>"><s:property value="zymc"/></option>
			     				</s:iterator>
			     			</select>
			     		</td>
			     		<th>年级：</th>
			     		<td>
							<select name="bjdm_njdm_id" id="bjdm_njdm_id" style="width:150px" class="standard" >
			     				<option value="全部">全部</option>
			     				<s:iterator value="njdms">
			     					<option value="<s:property value="njdm_id"/>"><s:property value="njmc"/></option>
			     				</s:iterator>
			     			</select>
			     		</td>
			      	</tr>
		    	</tbody>
	    	</table>
    	</div>
    	<!-- 数据范围box -->
		<div id="dataRangeBox" class="dataRangeBox"></div>
	</div>
	<!-- 数据保存，关闭按钮 -->
    <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
    	<tfoot>
			<tr>
				<td colspan="2">
					<div class="btn">
						<button name="保存" id="dataRangeSaveBt" type="button"> 保存 </button>
						<button name="关闭" onclick="iFClose();" type="button"> 关闭 </button>
					</div>
				</td>
			</tr>
		</tfoot>
 	</table>
	<div style="display: none;width: 100%;text-align: center;vertical-align: middle;margin-top: 10px;" id="__waiting">
		<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100" height="100" id="FlashID" title="loading">
			<param name="movie" value="<%=systemPath %>/swf/loading02.swf" />
			<param name="quality" value="high" />
			<param name="wmode" value="transparent" />
			<param name="swfversion" value="8.0.35.0" />
			<!-- 此 param 标签提示使用 Flash Player 6.0 r65 和更高版本的用户下载最新版本的 Flash Player。如果您不想让用户看到该提示，请将其删除。 -->
			<!-- 下一个对象标签用于非 IE 浏览器。所以使用 IECC 将其从 IE 隐藏。 -->
			<!--[if !IE]>-->
			<object type="application/x-shockwave-flash" data="<%=systemPath %>/swf/loading02.swf" width="100"
				height="100">
				<!--<![endif]-->
				<param name="quality" value="high" />
				<param name="wmode" value="transparent" />
				<param name="swfversion" value="8.0.35.0" />
				<!-- 浏览器将以下替代内容显示给使用 Flash Player 6.0 和更低版本的用户。 -->
				<div>
				</div>
				<!--[if !IE]>-->
			</object>
			<!--<![endif]-->
		</object>
		<p>
			正在查询，请稍等……
		</p>
   	</div>
</body>
</html>
