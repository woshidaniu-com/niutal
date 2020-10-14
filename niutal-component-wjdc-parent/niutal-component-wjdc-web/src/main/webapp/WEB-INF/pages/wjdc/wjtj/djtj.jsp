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
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/wjdc/wjtj.js?_rv_=<%=resourceVersion%>"></script>
		<link rel="stylesheet" href="<%=systemPath %>/css/plugins/textClue.css?_rv_=<%=resourceVersion%>" type="text/css" media="all" />
		<script type="text/javascript">
		
			//按钮绑定
			function bdan() {
				var btn_fh=jQuery("#btn_fh");//返回
				
				if (btn_fh != null) {
					btn_fh.click(function () {
						refRightContent('<%=systemPath %>/zfxg/wjdc/wjtj_cxWjtj.html');
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
				setInit();//优化界面选项
			});

			//查询结果
			function searchResult() {
				
					if(jQuery("#rightContent").length > 0){
						ajaxSubForm("form_djtj", "<%=systemPath %>/zfxg/wjdc/wjtj_djtj.html");
					}else{
						subForm("<%=systemPath %>/zfxg/wjdc/wjtj_djtj.html");
					}
				
			}
		</script>
	</head>

	<s:form namespace="/zfxg/wjdc" action="wjtj_djtj" method="post" id="form_djtj" theme="simple">
	<body style="height: 950px">
		<!-- 表名 -->
		<input type="hidden" name="bm" id="bm" value="${sjyModel.bm }"/>
		
		<div class="tab_cur">
			<p class="location">
				<em>您的当前位置:</em><a>问卷调查 - 问卷统计 - 答卷统计</a>
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
					 <li id="${lxid}"><a href="#" onclick="refRightContent('<%=systemPath %>/zfxg/wjdc/wjtj_djtj.html?lxid=${lxid }'+'&wjid=${wjid }');"><span>${lxmc}</span></a></li>
				</s:iterator>
		      </ul>
		    </div>
		
		<div class="toolbox">
			<!-- 加载当前菜单栏目下操作     -->
			
			<div class="buttonbox">
			<ul id="but_ancd">
<!--					<li>-->
<!--						<a href="javascript:void(0);" id="btn_zj" class="btn_zj" >-->
<!--							增加</a>-->
<!--					</li>-->
<!--					<li>-->
<!--						<a href="javascript:void(0);" id="btn_sc" class="btn_sc" >-->
<!--							删除</a>-->
<!--					</li>-->
<!--					<li>-->
<!--						<a href="javascript:void(0);" id="btn_fb" class="btn_shtg" >-->
<!--							分发</a>-->
<!--					</li>-->
					<li>
						<a href="javascript:void(0);" id="btn_fh" class="btn_fh" >
							返回</a>
					</li>
			</ul>
		</div>
			
			<!-- 加载当前菜单栏目下操作 -->

			<div class="searchtab">
		          <table width="100%">
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
			<div class="searchbox">
				<table class=searchbox_cx width="100%">
					<tbody>
						<tr>
							<td>
								<s:if test="jgList.size()>0">
								分组项&nbsp;
								<s:select headerKey="" headerValue="" list="jgList" listKey="zd"
									listValue="zdmc" name="groupFields"></s:select>
								</s:if>
							</td>
							<s:if test="(tjList!=null && tjList.size() > 0) || (jgList!=null && jgList.size()>0)">
							<td>
								<div class="btn">
									<button type="button" id="search_go" onclick="searchResult();">
										统 计
									</button>
									<button type="reset" onclick="searchReset();return false;">
										重 置
									</button>
								</div>
							</td>
							</s:if>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="formbox">
			<table width="100%" class="dateline">
              <thead>
				<tr>
				<s:iterator value="titList" id="titObj">
					<td>
						${zdmc}
					</td>
				</s:iterator>
					<td>应答卷人数</td>
					<td>已答卷人数</td>
					<td>已答卷人数百分比</td>
				</tr>
			</thead>
			<tbody>
				<%
				List<HashMap<String,Object>> rsList=(List<HashMap<String,Object>>)request.getAttribute("rsList");
				String[] groupFields=(String[])request.getAttribute("groupFields");
				for(HashMap<String,Object> rs : rsList){
					%><tr><%
					for(String gf : groupFields){
						%><td><%=rs.get(gf.toUpperCase())==null?"":rs.get(gf.toUpperCase())%></td><%
					}
					%><td><%=rs.get("WJFFRS")%></td><%
					%><td><%=rs.get("WJDJRS")%></td><%
					%><td><%=rs.get("WJDJRSBFB")%>%</td><%
					%></tr><%
				}				
				 %>
			</tbody>
			</table>
			<div style="display: none;">
				<jsp:include page="/WEB-INF/pages/comm/pageFootMenu.jsp"></jsp:include>
			</div>
		</div>
	</body>
		<input type="hidden" name="result" id="result" value="${result}"/>
	  <s:if test="result != null && result != ''">
	  	<script>
	  	//refRightContent('<%=systemPath %>/zfxg/wjdc/wjtj_djtj.html?wjid='+jQuery('#wjid').val()+'&lxid='+jQuery('#lxid').val());}
	  	alert('${result}','',{'clkFun':function(){refershParent()});
	  	</script>
	  </s:if>
	</s:form>
</html>
