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
			function wjdxqd() {
				var pkObj = jQuery("input[name='pkValue']");
				var flag = false;
				jQuery(pkObj).each(function(i,n) {
					if (jQuery(n) && jQuery(n).attr("checked")) {
						flag = true;
						return;
					}
				});
				if (flag) {
					var _do = function(){
						subForm("<%=systemPath %>/zfxg/wjdc/wjffgl_zjWjffxx.html?doType=save");
					}
					showConfirmDivLayer('您确定要分发当前选择的对象吗？',{'okFun':_do});
				} else {
					var _do = function(){
						subForm("<%=systemPath %>/zfxg/wjdc/wjffgl_zjWjffxx.html?doType=save&dx=all");
					}
					//showConfirmDivLayer('您确定要分发到当前查询的所有对象吗？',{'okFun':_do});
					//根据条件检查当前是否还有数据，有则可以提交，否则不能提交
					var map = {};
					map["bm"] = jQuery("#bm").val();
					var tjObj = jQuery("#tbody_obj").find(":input");
					if (tjObj != null) {
						jQuery(tjObj).each(function (i,n) {
							if (jQuery(n) && (jQuery(n).val()!=null) && (jQuery(n).val()!="") && ("cx_"==jQuery(n).attr("name").substr(0,3))) {
								map[jQuery(n).attr("name")] = jQuery(n).val();
							}
						});
					}
					jQuery.ajax({
						type: "POST",
					    url: "<%=systemPath %>/zfxg/wjdc/wjbase_jcWjdxsj.html",
					    data: map,
					    dataType: "json",
					    success: function(data){
							if (data) {
								//subForm("<%=systemPath %>/zfxg/wjdc/wjffgl_zjWjffxx.html?doType=save");
								showConfirmDivLayer('您确定要分发到当前查询的所有对象吗？',{'okFun':_do});
							} else {
								alert("未找到任何数据，请更换查询条件再试!");
							}
						}
					});
					
				}
			}
			
			//按钮绑定
			function bdan() {
				var btn_qd=jQuery("#btn_qd");//增加
				var btn_gb=jQuery("#btn_gb");//删除
				
				if (btn_qd != null) {
					btn_qd.click(function () {
						wjdxqd();
					});
				}

				
				if (btn_gb != null) {
					btn_gb.click(function () {
						iFClose();
					});
				}
			}
			//数据加载
			jQuery(document).ready(function(){
				bdan();
				loadOption();
				dispFiledValue();
			});

			//查询结果
			function searchResult() {
				subForm("<%=systemPath %>/zfxg/wjdc/wjffgl_zjWjffxx.html");
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

			function selectUser(){
				var url = _path + "/commons/q_selectUser.html";	 
				showWindow('选择用户',700,480,url);
			}
			
			function selectUserCallback(data){
				if(data != null && data.length > 0){
					jQuery('input[name=cx_zghs]').val(data.join(','));
					//subForm("<%=systemPath %>/zfxg/wjdc/wjffgl_zjWjffxx.html");
				}
			}
		</script>
	</head>

	<body>
	<div class="prompt">
				<h3>
					<span>系统提示：</span>
				</h3>
				<p>
					<font color="red">未选择任何记录点击确定按钮时，将会分发到当前查询的所有对象</font>
				</p>
				<a class="close" title="隐藏" onclick="this.parentNode.style.display='none';"></a>
			</div>
	<s:form namespace="/zfxg/wjdc" action="wjffgl_zjFfwjxx" id="zjffwj" method="post" theme="simple">
		<!-- 表名 -->
		<input type="hidden" name="bm" id="bm" value="${sjyModel.bm }"/>
			<!-- 类型ID -->
			<input type="hidden" name="lxid" id="lxid" value="${lxbt }"/>
			<!-- 问卷ID -->
			<input type="hidden" name="wjid" id="wjid" value="${wjid }"/>
			<input type="hidden" name="valueStr" id="valueStr" value="${valueStr }"/>
		
		<div class="toolbox">
			<!-- 加载当前菜单栏目下操作     -->
			
			<div class="buttonbox">
			<ul id="but_ancd">
					<li>
						<a href="javascript:void(0);" id="btn_qd" class="btn_qd" >
							确定</a>
					</li>
					<li>
						<a href="javascript:void(0);" id="btn_gb" class="btn_fh" >
							关闭</a>
					</li>
			</ul>
		</div>
			
			<!-- 加载当前菜单栏目下操作 -->

			<div class="searchtab">
		          <table width="100%">
		            <tfoot>
					<tr>
                <td colspan="6">
                	<div class="btn">
                   					<button type="button" id="search_go"
										onclick="searchResult();" >
										查 询
									</button>
									<button type="reset" onclick="searchReset();return false;">
										重 置
									</button>
			                  </div>
			                 </td>
			              </tr>
		            </tfoot>
		            <tbody id="tbody_obj">
		              <tr>
		                
		              	<!-- 循环出查询条件列表 -->
		              	<s:iterator value="tjList" id="tjobj" status="tjsta">
		              		<s:if test="(#tjsta.index%3==0) && (#tjsta.index != tjList.size() && (#tjsta.index!=0))">
		              			</tr><tr>
		              		</s:if>
		              		<th>
		              			${zdmc }
		              		</th>
		              		<td>
		              			<input type="text" name="cx_${zd}" style="width:150px" maxlength="15" id="${zd }" class="tj_${bqlx }"/>
		              		</td>
		              	</s:iterator>
		              </tr>
		              <s:if test="lxid == 'teacher'">
		              	<tr>
		              		<th>用户</th>
		              		<td>
		              			<input type="text" name="cx_zghs" id="zghs"/>	
		              			<button type="reset" onclick="selectUser();return false;">选择</button>
		              		</td>
		              	</tr>	
           			  </s:if>
		            </tbody>
		          </table>
        	</div>

		</div>

		<div class="formbox">
			<table width="100%" class="dateline">
              <thead>
				<tr>
					<td>
						<input type="checkbox" name="ck" id="ck" onclick="selectPk(this,'pkValue')"/>
					</td>
				<s:iterator value="titList" id="titObj">
					<td>
						${mc }
					</td>
				</s:iterator>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="rsList" id="rsObj">
					<tr>
						<s:iterator value="rsObj" id="zd" status="sta">
							<td>
							<s:if test="#sta.index==0">
								<input type="checkbox" id="pkValue" name="pkValue" value="${zd }" />
							</s:if>
							<s:else>
								<s:if test="#zd != null && #zd != 'null'">
									${zd }
								</s:if>
							</s:else>
							</td>
						</s:iterator>
					</tr>
				</s:iterator>
			</tbody>
			</table>
			<jsp:include page="/WEB-INF/pages/comm/pageFootMenu.jsp"></jsp:include>
		</div>
	<input type="hidden" name="result" id="result" value="${result}"/>
	  <s:if test="result != null && result != ''">
	  	<script>
	  	//refRightContent('<%=systemPath %>/zfxg/wjdc/wjffgl_cxFfwjxx.html?wjid='+jQuery('#wjid').val()+'&lxid='+jQuery('#lxid').val());
	  	alert('${result}','',{'clkFun':function(){refershParent()}});
	  	</script>
	  </s:if>
	</s:form>
	
	</body>
</html>
