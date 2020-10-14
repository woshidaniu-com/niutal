<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.woshidaniu.wjdc.dao.entites.XxglModel"%>
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
				//如果统计类型中就一个选项，直接选中
				if(jQuery("#tjtype option").length==2){
					jQuery("#tjtype").val(jQuery("#tjtype option").eq(1).val());
				}
			});

			//查询结果
			function searchResult() {
				var tjtype=jQuery("#tjtype").val();
				var stidy=jQuery("#stidy").val();
				
				if(tjtype==""){
					alert("请选择分组项或分组试题！");
					return false;
				}
				if(stidy==""){
					alert("请选择统计试题！");
					return false;
				}

					if(jQuery("#rightContent").length > 0){
						ajaxSubForm("form_djtj", "<%=systemPath %>/zfxg/wjdc/wjtj_jctjcspz.html");
					}else{
						subForm( "<%=systemPath %>/zfxg/wjdc/wjtj_jctjcspz.html");
					}				
				
				
			}
		</script>
	</head>

	<s:form namespace="/zfxg/wjdc" action="wjtj_jctjcspz" method="post" id="form_djtj" theme="simple">
	<body style="height: 950px">
		<!-- 表名 -->
		<input type="hidden" name="bm" id="bm" value="${sjyModel.bm }"/>
		
		<div class="tab_cur">
			<p class="location">
				<em>您的当前位置:</em><a>问卷调查 - 问卷统计 - 交叉统计</a>
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
					 <li id="${lxid}"><a href="#" onclick="refRightContent('<%=systemPath %>/zfxg/wjdc/wjtj_jctj.html?lxid=${lxid }'+'&wjid=${wjid }');"><span>${lxmc}</span></a></li>
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
		            <!-- tfoot>
					<tr>
                		<td colspan="6">
                				<div class="btn">
                   					<button type="button" id="search_go"
										onclick="searchResult();" >
										统 计
									</button>
									<button type="reset" onclick="searchReset();return false;">
										重 置
									</button>
			                  </div>
			                 </td>
			              </tr>
		            </tfoot-->
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
			              		统计类型&nbsp;<s:select name="tjtype" value="#{tjtype}" cssStyle="width:200px;" id="tjtype" list="jgList" listKey="TJTYPE" listValue="TJMC" headerKey="" headerValue=""></s:select>
			              	</td>
			              	<td>
			              		统计试题&nbsp;<s:select name="stidy" cssStyle="width:200px;" id="stidy" list="wjstList" listKey="stid" listValue="stmc" headerKey="" headerValue=""></s:select>
			              	</td>
							<td rowspan="1">
								<div class="btn">
                   					<button type="button" id="search_go"
										onclick="searchResult();" >
										统 计
									</button>
									<button type="reset" onclick="searchReset();return false;">
										重 置
									</button>
			                  </div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

		<div class="formbox">
			<table width="100%" class="dateline">
              <thead>
				<tr>
					<td colspan="${xy_colspan}">x/y</td>
					<s:iterator value="xxList" id="xx">
						<td colspan="2">${xx.xxmc}</td>
					</s:iterator>
					<td>小计</td>
				</tr>
			</thead>
			<tbody>
				<%
				List<HashMap<String,Object>> rsList=(List<HashMap<String,Object>>)request.getAttribute("rsList");
				List<XxglModel> xxList=(List<XxglModel>)request.getAttribute("xxList");
				String[] groupFields=(String[])request.getAttribute("groupFields");
				String xy_colspan=(String)request.getAttribute("xy_colspan");
				for(HashMap<String,Object> rs : rsList){
					%><tr><%
					for(int i=0;i<groupFields.length;i++){
						if(groupFields[i]!=null&&!"".equals(groupFields[i])){
							if("1".equals(xy_colspan)){
								%><td><%=rs.get("XXMC"+i)==null?"":rs.get("XXMC"+i)%></td><%
							}else{
								if(rs.get("XXMC"+i+"COLSPANNO")==null)
								if(rs.get("XXMC"+i+"ROWSPAN")!=null){//该处是用于处理多个分组字段需要合并单元格的处理
									%><td rowspan="<%=rs.get("XXMC"+i+"ROWSPAN") %>"
									<%if(rs.get("XXMC"+i+"COLSPAN")!=null){ %>
										colspan="<%=rs.get("XXMC"+i+"COLSPAN") %>"
									<%} %> 
										  >
										<%=rs.get("XXMC"+i)==null?"":rs.get("XXMC"+i)%></td><%
								}
							}
						}
					}
					for(XxglModel xx : xxList){
						%><td width="1%">
						<%=rs.get(xx.getXxid().toUpperCase()+"_RS")==null?"":rs.get(xx.getXxid().toUpperCase()+"_RS")%>
						</td>
						<td width="1%">
						<%=rs.get(xx.getXxid().toUpperCase()+"_RSBFB")==null?"":rs.get(xx.getXxid().toUpperCase()+"_RSBFB")%>
						</td><%
					}
					%><td><%=rs.get("ZRS")%></td><%
					%></tr><%
				}				
				 %>
				<s:iterator value="rsList" id="rs">
					<tr>
						<td>${rs.xxmc}</td>
						<td>${rs.stModel.stlxmc}</td>
						<td>${rs.stModel.stlxmc}</td>
					</tr>
				</s:iterator>
			</tbody>
			</table>
		</div>
	</body>
		<input type="hidden" name="result" id="result" value="${result}"/>
	  <s:if test="result != null && result != ''">
	  	<script>
	  	//refRightContent('<%=systemPath %>/zfxg/wjdc/wjtj_sttj.html?wjid='+jQuery('#wjid').val()+'&lxid='+jQuery('#lxid').val());}
	  	alert('${result}','',{'clkFun':function(){refershParent()});
	  	</script>
	  </s:if>
	</s:form>
</html>
