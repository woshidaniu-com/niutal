<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript"
			src="<%=systemPath%>/js/jquery/dragsort/jquery.dragsort-0.4.min.js?_rv_=<%=resourceVersion%>"></script>
		<style>
		.choose_yx{display: block;height: 13px;position: absolute;right: -3px;top: 2px;width: 13px;cursor:pointer;background: url("<%=stylePath %>/images/blue/ico_90.gif") no-repeat 0 0 !important;}
		.choose_wx{display: block;height: 13px;position: absolute;right: -3px;top: 2px;width: 13px;cursor:pointer;background: url("<%=stylePath %>/images/blue/ico_91.gif") no-repeat 0 0 !important;}
		
		.kzlist{
			  margin-top: 5px;
			  overflow: hidden;
			  border: 1px solid #f1e0b5;
			  width: 100%;
			  float: left;
			  background: #fffedf;
			  margin-bottom:3px;
		}
		.kzlist p{
			  float: left;
			  color: #61604d;
			  font: bold 12px/29px "宋体";
			  margin: 0px 12px;
		}
		.kzlist ul{
			  float: left;
		}
		.kzlist ul li{
			  list-style-type:none;
			  float: left;
			  margin-left: 12px;
			  margin-top: 3px;
		}
		.kzlist ul li a{
			  height: 22px;
			  padding: 0px 8px;
			  color: #61604d;
			  font: normal 12px/22px "宋体";
			  display: block;
			  float: left;
		}
		.kzlist ul li a:hover{
			 text-decoration: initial
		}
		.kzlist ul li a span{
			   padding-left: 7px;
			   display: none;
			   width: 10px;
		}
		.kzlist ul li a.cur{
			  background: #FAAE49;
		}
		.kzlist ul li a i{
			font-style: normal;
		}
		.kzlist ul li a.cur0{
			  border: 1px solid #a09f8c;
			  height: 20px;
			  line-height: 20px;
			  padding: 0px 7px;
		}
		.kzlist ul li a span.del:hover{
			color:red;
			font-weight: bolder;
			font-size: 13px;
		}
		</style>
		<script type="text/javascript" src="<%=systemPath%>/js/dc/export.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript">
			var isModify = false;
			var defval, defvalID;
		
			jQuery(function() {
				jQuery("#unselectUl, #selectUl").dragsort({
					dragSelector : "label",
					dragBetween : true,
					dragEnd : saveOrder,
					placeHolderTemplate : "<li><label class='college_li college_checkbox' style='border:#155FBE 1px dotted;background:#CBE4F8;height:20px;line-height:20px!important;*height:28px;width:90px;padding:3px 0px;text-indent: 15px;'></label></li>"
				});
				
				//默认全部选中
				jQuery('#unselectUl li span').click();
			});
		
			//拖动后
			function saveOrder() {
				isModify = true;
				jQuery("#unselectUl").find(":input").attr("name","unselectCol");
				
				var unspan = jQuery("#unselectUl").find(".choose_yx");
				unspan.parent().append("<span class='choose_wx' onclick='select(this)'></span>");
				unspan.remove();
				
				var span = jQuery("#selectUl").find(".choose_wx");
				span.parent().append("<span class='choose_yx' onclick='unselect(this)'></span>");
				span.remove();
			};
			
		</script>
	</head>

	<body>
		<s:form id="exportForm" theme="simple" style="margin-left:5px;margin-right:5px">
			<s:hidden name="dcclbh" id="dcclbh"/>
				<div class="kzlist">
			    	<p>偏好设置</p>
			      	<ul>
			      		<s:iterator value="phList" id="c" status="ph">
			      			<li>
			      				<a class="cur0" href="javascript:;">
			      					<i onclick="selectPh('<s:property value="#c.id" />',this);" class="select"><s:property value="#c.mc" /></i>
			      					<span title="点击删除" class="del" style="display: inline-block;" onclick="deletePh('<s:property value="#c.id" />','<s:property value="#c.mc" />',this);">×</span>
			      				</a>
			      			</li>
			      		</s:iterator>
			        </ul>
			    </div>
				
				<table width="100%" class="fieldlist"
				<tbody>
					<tr>
						<td width="50%" >
							<div class="tab_box">
								<h3>
									可选择导出列
								</h3>
								<div class="demo_college" style="height: 365px; width: 100%;overflow-y:scroll;*position:relative;*z-index:1">
									<div style="height: 365px; width: 100%;">
										<ul id="unselectUl" >
											<s:iterator value="configList" id="c" status="config">
													<li style="position:relative">
														<label class="college_li college_checkbox" style="height:20px;line-height:20px!important;font-size:12px!important;*height:28px;width:90px;padding:3px 0px;text-indent: 15px;">
															<s:property value="#c.zdmc" />
															<input name="unselectCol" type="hidden"
																value="<s:property value="#c.zd"/>@<s:property value="#c.zdmc"/>" />
														</label>
														<span class="choose_wx" onclick="select(this);"></span>
													</li>
											</s:iterator>
										</ul>
									</div>
								</div>
							</div>
						</td>
						<td width="50%">
							<div class="tab_box" >
								<h3>
									已选择导出列<font color="red">（拖拽可以排序）</font>
								</h3>
								<div class="demo_college" style="height: 365px; width: 100%;overflow-y:scroll;*position:relative;*z-index:1">
									<div style="height: 365px; width: 100%; ">
										<ul id="selectUl">
											<!-- <s:iterator value="configList" id="c" status="config">
												<s:if test="#c.zt==1">
													<li style="position:relative">
														<label class="college_li college_checkbox" style="height:20px;line-height:20px!important;font-size:12px!important;*height:28px;width:140px;padding:3px 0px;text-indent: 15px;">
															<s:property value="#c.zdmc" />
															<input name="selectCol" type="hidden"
																value="<s:property value="#c.zd"/>@<s:property value="#c.zdmc"/>" />
														</label>
														<span class="choose_yx" onclick="unselect(this);"></span>
													</li>
												</s:if>
											</s:iterator> -->
										</ul>
									</div>
								</div>
							</div>
						</td>
					</tr>
				</tbody>
			</table>	
			<div style="height:30px"></div>
			<table border="0" class="formlist" id="below" 
			style="position: fixed; _position: absolute; bottom: 0;z-index:2;width:initial;width:auto;">
				<tfoot>
					<tr>
						<td colspan="2">	
							<div class="btn" >
							<label style="font-size: 12px">
								<input type="radio" name="exportWjgs" value="xls" checked="checked">EXCEL表格
							</label>
<!-- 							<label style="font-size: 12px"> -->
<!-- 								<input type="radio" name="exportWjgs" value="dbf" >DBF文件 -->
<!-- 							</label> -->
							</div>
						</td>
					</tr>
				</tfoot>
			</table>	
		</s:form>
	</body>
</html>
