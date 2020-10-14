<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<body>
<s:if test=" funcModel != null ">
<div class="tree well">
	<ul>
		<li id="<s:property value="funcModel.func_code"/>" >
			<span>
		       <i class="bigger-120 icon-collapse-alt"></i> <s:property value="funcModel.func_name"/>
		       <s:if test="funcModel.childFuncList != null and funcModel.childFuncList.size() > 0">
		       <span class="badge badge-default"><s:property value="funcModel.childFuncList.size()"/></span>
		       </s:if>
		       <s:elseif test="funcModel.funcBtnList != null and funcModel.funcBtnList.size() > 0">
		       <span class="badge badge-default"><s:property value="funcModel.funcBtnList.size()"/></span>
		       </s:elseif>
     		   <s:if test="funcModel.func_displayed == 1"><b class="green">【可见】</b></s:if><s:else><b class="light-grey">【不可见】</b></s:else>
			</span>
			<s:if test="funcModel.childFuncList != null">
			<ul>
				<s:iterator value="funcModel.childFuncList" var="childNode_1" status="stat_1">
					<li id="<s:property value="#childNode_1.func_code"/>" >
						<span>
							<s:if test="#childNode_1.childFuncList != null or #childNode_1.funcBtnList != null">
								<s:if test="#stat_1.index == 0">
								<i class="bigger-120 icon-collapse-alt"></i>
								</s:if>
								<s:else>
								<i class="bigger-120 icon-expand-alt"></i>
								</s:else>
							</s:if>
							<s:property value="#childNode_1.func_name"/>
							<s:if test="#childNode_1.childFuncList != null ">
						       <span class="badge badge-default"><s:property value="#childNode_1.childFuncList.size()"/></span>
						    </s:if>
						    <s:elseif test="#childNode_1.funcBtnList != null ">
						       <span class="badge badge-default"><s:property value="#childNode_1.funcBtnList.size()"/></span>
						    </s:elseif>
							<s:if test="#childNode_1.func_displayed == 1"><b class="green">【可见】</b></s:if><s:else><b class="light-grey">【不可见】</b></s:else>
						</span>
						<s:if test="func_xgzdycd == 1 && #childNode_1.func_user_defined == 1">
						<!-- 菜单编辑功能按钮 -->
						<span style="border:none;background-color: #FFF !important; ">
							<div class="btn-group btn-group-xs">
								<s:if test="#childNode_1.func_designed == 1">
								<button class="btn btn-design-edit btn-default" data-func_guid="<s:property value="#childNode_1.func_guid"/>" data-func_code="<s:property value="#childNode_1.func_code"/>" data-func_name="<s:property value="#childNode_1.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="编辑自定义功能"><i class="smaller-85 glyphicon glyphicon-edit"></i></button>
								<button class="btn btn-design-globe btn-default" data-func_guid="<s:property value="#childNode_1.func_guid"/>" data-func_code="<s:property value="#childNode_1.func_code"/>" data-func_name="<s:property value="#childNode_1.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="预览自定义功能页面"><i class="smaller-85 glyphicon glyphicon-globe"></i></button>
								<button class="btn btn-design-repeat btn-default" data-func_guid="<s:property value="#childNode_1.func_guid"/>" data-func_code="<s:property value="#childNode_1.func_code"/>" data-func_name="<s:property value="#childNode_1.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="重置自定义功能页面"><i class="smaller-85 glyphicon glyphicon-repeat"></i></button>
								<button class="btn btn-design-remove btn-default" data-func_guid="<s:property value="#childNode_1.func_guid"/>" data-func_code="<s:property value="#childNode_1.func_code"/>" data-func_name="<s:property value="#childNode_1.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="删除自定义功能"><i class="smaller-85 glyphicon glyphicon-trash"></i></button>
								</s:if>
								<s:else>
								<button class="btn btn-define-edit btn-default" data-func_guid="<s:property value="#childNode_1.func_guid"/>" data-func_code="<s:property value="#childNode_1.func_code"/>" data-func_name="<s:property value="#childNode_1.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="编辑功能菜单"><i class="smaller-85 glyphicon glyphicon-edit"></i></button>
								<button class="btn btn-define-remove btn-default" data-func_guid="<s:property value="#childNode_1.func_guid"/>" data-func_code="<s:property value="#childNode_1.func_code"/>" data-func_name="<s:property value="#childNode_1.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="删除功能菜单"><i class="smaller-85 glyphicon glyphicon-trash"></i></button>
								</s:else>
								<button class="btn btn-design-sqlscript btn-default" data-func_guid="<s:property value="#childNode_1.func_guid"/>" data-func_code="<s:property value="#childNode_1.func_code"/>" data-func_name="<s:property value="#childNode_1.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="下载自定义功能升级脚本"><i class="smaller-85 glyphicon glyphicon-cloud-download"></i></button>
							</div>
						</span>
						</s:if>
						<!-- 检测1级菜单下是否有2级菜单 -->
						<s:if test="#childNode_1.childFuncList != null ">
						<ul>
							<!-- 循环2级菜单 -->
							<s:iterator value="#childNode_1.childFuncList" var="childNode_2" status="stat_2">
							<li <s:if test="#stat_1.index != 0">style="display: none;" </s:if> id="<s:property value="#childNode_2.func_code"/>"  >
								<span>
									<s:if test="#childNode_2.childFuncList != null  or #childNode_2.funcBtnList != null">
									<i class="bigger-120 icon-expand-alt"></i>
									</s:if>
									<s:property value="#childNode_2.func_name"/>
									<s:if test="#childNode_2.childFuncList != null ">
								       <span class="badge badge-default"><s:property value="#childNode_2.childFuncList.size()"/></span>
								    </s:if>
								    <s:elseif test="#childNode_2.funcBtnList != null ">
								       <span class="badge badge-default"><s:property value="#childNode_2.funcBtnList.size()"/></span>
								    </s:elseif>
									<s:if test="#childNode_2.func_displayed == 1"><b class="green">【可见】</b></s:if><s:else><b class="light-grey">【不可见】</b></s:else>
								</span>
								<s:if test="func_xgzdycd == 1 && #childNode_2.func_user_defined == 1">
								<!-- 菜单编辑功能按钮 -->
								<span style="border:none;background-color: #FFF !important; ">
									<div class="btn-group btn-group-xs">
										<s:if test="#childNode_2.func_designed == 1">
										<button class="btn btn-design-edit btn-default" data-func_guid="<s:property value="#childNode_2.func_guid"/>" data-func_code="<s:property value="#childNode_2.func_code"/>" data-func_name="<s:property value="#childNode_2.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="编辑自定义功能"><i class="smaller-85 glyphicon glyphicon-edit"></i></button>
										<button class="btn btn-design-globe btn-default" data-func_guid="<s:property value="#childNode_2.func_guid"/>" data-func_code="<s:property value="#childNode_2.func_code"/>" data-func_name="<s:property value="#childNode_2.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="预览自定义功能页面"><i class="smaller-85 glyphicon glyphicon-globe"></i></button>
										<button class="btn btn-design-repeat btn-default" data-func_guid="<s:property value="#childNode_2.func_guid"/>" data-func_code="<s:property value="#childNode_2.func_code"/>" data-func_name="<s:property value="#childNode_2.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="重置自定义功能页面"><i class="smaller-85 glyphicon glyphicon-repeat"></i></button>
										<button class="btn btn-design-remove btn-default" data-func_guid="<s:property value="#childNode_2.func_guid"/>" data-func_code="<s:property value="#childNode_2.func_code"/>" data-func_name="<s:property value="#childNode_2.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="删除自定义功能"><i class="smaller-85 glyphicon glyphicon-trash"></i></button>
										</s:if>
										<s:else>
										<button class="btn btn-define-edit btn-default" data-func_guid="<s:property value="#childNode_2.func_guid"/>" data-func_code="<s:property value="#childNode_2.func_code"/>" data-func_name="<s:property value="#childNode_2.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="编辑功能菜单"><i class="smaller-85 glyphicon glyphicon-edit"></i></button>
										<button class="btn btn-define-remove btn-default" data-func_guid="<s:property value="#childNode_2.func_guid"/>" data-func_code="<s:property value="#childNode_2.func_code"/>" data-func_name="<s:property value="#childNode_2.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="删除功能菜单"><i class="smaller-85 glyphicon glyphicon-trash"></i></button>
										</s:else>
										<button class="btn btn-design-sqlscript btn-default" data-func_guid="<s:property value="#childNode_2.func_guid"/>" data-func_code="<s:property value="#childNode_2.func_code"/>" data-func_name="<s:property value="#childNode_2.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="下载自定义功能升级脚本"><i class="smaller-85 glyphicon glyphicon-cloud-download"></i></button>
									</div>
								</span>
								</s:if>
								<!-- 检测2级菜单下是否有3级菜单 -->
								<s:if test="#childNode_2.childFuncList != null ">
								<ul>
									<!-- 循环3级菜单 -->
									<s:iterator value="#childNode_2.childFuncList" var="childNode_3" status="stat_3">
									<li style="display: none;"  id="<s:property value="#childNode_3.func_code"/>" >
										<span>
											<s:if test="#childNode_3.childFuncList != null  or #childNode_3.funcBtnList != null">
											<i class="bigger-120 icon-collapse-alt"></i>
											</s:if>
											<s:property value="#childNode_3.func_name"/>
											<s:if test="#childNode_3.childFuncList != null ">
										       <span class="badge badge-default"><s:property value="#childNode_3.childFuncList.size()"/></span>
										    </s:if>
										    <s:elseif test="#childNode_3.funcBtnList != null ">
										       <span class="badge badge-default"><s:property value="#childNode_3.funcBtnList.size()"/></span>
										    </s:elseif>
											<s:if test="#childNode_3.func_displayed == 1"><b class="green">【可见】</b></s:if><s:else><b class="light-grey">【不可见】</b></s:else>
										</span>
										<s:if test="func_xgzdycd == 1 && #childNode_3.func_user_defined == 1">
										<!-- 菜单编辑功能按钮 -->
										<span style="border:none;background-color: #FFF !important; ">
											<div class="btn-group btn-group-xs">
												<s:if test="#childNode_3.func_designed == 1 ">
												<button class="btn btn-design-edit btn-default" data-func_guid="<s:property value="#childNode_3.func_guid"/>" data-func_code="<s:property value="#childNode_3.func_code"/>" data-func_name="<s:property value="#childNode_3.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="编辑自定义功能"><i class="smaller-85 glyphicon glyphicon-edit"></i></button>
												<button class="btn btn-design-globe btn-default" data-func_guid="<s:property value="#childNode_3.func_guid"/>" data-func_code="<s:property value="#childNode_3.func_code"/>" data-func_name="<s:property value="#childNode_3.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="预览自定义功能页面"><i class="smaller-85 glyphicon glyphicon-globe"></i></button>
												<button class="btn btn-design-repeat btn-default" data-func_guid="<s:property value="#childNode_3.func_guid"/>" data-func_code="<s:property value="#childNode_3.func_code"/>" data-func_name="<s:property value="#childNode_3.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="重置自定义功能页面"><i class="smaller-85 glyphicon glyphicon-repeat"></i></button>
												<button class="btn btn-design-remove btn-default" data-func_guid="<s:property value="#childNode_3.func_guid"/>" data-func_code="<s:property value="#childNode_3.func_code"/>" data-func_name="<s:property value="#childNode_3.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="删除自定义功能"><i class="smaller-85 glyphicon glyphicon-trash"></i></button>
												</s:if>
												<s:else>
												<button class="btn btn-define-edit btn-default" data-func_guid="<s:property value="#childNode_3.func_guid"/>" data-func_code="<s:property value="#childNode_3.func_code"/>" data-func_name="<s:property value="#childNode_3.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="编辑功能菜单"><i class="smaller-85 glyphicon glyphicon-edit"></i></button>
												<button class="btn btn-define-remove btn-default" data-func_guid="<s:property value="#childNode_3.func_guid"/>" data-func_code="<s:property value="#childNode_3.func_code"/>" data-func_name="<s:property value="#childNode_3.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="删除功能菜单"><i class="smaller-85 glyphicon glyphicon-trash"></i></button>
												</s:else>
												<button class="btn btn-design-sqlscript btn-default" data-func_guid="<s:property value="#childNode_3.func_guid"/>" data-func_code="<s:property value="#childNode_3.func_code"/>" data-func_name="<s:property value="#childNode_3.func_name"/>" data-opt_code="cx" type="button"  data-placement="right" title="下载自定义功能升级脚本"><i class="smaller-85 glyphicon glyphicon-cloud-download"></i></button>
											</div>
										</span>
										</s:if>
										<!-- 检测3级菜单下是否有4级菜单 -->
										<s:if test="#childNode_3.childFuncList != null ">
										<ul>
											<!-- 循环4级菜单 -->
											<s:iterator value="#childNode_3.childFuncList" var="childNode_4" status="stat_4">
											<li style="display: none;"  id="<s:property value="#childNode_4.func_code"/>" >
												<span>
													<s:if test="#childNode_4.childFuncList != null  or #childNode_4.funcBtnList != null">
													<i class="bigger-120 icon-collapse-alt"></i>
													</s:if>
													<s:property value="#childNode_4.func_name"/>
													<s:if test="#childNode_4.childFuncList != null ">
												       <span class="badge badge-default"><s:property value="#childNode_4.childFuncList.size()"/></span>
												    </s:if>
												    <s:elseif test="#childNode_4.funcBtnList != null ">
												       <span class="badge badge-default"><s:property value="#childNode_4.funcBtnList.size()"/></span>
												    </s:elseif>
													<s:if test="#childNode_4.func_displayed == 1"><b class="green">【可见】</b></s:if><s:else><b class="light-grey">【不可见】</b></s:else>
												</span>
												<!-- 检测4级菜单下是否有5级菜单 -->
												<s:if test="#childNode_4.childFuncList != null ">
													<!-- 目前系统没有4级菜单 -->
												</s:if>
												<s:else>
													<ul>
													<s:if test="#childNode_4.funcBtnList != null ">
													<!-- 检测4级菜单下是否直接有绑定功能操作按钮 -->
														<!-- 循环4级功能按钮 -->
														<s:iterator value="#childNode_4.funcBtnList" var="btnNode_4">
															<li style="display: none;"  id="<s:property value="#btnNode_4.opt_code"/>" >
																<span>
																<i class="bigger-100 <s:property value="#btnNode_4.btn_icon"/>"></i> <s:property value="#btnNode_4.btn_text"/>
																</span>
																<s:if test="func_xgzdyan == 1 && #btnNode_4.btn_user_defined == 1 && #btnNode_4.opt_code != 'cx' ">
																<!-- 按钮编辑 -->
																<span style="border:none;background-color: #FFF !important; ">
																	<div class="btn-group btn-group-xs">
																		<button class="btn btn-design-edit btn-default" data-func_guid="<s:property value="#btnNode_4.func_guid"/>" data-func_code="<s:property value="#childNode_4.func_code"/>" data-func_name="<s:property value="#childNode_4.func_name"/>" data-opt_code="<s:property value="#btnNode_4.opt_code"/>" data-btn_text="<s:property value="#btnNode_4.btn_text"/>" type="button"  data-placement="right" title="编辑功能按钮"><i class="smaller-85 glyphicon glyphicon-edit"></i></button>
																		<button class="btn btn-design-bind btn-default" data-func_guid="<s:property value="#btnNode_4.func_guid"/>" data-func_code="<s:property value="#childNode_4.func_code"/>" data-func_name="<s:property value="#childNode_4.func_name"/>" data-opt_code="<s:property value="#btnNode_4.opt_code"/>" data-btn_text="<s:property value="#btnNode_4.btn_text"/>" type="button"  data-placement="right" title="绑定自定义功能"><i class="smaller-85 fa fa-link"></i></button>
																		<s:if test="#btnNode_4.func_guid != null">
																		<button class="btn btn-design-unbind btn-default" data-func_guid="<s:property value="#btnNode_4.func_guid"/>" data-func_code="<s:property value="#childNode_4.func_code"/>" data-func_name="<s:property value="#childNode_4.func_name"/>" data-opt_code="<s:property value="#btnNode_4.opt_code"/>" data-btn_text="<s:property value="#btnNode_4.btn_text"/>" type="button"  data-placement="right" title="解绑自定义功能"><i class="smaller-85 fa fa-unlink"></i></button>
																		<button class="btn btn-design-repeat btn-default" data-func_guid="<s:property value="#btnNode_4.func_guid"/>" data-func_code="<s:property value="#childNode_4.func_code"/>" data-func_name="<s:property value="#childNode_4.func_name"/>" data-opt_code="<s:property value="#btnNode_4.opt_code"/>" data-btn_text="<s:property value="#btnNode_4.btn_text"/>" type="button"  data-placement="right" title="重置自定义功能页面"><i class="smaller-85 glyphicon glyphicon-repeat"></i></button>
																		</s:if>
																		<button class="btn btn-design-remove btn-default" data-func_guid="<s:property value="#btnNode_4.func_guid"/>" data-func_code="<s:property value="#childNode_4.func_code"/>" data-func_name="<s:property value="#childNode_4.func_name"/>" data-opt_code="<s:property value="#btnNode_4.opt_code"/>" data-btn_text="<s:property value="#btnNode_4.btn_text"/>" type="button"  data-placement="right" title="删除自定义功能"><i class="smaller-85 glyphicon glyphicon-trash"></i></button>
																	</div>
																</span>
																</s:if>
															</li>
														</s:iterator>
													</s:if>
													<s:if test="func_zjzdyan == 1">
													<s:if test="#childNode_4.func_readonly == 1 && #childNode_4.func_designed != 1 "> 
													<li style="display: none;">
														<span data-parent_code="<s:property value="#childNode_4.func_code"/>" 
															  data-parent_name="<s:property value="#childNode_4.func_name"/>" 
															  data-func_level="<s:property value="#childNode_4.func_level"/>" 
															  data-max_ordinal="<s:property value="#childNode_4.max_ordinal"/>" 
															  data-opt_code="cx" class="btn-plus btn-define-plus">
														<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加子菜单
														</span>
													</li>
													<li style="display: none;" >
														<span data-parent_code="<s:property value="#childNode_4.func_code"/>" 
															  data-parent_name="<s:property value="#childNode_4.func_name"/>" 
															  data-func_level="<s:property value="#childNode_4.func_level"/>" 
															  data-max_ordinal="<s:property value="#childNode_4.max_ordinal"/>" 
															  data-opt_code="cx" class="btn-plus btn-design-plus">
														<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加自定义功能
														</span>
													</li>
													</s:if>
													<li style="display: none;">
														<span data-parent_code="<s:property value="#childNode_4.func_code"/>" 
															  data-parent_name="<s:property value="#childNode_4.func_name"/>" 
															  data-func_level="<s:property value="#childNode_4.func_level"/>" 
															  data-max_ordinal="<s:property value="#childNode_4.max_ordinal"/>" 
															  data-opt_code="cx" class="btn-plus btn-opt-plus">
														<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加功能按钮
														</span>
													</li>
													</s:if>
													</ul>
												</s:else>
											</li>
											</s:iterator>
											<s:if test="func_zjzdycd == 1">
											<s:if test="#childNode_3.func_readonly == 1 && #childNode_3.func_designed != 1 ">
											<li style="display: none;">
												<span data-parent_code="<s:property value="#childNode_3.func_code"/>" 
													  data-parent_name="<s:property value="#childNode_3.func_name"/>" 
													  data-func_level="<s:property value="#childNode_3.func_level"/>" 
													  data-max_ordinal="<s:property value="#childNode_3.max_ordinal"/>" 
													  data-opt_code="cx" class="btn-plus btn-define-plus">
												<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加子菜单
												</span>
											</li>
											</s:if>
											<li style="display: none;">
												<span data-parent_code="<s:property value="#childNode_3.func_code"/>" 
													  data-parent_name="<s:property value="#childNode_3.func_name"/>" 
													  data-func_level="<s:property value="#childNode_3.func_level"/>" 
													  data-max_ordinal="<s:property value="#childNode_3.max_ordinal"/>" 
													  data-opt_code="cx" class="btn-plus btn-design-plus">
												<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加自定义功能
												</span>
											</li>
											</s:if>
										</ul>
										</s:if>
										<s:else>
											<ul>
											<s:if test="#childNode_3.funcBtnList != null ">
												<!-- 检测3级菜单下是否直接有绑定功能操作按钮 -->
												<!-- 循环3级功能按钮 -->
												<s:iterator value="#childNode_3.funcBtnList" var="btnNode_3">
													<li style="display: none;"  id="<s:property value="#btnNode_3.opt_code"/>" >
														<span>
														<i class="bigger-100 <s:property value="#btnNode_3.btn_icon"/>"></i> <s:property value="#btnNode_3.btn_text"/>
														</span>
														<s:if test="func_xgzdyan == 1  && #btnNode_3.btn_user_defined == 1 && #btnNode_3.opt_code != 'cx'">
														<!-- 按钮编辑 -->
														<span style="border:none;background-color: #FFF !important; ">
															<button class="btn btn-design-edit btn-default" data-func_guid="<s:property value="#btnNode_3.func_guid"/>" data-func_name="<s:property value="#childNode_3.func_name"/>" data-func_code="<s:property value="#childNode_3.func_code"/>" data-opt_code="<s:property value="#btnNode_3.opt_code"/>" data-btn_text="<s:property value="#btnNode_3.btn_text"/>" type="button"  data-placement="right" title="编辑功能按钮"><i class="smaller-85 glyphicon glyphicon-edit"></i></button>
															<button class="btn btn-design-bind btn-default" data-func_guid="<s:property value="#btnNode_3.func_guid"/>" data-func_name="<s:property value="#childNode_3.func_name"/>" data-func_code="<s:property value="#childNode_3.func_code"/>" data-opt_code="<s:property value="#btnNode_3.opt_code"/>" data-btn_text="<s:property value="#btnNode_3.btn_text"/>" type="button"  data-placement="right" title="绑定自定义功能"><i class="smaller-85 fa fa-link"></i></button>
															<s:if test="#btnNode_3.func_guid != null">
															<button class="btn btn-design-unbind btn-default" data-func_guid="<s:property value="#btnNode_3.func_guid"/>" data-func_name="<s:property value="#childNode_3.func_name"/>" data-func_code="<s:property value="#childNode_3.func_code"/>" data-opt_code="<s:property value="#btnNode_3.opt_code"/>" data-btn_text="<s:property value="#btnNode_3.btn_text"/>" type="button"  data-placement="right" title="解绑自定义功能"><i class="smaller-85 fa fa-unlink"></i></button>
															<button class="btn btn-design-repeat btn-default" data-func_guid="<s:property value="#btnNode_3.func_guid"/>" data-func_name="<s:property value="#childNode_3.func_name"/>" data-func_code="<s:property value="#childNode_3.func_code"/>" data-opt_code="<s:property value="#btnNode_3.opt_code"/>" data-btn_text="<s:property value="#btnNode_3.btn_text"/>" type="button"  data-placement="right" title="重置自定义功能页面"><i class="smaller-85 glyphicon glyphicon-repeat"></i></button>
															</s:if>
															<button class="btn btn-design-remove btn-default" data-func_guid="<s:property value="#btnNode_3.func_guid"/>" data-func_name="<s:property value="#childNode_3.func_name"/>" data-func_code="<s:property value="#childNode_3.func_code"/>" data-opt_code="<s:property value="#btnNode_3.opt_code"/>" data-btn_text="<s:property value="#btnNode_3.btn_text"/>" type="button"  data-placement="right" title="删除自定义功能"><i class="smaller-85 glyphicon glyphicon-trash"></i></button>
														</span>
														</s:if>
													</li>
												</s:iterator>
											</s:if>
											<s:if test="func_zjzdyan == 1">
											<s:if test="#childNode_3.func_readonly == 1 && #childNode_3.func_designed != 1 ">
											<li style="display: none;" >
												<span data-parent_code="<s:property value="#childNode_3.func_code"/>" 
													  data-parent_name="<s:property value="#childNode_3.func_name"/>" 
													  data-func_level="<s:property value="#childNode_3.func_level"/>" 
													  data-max_ordinal="<s:property value="#childNode_3.max_ordinal"/>" 
													  data-opt_code="cx" class="btn-plus btn-define-plus">
												<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加子菜单
												</span>
											</li>
											<li style="display: none;" >
												<span data-parent_code="<s:property value="#childNode_3.func_code"/>" 
													  data-parent_name="<s:property value="#childNode_3.func_name"/>" 
													  data-func_level="<s:property value="#childNode_3.func_level"/>" 
													  data-max_ordinal="<s:property value="#childNode_3.max_ordinal"/>" 
													  data-opt_code="cx" class="btn-plus btn-design-plus">
												<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加自定义功能
												</span>
											</li>
											</s:if>
											<li style="display: none;" >
												<span data-parent_code="<s:property value="#childNode_3.func_code"/>" 
													  data-parent_name="<s:property value="#childNode_3.func_name"/>" 
													  data-func_level="<s:property value="#childNode_3.func_level"/>" 
													  data-max_ordinal="<s:property value="#childNode_3.max_ordinal"/>" 
													  data-opt_code="cx" class="btn-plus btn-opt-plus">
												<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加功能按钮
												</span>
											</li>
											</s:if>
											</ul>
										</s:else>
									</li>
									</s:iterator>
									<s:if test="func_zjzdycd == 1">
									<s:if test="#childNode_2.func_readonly == 1 && #childNode_2.func_designed != 1 ">
									<li style="display: none;" >
										<span data-parent_code="<s:property value="#childNode_2.func_code"/>" 
											  data-parent_name="<s:property value="#childNode_2.func_name"/>" 
											  data-func_level="<s:property value="#childNode_2.func_level"/>" 
											  data-max_ordinal="<s:property value="#childNode_2.max_ordinal"/>" 
											  data-opt_code="cx" class="btn-plus btn-define-plus">
										<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加子菜单
										</span>
									</li>
									</s:if>
									<li style="display: none;" >
										<span data-parent_code="<s:property value="#childNode_2.func_code"/>" 
											  data-parent_name="<s:property value="#childNode_2.func_name"/>" 
											  data-func_level="<s:property value="#childNode_2.func_level"/>" 
											  data-max_ordinal="<s:property value="#childNode_2.max_ordinal"/>" 
											  data-opt_code="cx" class="btn-plus btn-design-plus">
										<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加自定义功能
										</span>
									</li>
									</s:if>
								</ul>
								</s:if>
								<s:else>
									<ul>
									<s:if test="#childNode_2.funcBtnList != null ">
										<!-- 检测2级菜单下是否直接有绑定功能操作按钮 -->
										<!-- 循环2级功能按钮 -->
										<s:iterator value="#childNode_2.funcBtnList" var="btnNode_2">
											<li style="display: none;" id="<s:property value="#btnNode_2.opt_code"/>" >
												<span>
												<i class="bigger-100 <s:property value="#btnNode_2.btn_icon"/>"></i> <s:property value="#btnNode_2.btn_text"/>
												</span>
												<s:if test="func_xgzdyan == 1  && #btnNode_2.btn_user_defined == 1 && #btnNode_2.opt_code != 'cx'">
												<!-- 按钮编辑 -->
												<span style="border:none;background-color: #FFF !important; ">
													<div class="btn-group btn-group-xs">
														<button class="btn btn-design-edit btn-default" data-func_guid="<s:property value="#btnNode_2.func_guid"/>" data-func_code="<s:property value="#childNode_2.func_code"/>" data-func_name="<s:property value="#childNode_2.func_name"/>" data-opt_code="<s:property value="#btnNode_2.opt_code"/>" data-btn_text="<s:property value="#btnNode_2.btn_text"/>" type="button"  data-placement="right" title="编辑功能按钮"><i class="smaller-85 glyphicon glyphicon-edit"></i></button>
														<button class="btn btn-design-bind btn-default" data-func_guid="<s:property value="#btnNode_2.func_guid"/>" data-func_code="<s:property value="#childNode_2.func_code"/>" data-func_name="<s:property value="#childNode_2.func_name"/>" data-opt_code="<s:property value="#btnNode_2.opt_code"/>" data-btn_text="<s:property value="#btnNode_2.btn_text"/>" type="button"  data-placement="right" title="绑定自定义功能"><i class="smaller-85 fa fa-link"></i></button>
														<s:if test="#btnNode_2.func_guid != null">
														<button class="btn btn-design-unbind btn-default" data-func_guid="<s:property value="#btnNode_2.func_guid"/>" data-func_code="<s:property value="#childNode_2.func_code"/>" data-func_name="<s:property value="#childNode_2.func_name"/>" data-opt_code="<s:property value="#btnNode_2.opt_code"/>" data-btn_text="<s:property value="#btnNode_2.btn_text"/>" type="button"  data-placement="right" title="解绑自定义功能"><i class="smaller-85 fa fa-unlink"></i></button>
														<button class="btn btn-design-repeat btn-default" data-func_guid="<s:property value="#btnNode_2.func_guid"/>" data-func_code="<s:property value="#childNode_2.func_code"/>" data-func_name="<s:property value="#childNode_2.func_name"/>" data-opt_code="<s:property value="#btnNode_2.opt_code"/>" data-btn_text="<s:property value="#btnNode_2.btn_text"/>" type="button"  data-placement="right" title="重置自定义功能页面"><i class="smaller-85 glyphicon glyphicon-repeat"></i></button>
														</s:if>
														<button class="btn btn-design-remove btn-default" data-func_guid="<s:property value="#btnNode_2.func_guid"/>" data-func_code="<s:property value="#childNode_2.func_code"/>" data-func_name="<s:property value="#childNode_2.func_name"/>" data-opt_code="<s:property value="#btnNode_2.opt_code"/>" data-btn_text="<s:property value="#btnNode_2.btn_text"/>" type="button"  data-placement="right" title="删除自定义功能"><i class="smaller-85 glyphicon glyphicon-trash"></i></button>
													</div>
												</span>
												</s:if>
											</li>
										</s:iterator>
									</s:if>
									<s:if test="func_zjzdyan == 1">
									<s:if test="#childNode_2.func_readonly == 1 && #childNode_2.func_designed != 1 ">
									<li style="display: none;" >
										<span data-parent_code="<s:property value="#childNode_2.func_code"/>" 
										  	  data-parent_name="<s:property value="#childNode_2.func_name"/>" 
										  	  data-func_level="<s:property value="#childNode_2.func_level"/>" 
										  	  data-max_ordinal="<s:property value="#childNode_2.max_ordinal"/>" 
										  	  data-opt_code="cx" class="btn-plus btn-define-plus">
										<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加子菜单
										</span>
									</li>
									<li style="display: none;" >
										<span data-parent_code="<s:property value="#childNode_2.func_code"/>" 
											  data-parent_name="<s:property value="#childNode_2.func_name"/>" 
											  data-func_level="<s:property value="#childNode_2.func_level"/>" 
											  data-max_ordinal="<s:property value="#childNode_2.max_ordinal"/>" 
											  data-opt_code="cx" class="btn-plus btn-design-plus">
										<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加自定义功能
										</span>
									</li>
									</s:if>
									<li style="display: none;" >
										<span data-parent_code="<s:property value="#childNode_2.func_code"/>" 
										  	  data-parent_name="<s:property value="#childNode_2.func_name"/>" 
										  	  data-func_level="<s:property value="#childNode_2.func_level"/>" 
										  	  data-max_ordinal="<s:property value="#childNode_2.max_ordinal"/>" 
										  	  data-opt_code="cx" class="btn-plus btn-opt-plus">
										<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加功能按钮
										</span>
									</li>
									</s:if>
									</ul>
								</s:else>
							</li>
							</s:iterator>
							<s:if test="func_zjzdycd == 1">
							<s:if test="#childNode_1.func_readonly == 1 && #childNode_1.func_designed != 1 ">
							<li style="display: none;"  >
								<span data-parent_code="<s:property value="#childNode_1.func_code"/>" 
									  data-parent_name="<s:property value="#childNode_1.func_name"/>" 
									  data-func_level="<s:property value="#childNode_1.func_level"/>" 
									  data-max_ordinal="<s:property value="#childNode_1.max_ordinal"/>" 
									  data-opt_code="cx" class="btn-plus btn-define-plus">
								<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加子菜单
								</span>
							</li>
							</s:if>
							<li style="display: none;"  >
								<span data-parent_code="<s:property value="#childNode_1.func_code"/>" 
									  data-parent_name="<s:property value="#childNode_1.func_name"/>" 
									  data-func_level="<s:property value="#childNode_1.func_level"/>" 
									  data-max_ordinal="<s:property value="#childNode_1.max_ordinal"/>" 
									  data-opt_code="cx" class="btn-plus btn-design-plus">
								<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加自定义功能
								</span>
							</li>
							</s:if>
						</ul>
						</s:if>
						<s:else>
							<ul>
							<s:if test="#childNode_1.funcBtnList != null ">
								<!-- 检测1级菜单下是否直接有绑定功能操作按钮 -->
								<!-- 循环2级功能按钮 -->
								<s:iterator value="#childNode_1.funcBtnList" var="btnNode_1">
									<li style="display: none;" id="<s:property value="#btnNode_1.opt_code"/>" >
										<span>
										<i class="bigger-100 <s:property value="#btnNode_1.btn_icon"/>"></i> <s:property value="#btnNode_1.btn_text"/>
										</span>
										<s:if test="func_xgzdyan == 1  && #btnNode_1.btn_user_defined == 1 && #btnNode_1.opt_code != 'cx'">
										<!-- 按钮编辑 -->
										<span style="border:none;background-color: #FFF !important; ">
											<div class="btn-group btn-group-xs">
												<button class="btn btn-design-edit btn-default" data-func_guid="<s:property value="#btnNode_1.func_guid"/>" data-func_code="<s:property value="#childNode_1.func_code"/>" data-func_name="<s:property value="#childNode_1.func_name"/>" data-opt_code="<s:property value="#btnNode_1.opt_code"/>" data-btn_text="<s:property value="#btnNode_1.btn_text"/>" type="button"  data-placement="right" title="编辑功能按钮"><i class="smaller-85 glyphicon glyphicon-edit"></i></button>
												<button class="btn btn-design-bind btn-default" data-func_guid="<s:property value="#btnNode_1.func_guid"/>" data-func_code="<s:property value="#childNode_1.func_code"/>" data-func_name="<s:property value="#childNode_1.func_name"/>" data-opt_code="<s:property value="#btnNode_1.opt_code"/>" data-btn_text="<s:property value="#btnNode_1.btn_text"/>" type="button"  data-placement="right" title="绑定自定义功能"><i class="smaller-85 fa fa-link"></i></button>
												<s:if test="#btnNode_1.func_guid != null">
												<button class="btn btn-design-unbind btn-default" data-func_guid="<s:property value="#btnNode_1.func_guid"/>" data-func_code="<s:property value="#childNode_1.func_code"/>" data-func_name="<s:property value="#childNode_1.func_name"/>" data-opt_code="<s:property value="#btnNode_1.opt_code"/>" data-btn_text="<s:property value="#btnNode_1.btn_text"/>" type="button"  data-placement="right" title="解绑自定义功能"><i class="smaller-85 fa fa-unlink"></i></button>
												<button class="btn btn-design-repeat btn-default" data-func_guid="<s:property value="#btnNode_1.func_guid"/>" data-func_code="<s:property value="#childNode_1.func_code"/>" data-func_name="<s:property value="#childNode_1.func_name"/>" data-opt_code="<s:property value="#btnNode_1.opt_code"/>" data-btn_text="<s:property value="#btnNode_1.btn_text"/>" type="button"  data-placement="right" title="重置自定义功能页面"><i class="smaller-85 glyphicon glyphicon-repeat"></i></button>
												</s:if>
												<button class="btn btn-design-remove btn-default" data-func_guid="<s:property value="#btnNode_1.func_guid"/>" data-func_code="<s:property value="#childNode_1.func_code"/>" data-func_name="<s:property value="#childNode_1.func_name"/>" data-opt_code="<s:property value="#btnNode_1.opt_code"/>" data-btn_text="<s:property value="#btnNode_1.btn_text"/>" type="button"  data-placement="right" title="删除自定义功能"><i class="smaller-85 glyphicon glyphicon-trash"></i></button>
											</div>
										</span>
										</s:if>
									</li>
								</s:iterator>
							</s:if>
							<s:if test="func_zjzdyan == 1">
							<s:if test="#childNode_1.func_readonly == 1 && #childNode_1.func_designed != 1 ">
							<li style="display: none;">
								<span data-parent_code="<s:property value="#childNode_1.func_code"/>" 
									  data-parent_name="<s:property value="#childNode_1.func_name"/>" 
									  data-func_level="<s:property value="#childNode_1.func_level"/>" 
									  data-max_ordinal="<s:property value="#childNode_1.max_ordinal"/>" 
									  data-opt_code="cx" class="btn-plus btn-define-plus">
								<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加子菜单
								</span>
							</li>
							<li style="display: none;" >
								<span data-parent_code="<s:property value="#childNode_1.func_code"/>" 
									  data-parent_name="<s:property value="#childNode_1.func_name"/>" 
									  data-func_level="<s:property value="#childNode_1.func_level"/>" 
									  data-max_ordinal="<s:property value="#childNode_1.max_ordinal"/>" 
									  data-opt_code="cx" class="btn-plus btn-design-plus">
								<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加自定义功能
								</span>
							</li>
							</s:if>
							<li style="display: none;" >
								<span data-parent_code="<s:property value="#childNode_1.func_code"/>" 
									  data-parent_name="<s:property value="#childNode_1.func_name"/>" 
									  data-func_level="<s:property value="#childNode_1.func_level"/>" 
									  data-max_ordinal="<s:property value="#childNode_1.max_ordinal"/>" 
									  data-opt_code="cx" class="btn-plus btn-opt-plus">
								<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加功能按钮
								</span>
							</li>
							</s:if>
							</ul>
						</s:else>
					</li>
				</s:iterator>
				<s:if test="func_zjzdycd == 1">
				<s:if test="funcModel.func_readonly == 1 && funcModel.func_designed != 1 ">
				<li>
					<span data-parent_code="<s:property value="funcModel.func_code"/>" 
						  data-parent_name="<s:property value="funcModel.func_name"/>" 
						  data-func_level="<s:property value="funcModel.func_level"/>" 
						  data-max_ordinal="<s:property value="funcModel.max_ordinal"/>" 
						  data-opt_code="cx" class="btn-plus btn-define-plus">
					<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加子菜单
					</span>
				</li>
				</s:if>
				<li>
					<span data-parent_code="<s:property value="funcModel.func_code"/>" 
						  data-parent_name="<s:property value="funcModel.func_name"/>" 
						  data-func_level="<s:property value="funcModel.func_level"/>" 
						  data-max_ordinal="<s:property value="funcModel.max_ordinal"/>" 
						  data-opt_code="cx" class="btn-plus btn-design-plus">
					<i class="bigger-100 glyphicon glyphicon-plus-sign"></i> 增加自定义功能
					</span>
				</li>
				</s:if>
			</ul>
			</s:if>
		</li>
	</ul>
</div>
</s:if>
</body>
</html>