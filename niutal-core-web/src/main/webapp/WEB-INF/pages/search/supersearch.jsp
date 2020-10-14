<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.woshidaniu.util.base.MessageUtil"%>
<%
String _supersearch_version_ = MessageUtil.getText("niutal_resource_version");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/search/plugin/config.js?_rv_=<%=_supersearch_version_%>"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/search/plugin/search_html_creater.js?_rv_=<%=_supersearch_version_%>"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/search/search.js?_rv_=<%=_supersearch_version_%>"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/window/ymPrompt.js?_rv_=<%=_supersearch_version_%>"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery/loadmask/jquery.loadmask.min.js?_rv_=<%=_supersearch_version_%>"></script>
		<link rel="stylesheet" href="<%=request.getContextPath() %>/js/window/skin/zfstyle/ymPrompt.css?_rv_=<%=_supersearch_version_%>" type="text/css" media="all" />
		<link rel="stylesheet" href="<%=request.getContextPath() %>/css/plugins/jquery.loadmask.css?_rv_=<%=_supersearch_version_%>" type="text/css" media="all" />
		<link rel="stylesheet" href="<%=request.getContextPath() %>/css/search/supersearch_plugin.css?_rv_=<%=_supersearch_version_%>" type="text/css" media="all" />
		<style type="text/css">
			.search_advanced .prop-item li a{
				margin-top: 4px !important;
			}
			.options_type dd {
				width: 200px !important;
				}
			.options_type.hover dd {
				border-width: 1px 1px 1px 1px !important;
				}
		</style>
	</head>
	<body>	
		<input type="hidden" id="GLOBAL_CONFIG" value="for super search use only"  
												context-path="<%=request.getContextPath()%>" 
												config-id="<%=request.getParameter("id")%>"
												/>
			
			<script type="text/javascript">
			/**
			 * 全选 判断是否选择
			 */
			function isShowCheckBox(){
				var cxtj_cz =jQuery("#cxtj_cz:checked").val();
				if(cxtj_cz!=null && cxtj_cz=="on"){
					return true;
				}
				return false;
			}
			
			jQuery(function(){
				//step.1 初始化高级查询配置，并生成html骨架
				initialSuperSearchConfig();
				 //step.2 绑定页面事件处理函数V1.0
				bindSearchHtmlEventListener();
				//step.3 设置默认选中的条件
				initialSuperSearchDefautlCondition(<%=request.getParameter("defaultCondition")%>);
			});
		</script>
		<input type="hidden" id="GLOBAL_DATA_REPOSITORY"  value="for super search use only" />
		
		<div class="searchtab" id="searchtab">
			<div id="searchDiv">
				<div class="search_advanced">
					<div class="adv_filter">
						<table border="0" width="100%">
							<tbody>
								<tr>
									<td >
										<div class="searchbox2" style="width:365px">
											<div id="mhcx_options" class="options_type">
												<dl>
													<dt>
														<input type="text" readonly="readonly" id="text"
															name="sm.keyTypeStr" value="全部">
															<input type="text" name="sm.keyType" value="" 
																id="blurType">
																<b></b>
													</dt>
													<dd id="dd_mhcx_lx" style="display: none;">
														
													</dd>
												</dl>
											</div>
											<div id="inputbox">
												<input type="text" class="birds" value="请输入关键词"
													id="blurValue" style="width:208px">
											</div>
											<input type="button" id="search_go" onclick="searchResult()"
												class="sreachbtn" value="查询">
										</div>
									</td>
								</tr>
							</tbody>
						</table>
						<%
							//是否显示按条件操作
							String showCheckbox = request.getParameter("showCheckbox");
							if(showCheckbox!=null && "1".equals(showCheckbox)){
								%>
									<label style="cursor: pointer;" ><input type="checkbox" name="cxtj_cz" id="cxtj_cz" />全选</label>
								<%
							}
						%>
						
					</div>
					<div style="display: none;" id="search_selected" class="selected-attr">
						<h3>
							已选条件：
						</h3>
						<dl id="dl_choice"></dl>
						<script type="text/javascript">
							//jQuery('#dl_choice').data('defaultCondition' ,  <%=request.getParameter("defaultCondition")%>);
						</script>
					</div>
					<div class="prop-item" id="div_searchContent" style="display: none;">
					</div>
				</div>
				<div class="more--item_bottom" style="clear: both;">
					<p>
						<a id="a_id_more" class="down" href="javascript:void(0);">展开</a>
					</p>
				</div>
			</div>
		</div>
	</body>
</html>

