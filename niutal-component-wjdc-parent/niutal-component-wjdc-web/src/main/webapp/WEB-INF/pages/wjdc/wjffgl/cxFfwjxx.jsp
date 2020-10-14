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
		
			function scwjdxxx() {
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
						if(jQuery("#rightContent").length > 0){
							ajaxSubForm("cxwjff", "<%=systemPath %>/zfxg/wjdc/wjffgl_cxFfwjxx.html?doType=del");
						}else{
							subForm("<%=systemPath %>/zfxg/wjdc/wjffgl_cxFfwjxx.html?doType=del");
						}
					}
					showConfirmDivLayer('您确定要删除选择的记录吗？',{'okFun':_do});
				} else {
					alert("请选择您要删除的记录！");
				}
			}
			//问卷对象发布
			function wjdxfb() {
				var _do = function(){
						if(jQuery("#rightContent").length > 0){
							ajaxSubForm("cxwjff", "<%=systemPath %>/zfxg/wjdc/wjffgl_cxFfwjxx.html?doType=ff");
						}else{
							subForm("<%=systemPath %>/zfxg/wjdc/wjffgl_cxFfwjxx.html?doType=ff");
						}					
				}
				showConfirmDivLayer('您确定要发布所有的问卷对象吗？',{'okFun':_do});
			}

			//问卷对象取消分发
			function wjdxqxfs(){
				var _do = function(){
										
						if(jQuery("#rightContent").length > 0){
							ajaxSubForm("cxwjff", "<%=systemPath %>/zfxg/wjdc/wjffgl_cxFfwjxx.html?doType=qxff");
						}else{
							subForm("<%=systemPath %>/zfxg/wjdc/wjffgl_cxFfwjxx.html?doType=qxff");
						}					
					
				}
				showConfirmDivLayer('您确定要取消发布所选的问卷对象吗？',{'okFun':_do});
			}
			
			//按钮绑定
			function bdan() {
				var btn_zj=jQuery("#btn_zj");//增加
				var btn_sc=jQuery("#btn_sc");//删除
				var btn_fb=jQuery("#btn_fb");//发布
				var btn_fh=jQuery("#btn_fh");//返回
				var btn_qxfb=jQuery("#btn_qxfb");//取消分发
				
				if (btn_zj != null) {
					btn_zj.click(function () {
						//宽度改为750，iframe下内嵌。by ligl-20130701
						showWindow('选择分发对象',750,545,"<%=systemPath %>/zfxg/wjdc/wjffgl_zjWjffxx.html?lxid="+jQuery('#lxid').val()+"&wjid="+jQuery('#wjid').val());
					});
				}
				
				if (btn_sc != null) {
					btn_sc.click(function () {
						scwjdxxx();
					});
				}
				
				if (btn_fb != null) {
					btn_fb.click(function () {
						wjdxfb();
					});
				}

				if (btn_qxfb != null){
					btn_qxfb.click(function () {
						wjdxqxfs();
					});
				} 
				
				if (btn_fh != null) {
					btn_fh.click(function () {
						refRightContent('<%=systemPath %>/zfxg/wjdc/wjffgl_cxWjffxx.html');
					});
				}
			}
			//数据加载
			jQuery(document).ready(function(){
				bdan();
				
				var lxbt = jQuery('#lxid').val();
				if (lxbt != null) {
					jQuery('#'+lxbt).addClass("ha");
				}
				
				loadOption();
				
				dispFiledValue();
			});

			//查询结果
			function searchResult() {
				if(jQuery("#rightContent").length > 0){
					ajaxSubForm("cxwjff", "<%=systemPath %>/zfxg/wjdc/wjffgl_cxFfwjxx.html");
				}else{
					subForm("<%=systemPath %>/zfxg/wjdc/wjffgl_cxFfwjxx.html");
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
				if (valueStr != "") {
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
		</script>
	</head>

	<body style="height: 950px">
	<s:form namespace="/zfxg/wjdc" action="wjffgl_cxFfwjxx" method="post" id="cxwjff" theme="simple">
		<!-- 表名 -->
		<input type="hidden" name="bm" id="bm" value="${sjyModel.bm }"/>
		
		<div class="tab_cur">
			<p class="location">
				<em>您的当前位置:</em><a>问卷调查 - 问卷分发 - 分发问卷</a>
			</p>	
		</div>
			<!-- 类型ID -->
			<input type="hidden" name="lxid" id="lxid" value="${lxbt }"/>
			<input type="hidden" name="wjid" id="wjid" value="${wjid }"/>
			<input type="hidden" name="valueStr" id="valueStr" value="${valueStr }"/>
			
			<!-- 循环出类型 -->
			<div class="comp_title">
		      <ul class="cla_lxbt">
				<s:iterator value="lxbtList" >
					<s:if test="lxid != 'anonymous'">
					 	<li id="${lxid}"><a href="#" onclick="refRightContent('<%=systemPath %>/zfxg/wjdc/wjffgl_cxFfwjxx.html?lxid=${lxid }'+'&wjid=${wjid }');"><span>${lxmc}</span></a></li>
					</s:if>
				</s:iterator>
		      </ul>
		    </div>
		
		<div class="toolbox">
			<!-- 加载当前菜单栏目下操作     -->
			
			<div class="buttonbox">
			<ul id="but_ancd">
					<li>
						<a href="javascript:void(0);" id="btn_zj" class="btn_zj" >
							增加</a>
					</li>
					<li>
						<a href="javascript:void(0);" id="btn_sc" class="btn_sc" >
							删除</a>
					</li>
					<!-- 
					<li>
						<a href="javascript:void(0);" id="btn_fb" class="btn_shtg" >
							分发</a>
					</li>
					<li>
						<a href="javascript:void(0);" id="btn_qxfb" class="btn_shtg" >
							取消分发</a>
					</li>
					 -->
					<li>
						<a href="javascript:void(0);" id="btn_fh" class="btn_fh" >
							返回</a>
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
						<s:iterator value="rsObj" id="zd" status="sta" >
							
							<s:if test="#sta.index==0">
							<td>
								<input type="checkbox" id="pkValue" name="pkValue" value="${zd }" <s:iterator value="rsObj" id="innerzd" begin="1" end="1">${innerzd}</s:iterator>/>
								</td>
							</s:if>
							<s:elseif test="#sta.index==1">
								
							</s:elseif>
							<s:else>
								<td >
									<s:if test="#zd != null && #zd != 'null'">
										${zd }
									</s:if>
								</td>
							</s:else>
							
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
	  	alert('${result}','',{'clkFun':function(){refershParent()}});
	  	</script>
	  </s:if>
	</s:form>
	</body>

</html>
