<!DOCTYPE html>
<html>
	<head>
		<title></title>
	    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="Copyright" content="woshidaniu" />	
		<link rel="icon" href="${base}/logo/favicon.ico" type="image/x-icon" />
		<link rel="shortcut icon" href="${base}/logo/favicon.ico" type="image/x-icon" />
		[#include "/globalweb/head/head_v5.ftl" /]
		
		<script type="text/javascript" src="${base}/js/tjcx/comm/highcharts/highcharts.js?ver=${messageUtil("niutal.jsVersion")}"></script>
		<script type="text/javascript" src="${base}/js/tjcx/comm/highcharts/modules/exporting.src.js?ver=${messageUtil("niutal.jsVersion")}"></script>
		<script type="text/javascript" src="${base}/js/tjcx/comp/tjcxUtils.js?ver=${messageUtil("niutal.jsVersion")}"></script>
		<script type="text/javascript" src="${base}/js/tjcx/comp/tjtb.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</head>
	<body>
    <form action="{base}/niutal/tjcx/tjtb/tjtb.zf" method="post" id="form1" >

     	<input type="hidden" id="gnmk" name="gnmk" value="${model.gnmk}"/>
    	<input type="hidden" id="xmdm" name="xmdm" value="${model.xmdm}"/>
     	<input type="hidden" id="gltj" name="gltj" value="${model.gltj}"/>
        <input type="hidden" id="curBbl" name="curBbl" value=""/>
       	<input type="hidden" id="curBblmc" name="curBblmc" value=""/>
       	      	
    	<input type="hidden" id="hxlHead" name="hxlHead" value="${model.hxlHead}">
    	<input type="hidden" id="hxlContent" name="hxlContent" value="${model.hxlContent}">
    	<input type="hidden" id="zxlContent" name="zxlContent" value="${model.zxlContent}">
       	<input type="hidden" id="zxlHeadCode" name="zxlHeadCode" value="${model.zxlHeadCode}">
 
    	<input type="hidden" id="hxlContentCode" name="hxlContentCode" value="${model.hxlContentCode}">
    	<input type="hidden" id="zxlContentCode" name="zxlContentCode" value="${model.zxlContentCode}">
    	
    	<input type="hidden" id="hxlhjContentJson" name="hxlhjContentJson" value="${model.hxlhjContentJson}">
     	<input type="hidden" id="zxlhjContentJson" name="zxlhjContentJson" value="${model.zxlhjContentJson}">
     	<input type="hidden" id="tjxJson" name="tjxJson" value="${model.tjxJson}">
     	<input type="hidden" id="contentJson" name="contentJson" value="${model.contentJson}">
     	<input type="hidden" id="zxlHead" name="zxlHead" value="${model.zxlHead}">
    </form>
    
 <!-- 过滤条件 -->
 	<div class="container">
    <div class="row">
          <table class="table" width="100%">
            <tbody>
              <tr>
                <th>统计项：</th>
                <td id="tjxTd">
                </td>
                <th>统计选项：</th>
                <td id="tjzhTd">
				</td>
              </tr>
              <tr>
                <th>图表类型：</th>
                <td>
                	<label><input type="radio" name="tblx" value="line" checked="checked"/>折线图</label>&nbsp;
                    <label><input type="radio" name="tblx" value="column"/>柱状图</label>&nbsp;
                	<span id="tblx_pie" ><label><input type="radio"  name="tblx" value="pie"/>饼图<font color="red">(仅限一维数据，自动匹配可选选项)</font></label>&nbsp;</span>
                </td>
				<th>图表标题：</th>
                <td><input id="tbbt" name="tbbt" alt="" maxlength="50" value="统计图表" style="width:300px;" /></td>                
              </tr>
          	  </tbody>        	  
            </table>
        </div>

		<div id="container" style="margin: 10px 20px 0 20px "></div>
		</div>
</body>
</html>

