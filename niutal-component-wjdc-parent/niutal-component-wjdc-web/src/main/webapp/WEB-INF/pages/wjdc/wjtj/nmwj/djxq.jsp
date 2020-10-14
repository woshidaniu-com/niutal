<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/dateformat.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/textClue.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/select.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/jquery/jquery.form.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comm/plugins/dateUtils.js?_rv_=<%=resourceVersion%>"></script>
		<link rel="stylesheet" href="<%=systemPath %>/css/plugins/textClue.css?_rv_=<%=resourceVersion%>" type="text/css" media="all" />
		<script type="text/javascript">
			
			//按钮绑定
			function bdan() {
				var btn_fh=jQuery("#btn_fh");//返回
				var btn_ck=jQuery("#btn_ck");//删除
				
				if (btn_fh != null) {
					btn_fh.click(function () {
						refRightContent('<%=systemPath %>/zfxg/wjdc/wjtj_cxWjtj.html');
					});
				}

				
				if (btn_ck != null) {
					btn_ck.click(function () {
						ckwj();
					});
				}
			}
			//数据加载
			jQuery(document).ready(function(){
				bdan();
				loadOption();
				dispFiledValue();
				
				var lxbt = jQuery('#lxid').val();
				if (lxbt != null) {
					jQuery('#'+lxbt).addClass("ha");
				}
			});

			//查询结果
			function searchResult() {
				
					if(jQuery("#rightContent").length > 0){
						ajaxSubForm("form_djxq","<%=systemPath %>/zfxg/wjdc/wjtj_djxq.html");
					}else{
						subForm("<%=systemPath %>/zfxg/wjdc/wjtj_djxq.html");
					}				
				
				
			}
			//加载SELECT标签列表数据
			function loadOption() {
				var selectObj = jQuery(".tj_select");
				if (selectObj != null && selectObj.length > 0) {
					jQuery(selectObj).each(function (i,n) {
						if (jQuery(n)) {
							jQuery(n).textClue({
								id:jQuery(n).attr("id"),
								divId:jQuery(n).attr("id")+"Div",
								url:_path+'/zfxg/wjdc/wjbase_getCxzdOption.html',
								listKey:'MC',
								listText:'MC',
								params:{bm:jQuery('#bm').val(),zd:jQuery(n).attr("id")}
							});
						}
					});
				}
			}
			//选中行首复选框
			function selectPk(obj,zdmc) {
				var pkObj = jQuery("input[name='pkValue']");
				jQuery(pkObj).each(function(i,n) {
					if (jQuery(n)) {
						jQuery(n).attr("checked",obj.checked);
					}
				});
			}
			
			//回显查询条件值
			function dispFiledValue() {
				var valueStr = jQuery('#valueStr').val();
				if (valueStr != "" && valueStr != null) {
					var array = valueStr.split("!!@@split!!@@");
					for (var i=0;i<array.length;i++) {
						var zdmc = array[i].split("!!=@@")[0];
						var zdz = array[i].split("!!=@@")[1];
						if (jQuery("#"+zdmc)) {
							jQuery("#"+zdmc).val(zdz);
						}	
					}
				}
			}
			
			//查看问卷
			function ckwj(){
				var pkObj = jQuery("input[name='pkValue']").filter(":checked");
				if(pkObj.length!=1){
					alert("请选择一条记录！");
					return false;					
				}
				//alert(pkObj[0].value);
				var djrid=pkObj[0].value;
				var wjid=jQuery("#wjid").val();
				var url= _path+"/zfxg/wjdc/stgl_yhdj.html?wjModel.wjid="+wjid+"&wjModel.djrid="+djrid+"&source=tj";
				window.open(url);
			}
			
			//页签切换
			function yqqh(lxid){
				jQuery("#lxid").val(lxid);
				var inputs=document.getElementById("tbody_obj").getElementsByTagName("input");
				for(var i=0;i<inputs.length;i++){
					inputs[i].value="";
				}
				searchResult();
			}
		</script>
	</head>

	<body>
	<div class="tab_cur">
			<p class="location">
				<em>您的当前位置:</em><a>问卷调查 - 问卷统计 - 答卷详情</a>
			</p>	
	</div>
	<s:form namespace="/zfxg/wjdc" action="wjtj_djxq" id="form_djxq" method="post" theme="simple">
		<!-- 表名 -->
		<input type="hidden" name="bm" id="bm" value="${sjyModel.bm }"/>
			<!-- 类型ID -->
			<input type="hidden" name="lxid" id="lxid" value="${lxbt }"/>
			<!-- 问卷ID -->
			<input type="hidden" name="wjid" id="wjid" value="${wjid }"/>
			<input type="hidden" name="valueStr" id="valueStr" value="${valueStr }"/>
			
			<!-- 循环出类型 -->
			<div class="comp_title">
		      <ul class="cla_lxbt">
				<s:iterator value="lxbtList" >
					 <s:if test="lxid == 'anonymous'">
					 	<li id="${lxid}"><a href="#" onclick="refRightContent('<%=systemPath %>/zfxg/wjdc/wjtj_djxqNmwj.html?lxid=${lxid }'+'&wjid=${wjid }');"><span>${lxmc}</span></a></li>
					 </s:if>
				</s:iterator>
		      </ul>
		    </div>
		    
		
		<div class="toolbox">
			<!-- 加载当前菜单栏目下操作     -->
			
			<div class="buttonbox">
			<ul id="but_ancd">
					<li>
						<a href="javascript:void(0);" id="btn_ck" class="btn_ck" >
							查看</a>
					</li>
					<li>
						<a href="javascript:void(0);" id="btn_fh" class="btn_fh" >
							返回</a>
					</li>
			</ul>
		</div>
			<!-- 加载当前菜单栏目下操作 -->
		</div>

		<div class="formbox">
			<table width="100%" class="dateline">
              <thead>
				<tr>
					<td width="10%">
						<input type="checkbox" name="ck" id="ck" onclick="selectPk(this,'pkValue')" />
					</td>
					<td>
						姓名
					</td>
					<td>
						答卷状态
					</td>
				</tr>
			</thead>
			<tbody>
				<%
				List<HashMap<String,Object>> rsList=(List<HashMap<String,Object>>)request.getAttribute("rsList");
				for(HashMap<String,Object> rs : rsList){
				%>
					<tr>
						<td><input type="checkbox" id="pkValue" name="pkValue" value="<%=rs.get("ZJZ")%>" /></td>
						<td><%=rs.get("ZJZMC")%></td>
						<td><%=rs.get("DJZT")%></td>
					</tr>	
				<%}%>
			</tbody>
			</table>
			<jsp:include page="/WEB-INF/pages/comm/pageFootMenu.jsp"></jsp:include>
		</div>
	</s:form>
	</body>
	  <s:if test="result != null && result != ''">
	  	<script>
	  	//refRightContent('<%=systemPath %>/zfxg/wjdc/wjffgl_cxFfwjxx.html?wjid='+jQuery('#wjid').val()+'&lxid='+jQuery('#lxid').val());
	  	alert('${result}','',{'clkFun':function(){refershParent()}});
	  	</script>
	  </s:if>
	
</html>
