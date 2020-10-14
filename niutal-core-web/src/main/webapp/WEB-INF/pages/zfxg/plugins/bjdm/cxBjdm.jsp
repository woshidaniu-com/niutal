<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/bjdm.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript">
			jQuery(function(){
				var bjdmGrid = new BjdmGrid();
				loadJqGrid("#tabGrid","#pager",bjdmGrid);
				bdan();
				
				jQuery("#bmdm_id_lsbm").bind("change",function(){
					var xydm = jQuery(this).val();
					jQuery.post(_path+"/zfxg/zydm/cxZydmByXydm.html",{bmdm_id_lsbm:xydm},function(data){
						jQuery("#zydm_id option:not(:first)").remove();
						
						jQuery.each(data,function(i,n){
							jQuery("#zydm_id").append("<option value='"+data[i]["zydm_id"]+"'>"+data[i]["zymc"]+"</option>");
						});
					},'json');
				});
			});
			
			function clearBindedEvents(){
				jQuery("#btn_zj").unbind();
				jQuery("#btn_sc").unbind();
				jQuery("#btn_xg").unbind();
				jQuery("#btn_dc").unbind();
				jQuery("#btn_dr").unbind();
			}
		</script>
	</head>

	<body>
<!-- 		<div class="tab_cur"> -->
<!-- 			<p class="location"> -->
<%-- 				<em>您的当前位置:</em>${currentMenu} --%>
<!-- 			</p> -->
<!-- 		</div> -->

		<div id="divBody">
			<div class="searchtab">
				<s:form  method="post" action="/zfxg/xydm/cxXydm.html" theme="simple" id="form1">
					<table width="100%" border="0" id="searchTab">
						<tbody>
							<tr>
								<th>班级代码</th>
								<td>
									<input type="text" name="bjdm" id="bjdm"/>
								</td>
								<th>班级名称</th>
								<td>
									<input type="text" name="bjmc" id="bjmc"/>
								</td>
								<th>学院</th>
								<td>
									<s:select list="xydmList" id="bmdm_id_lsbm" 
											  name="bmdm_id_lsbm" listKey="bmdm_id" 
									  		  listValue="bmmc" headerKey="" headerValue="---请选择---"
									  		  cssStyle="width:180px;"
								    ></s:select>
								</td>
							</tr>
							<tr>
								<th>年级</th>
								<td>
									<input type="text" name="njdm_id" id="njdm_id"/>
								</td>
								<th>专业</th>
								<td>
									<s:select list="zydmList" id="zydm_id" 
											  name="zydm_id" listKey="zydm_id" 
									  		  listValue="zymc" headerKey="" headerValue="---请选择---"
									  		  cssStyle="width:180px;"
								  	></s:select>
								</td>
								<td colspan="2">
									<div class="btn">
										<button type="button" class="btn_cx" id="search_go"
											onclick="searchResult();return false;">
											查 询
										</button>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</s:form>
			</div>
				
			<div class="compTab">
				<div class="comp_title" id="comp_title">
			      <ul>
			        <li><a href="javascript:void(0);" onclick="clearBindedEvents();jQuery('#mainBody').load('<%=request.getContextPath() %>/zfxg/xydm/cxXydm.html');"><span>学院</span></a></li>
			        <li><a href="javascript:void(0);" onclick="clearBindedEvents();jQuery('#divBody').load('<%=request.getContextPath() %>/zfxg/zydm/cxZydm.html');"><span>专业</span></a></li>
					<li class="ha"><a href="javascript:void(0);" ><span>班级</span></a></li>
			      </ul>
			    </div>
			</div>	
				
			<div class="formbox">
				<table id="tabGrid"></table>
				<div id="pager"></div>
			</div>
		</div>
	</body>
</html>
