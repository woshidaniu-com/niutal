<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>问卷调查——空白模板</title>
		<link type="text/css" rel="stylesheet" href="${messageUtil("system.stylePath")}/assets/fonts/font-awesome-4.2.0_ie7/4.2.0/css/font-awesome-ie7.min.css?ver=${messageUtil("niutal.cssVersion")}" />
		<link type="text/css" rel="stylesheet" href="${messageUtil("system.stylePath")}/assets/css/theme/theme-ocean.min.css?ver=${messageUtil("niutal.cssVersion")}"/>
		<link type="text/css" rel="stylesheet" href="${messageUtil("system.stylePath")}/assets/css/niutal-ui-base.css?ver=${messageUtil("niutal.cssVersion")}"/>
		[#include "/globalweb/head/niutal-ui-validation.ftl" /]
		<link type="text/css" rel="stylesheet" href="${base}/css/wjdc/wjsj.css?ver=${messageUtil("niutal.cssVersion")}" />
		<style type="text/css">
			.float_l {
				float: left;
			}
			
			.float_r {
				float: right;
			}
			
			.blank-module .btn {
				border: none;
				padding: 2px 5px;
				font-size: 12px;
			}
			
			.blank-module .btn i {
				margin-right: 3px;
			}
			
			.left-content {
				width: 200px;
			}
			
			.right-content {
				width: 700px;
				border-left: 1px solid #ddd;
			}
			
			.ui-state-highlight {
				height: 200px;
				border: 2px dashed #ddd;
			}
		</style>
	</head>
	<body>
		<div class="no-response-container">
			<div class="blank-module  edit-module mb  wj clearfix">

				<div class="left-content float_l" style="position: fixed;">
					<div class="fixed-left-block">
						<div class="position-relative">
						
							<div class="title">
								题型选择
							</div>
							<div class="content">
								<div class="item topic-type" id="dxxz" data-issue="dxxz" data-type="1">
									<div class="preview">
										<span class="img-icon"><i class="icon-circle-blank"></i></span>
										<span class="font">单项选择</span>
									</div>
									<div class="wj-setting-title no-sort">
										<span class="wj-question-type"><span class="current-number"></span>、<span class="official-title">这是一道单项选择题，这个是题目内容。</span></span>
										<span class="checkbox">
								    		<label>
								      			<input type="checkbox" checked="" name="sfbd" class="is-mandatory"> 必填题
								    		</label>
							  			</span>
							  			<span class="">每行选项个数：
											<select name="mhxxgs" class="chosen-size" data-value="">
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
												<option value="6">6</option>
												<option value="7">7</option>
												<option value="8">8</option>
											</select>
										</span>
										</br>
										<span class="">
											题目提示内容:<input name="ts" type="text" class="" value="" placeholder=""/>
										</span>
									</div>
									<div class="wj-setting-content">
										<div class="radio">
											<div class="setting-block">
												<label class="no-sort">
								          <input type="radio" value="option1" xxfz="0"><span>选项1</span>
								        </label>
											</div>
										</div>
										<div class="radio">
											<div class="setting-block">
												<label class="no-sort">
								          <input type="radio" value="option1" xxfz="0"><span>选项2</span>
								        </label>
											</div>
										</div>
										<div class="radio">
											<div class="setting-block">
												<label class="no-sort">
								          <input type="radio" value="option1" xxfz="0"><span>选项3</span>
								        </label>
											</div>
										</div>
									</div>
								</div>
								<div class="item topic-type" id="dxzhe" data-issue="dxzhe" data-type="2">
									<div class="preview">
										<span class="img-icon"><i class="icon-circle-blank"></i></span>
										<span class="font">单项组合</span>
									</div>
									<div class="wj-setting-title no-sort">
										<span class="wj-question-type"><span class="current-number"></span>、<span class="official-title">这是一道单项组合题，这个是题目内容。</span></span>
										<span class="checkbox">
								    		<label>
								      			<input type="checkbox" checked="" name="sfbd" class="is-mandatory"> 必填题
								    		</label>
							  			</span>
							  			<span class="">每行选项个数：
											<select name="mhxxgs" class="chosen-size" data-value="">
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
												<option value="6">6</option>
												<option value="7">7</option>
												<option value="8">8</option>
											</select>
										</span>
										</br>
										<span class="">
											题目提示内容:<input name="ts" type="text" class="" value="" placeholder=""/>
										</span>
									</div>
									<div class="wj-setting-content">
										<div class="radio">
											<div class="setting-block">
												<label class="no-sort">
								          <input type="radio" value="option1" xxfz="0"><span>选项1</span>
								        </label>
											</div>
										</div>
										<div class="radio">
											<div class="setting-block">
												<label class="no-sort">
								          <input type="radio" value="option1" xxfz="0"><span>选项2</span>
								        </label>
											</div>
										</div>
										<div class="radio other">
											<div class="setting-block">
												<label class="no-sort">
								          <input type="radio" value="option1" xxfz="0"><span>选项3</span>
								        </label>
												<input type="text" name="" id="" value="" class="no-sort" />
											</div>
										</div>
									</div>
								</div>
								<div class="item topic-type" id="duxxz" data-issue="duxxz" data-type="3">
									<div class="preview">
										<span class="img-icon"><i class="icon-check-empty"></i></span>
										<span class="font">多项选择</span>
									</div>
									<div class="wj-setting-title no-sort">
										<span class="wj-question-type"><span class="current-number"></span>、<span class="official-title">这是一道多项选择题，这个是题目内容。</span></span>
										<span class="checkbox">
								    		<label>
								      			<input type="checkbox" checked="" name="sfbd" class="is-mandatory"> 必填题
								    		</label>
							  			</span>
							  			<span class="">每行选项个数：
											<select name="mhxxgs" class="chosen-size" data-value="">
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
												<option value="6">6</option>
												<option value="7">7</option>
												<option value="8">8</option>
											</select>
										</span>
										</br>
										<span class="">最多可选个数：
											<select name="kxgs" id="" class="chosen-size" onchange="onchange_kxgs(this)">
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3" selected="selected">3</option>
											</select>
										</span>
										<span class="">是否可排序：
											<select name="sfyxpx" id="" class="chosen-size" data-value="0" onchange="onchange_sfyxpx(this)">
												<option value="0">否</option>
												<option value="1">是</option>
											</select>
										</span>
										<span class="">
											题目提示内容:<input name="ts" type="text" class="" value="" placeholder=""/>
										</span>
									</div>
									<div class="wj-setting-content">
										<div class="checkbox">
											<div class="setting-block">
												<label class="no-sort">
								          <input type="checkbox" value="option1" xxfz="0"><span>选项1</span>
								        </label>
											</div>
										</div>
										<div class="checkbox">
											<div class="setting-block">
												<label class="no-sort">
								          <input type="checkbox" value="option1" xxfz="0"><span>选项2</span>
								        </label>
											</div>
										</div>
										<div class="checkbox">
											<div class="setting-block">
												<label class="no-sort">
								          <input type="checkbox" value="option1" xxfz="0"><span>选项3</span>
								        </label>
											</div>
										</div>
									</div>
								</div>
								<div class="item topic-type" id="duxzh" data-issue="duxzh" data-type="4">
									<div class="preview"><span class="img-icon"><i class="icon-check-empty"></i></span>
										<span class="font">多项组合</span></div>
									<div class="wj-setting-title no-sort">
										<span class="wj-question-type"><span class="current-number"></span>、<span class="official-title">这是一道多项组合题，这个是题目内容。</span></span>
										<span class="checkbox">
								    		<label>
								      			<input type="checkbox" checked="" name="sfbd" class="is-mandatory"> 必填题
								    		</label>
							  			</span>
							  			<span class="">每行选项个数：
											<select name="mhxxgs" class="chosen-size" data-value="">
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
												<option value="6">6</option>
												<option value="7">7</option>
												<option value="8">8</option>
											</select>
										</span>
										</br>
										<span class="">最多可选个数：
											<select name="kxgs" class="chosen-size">
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3" selected="selected">3</option>
											</select>
										</span>
										<span class="">
											题目提示内容:<input name="ts" type="text" class="" value="" placeholder=""/>
										</span>
									</div>
									<div class="wj-setting-content">
										<div class="checkbox">
											<div class="setting-block">
												<label class="no-sort">
								          <input type="checkbox" value="option1" xxfz="0"><span>选项1</span>
								        </label>
											</div>
										</div>
										<div class="checkbox">
											<div class="setting-block">
												<label class="no-sort">
								          <input type="checkbox" value="option1" xxfz="0"><span>选项2</span>
								        </label>
											</div>
										</div>
										<div class="checkbox other">
											<div class="setting-block">
												<label class="no-sort">
								          <input type="checkbox" value="option1" xxfz="0"><span>选项3</span>
								        </label>
												<input type="text" name="" id="" value="" class="no-sort" />
											</div>
										</div>
									</div>
								</div>
								<div class="item topic-type" id="wbt" data-issue="wbt" data-type="5" >
									<div class="preview"><span class="img-icon"><i class="icon-file-alt"></i></span>
										<span class="font">文本题</span></div>
									<div class="wj-setting-title no-sort">
										<span class="wj-question-type"><span class="current-number"></span>、<span class="official-title">这是一道文本题，这个是题目内容。</span></span>
										<span class="checkbox">
								    		<label>
								      			<input type="checkbox" checked="" name="sfbd" class="is-mandatory"> 必填题
								    		</label>
							  			</span>
										<span class="">最大字数：
											<input type="text" name="zdzs" value="100" data-rules='{"required":true,"range":[1,500],"digits":true}'/>
										</span>
										<span class="">文本框高度：
											<select name="wbgd" class="wbms_select">
												<option value="1" selected="selected">一行</option>
												<option value="2">两行</option>
												<option value="3">三行</option>
												<option value="4">四行</option>
												<option value="5">五行</option>
												<option value="6">六行</option>
											</select>
										</span>
										</br>
										<span class="">文本类型：
											<select name="wblx" class="chosen-size" data-value="">
												<option value="1" >文本</option>
												<option value="2" >数字</option>
											</select>
										</span>
										<span class="">
												题目提示内容:<input name="ts" type="text" class="" value="" placeholder="" style="max-width:160px;"/>
										</span>
									</div>
									<div class="wj-setting-content">
										<textarea name="tkly" cols="" rows="1" class="form-control no-sort" style="height:40px;" placeholder="请输入..."></textarea>
									</div>
								</div>
								<div class="item topic-type" id="mssm" data-issue="mssm" data-type="0">
									<div class="preview"><span class="img-icon"><i class="icon-edit"></i></span>
									<span class="font">描述说明</span></div>
									<div class="wj-setting-title no-sort">
										<span class="">对齐方式：
											<select name="dqfs" class="chosen-size" data-value="">
												<option value="center" selected="selected">居中</option>
												<option value="left">居左</option>
												<option value="right">居右</option>
											</select>
										</span>
									</div>
									<div class="wj-setting-content">
										<textarea name="tkly" cols="" rows="4" class="form-control no-sort" data-rules='{"required":true,"rangelength":[1,200]}'></textarea>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="right-content float_l" style="margin-left:200px;">
					<div class="wj-content-area">
						<div class="wj-content-title no-sort">
							<p class="text-center wj-blue-h3">问卷标题</p>
						</div>
						<!--问卷内容-->
						<!--表单-->
						<form class="form-horizontal" role="form" data-toggle="validation" action="saveWjst.zf" id="ajaxForm" method="post">
							<input type="hidden" name="wjid" value="${model.wjid}" id="wjid"/>
							<input type="hidden" name="wjst" id="wjst"/>
						<div class="wj-content">
						[#list stxxList as stxx]
							[#if stxx.stlx==1]
							<!-- 单项选择题 -->
							<div class="item topic-type" data-type="${stxx.stlx}" data-issue="dxxz">
								<div class="wj-setting-title no-sort">
									<span class="wj-question-type"><span class="current-number">${stxx.xssx}</span>、<span class="official-title">${stxx.stmc}</span></span>
									<span class="checkbox">
								    	<label>
								      		<input type="checkbox" [#if stxx.sfbd == 'true']checked="checked"[/#if] name="sfbd" class="is-mandatory"> 必填题
								    	</label>
								  	</span>
								  	<span class="">每行选项个数：
											<select name="mhxxgs" class="chosen-size" data-value="${stxx.mhxxgs}">
												<option value="1" [#if stxx.mhxxgs == '1']selected="selected"[/#if]>1</option>
												<option value="2" [#if stxx.mhxxgs == '2']selected="selected"[/#if]>2</option>
												<option value="3" [#if stxx.mhxxgs == '3']selected="selected"[/#if]>3</option>
												<option value="4" [#if stxx.mhxxgs == '4']selected="selected"[/#if]>4</option>
												<option value="5" [#if stxx.mhxxgs == '5']selected="selected"[/#if]>5</option>
												<option value="6" [#if stxx.mhxxgs == '6']selected="selected"[/#if]>6</option>
												<option value="7" [#if stxx.mhxxgs == '7']selected="selected"[/#if]>7</option>
												<option value="8" [#if stxx.mhxxgs == '8']selected="selected"[/#if]>8</option>
											</select>
									</span>
									</br>
									<span class="">
										题目提示内容:<input name="ts" type="text" class="" value="${stxx.ts}" placeholder=""/>
									</span>
								</div>
								<div class="wj-setting-content">
									[#list xxxxList as item]
										[#if item.stid == stxx.stid]
										<div class="radio">
											<div class="setting-block">
												<label class="no-sort">
									            	<input type="radio" value="option${item_index}" xxfz="${item.xxfz}" tzstid=${item.tzstid}><span>${item.xxmc}</span>
									 	        </label>
											</div>
										</div>	
										[/#if]									
									[/#list]
								</div>
							</div>
							[#elseif stxx.stlx==2]
							<!-- 单选组合题 -->
							<div class="item topic-type" data-type="${stxx.stlx}" data-issue="dxzhe">
									<div class="wj-setting-title no-sort">
										<span class="wj-question-type"><span class="current-number">${stxx.xssx}</span>、<span class="official-title">${stxx.stmc}</span></span>
										<span class="checkbox">
								    		<label>
								      			<input type="checkbox" [#if stxx.sfbd == 'true']checked="checked"[/#if] name="sfbd" class="is-mandatory"> 必填题
								    		</label>
							  			</span>
							  			<span class="">每行选项个数：
											<select name="mhxxgs" class="chosen-size" data-value="${stxx.mhxxgs}">
												<option value="1" [#if stxx.mhxxgs == '1']selected="selected"[/#if]>1</option>
												<option value="2" [#if stxx.mhxxgs == '2']selected="selected"[/#if]>2</option>
												<option value="3" [#if stxx.mhxxgs == '3']selected="selected"[/#if]>3</option>
												<option value="4" [#if stxx.mhxxgs == '4']selected="selected"[/#if]>4</option>
												<option value="5" [#if stxx.mhxxgs == '5']selected="selected"[/#if]>5</option>
												<option value="6" [#if stxx.mhxxgs == '6']selected="selected"[/#if]>6</option>
												<option value="7" [#if stxx.mhxxgs == '7']selected="selected"[/#if]>7</option>
												<option value="8" [#if stxx.mhxxgs == '8']selected="selected"[/#if]>8</option>
											</select>
										</span>
										</br>
										<span class="">
											题目提示内容:<input name="ts" type="text" class="" value="${stxx.ts}" placeholder=""/>
										</span>
									</div>
									<div class="wj-setting-content">
										[#list xxxxList as item]
											[#if item.stid == stxx.stid]
											<div class="radio">
												<div class="setting-block">
													<label class="no-sort">
										        		<input type="radio" value="option${item_index}" xxfz="${item.xxfz}" tzstid=${item.tzstid}><span>${item.xxmc}</span>
									        		</label>
												</div>
											</div>
											[/#if]
										[/#list]
									</div>
							</div>		
							[#elseif stxx.stlx==3]
							<!-- 多选题 -->
							<div class="item topic-type" data-type="${stxx.stlx}" data-issue="duxxz">
									<div class="wj-setting-title no-sort">
										<span class="wj-question-type"><span class="current-number">${stxx.xssx}</span>、<span class="official-title">${stxx.stmc}</span></span>
										<span class="checkbox">
									    	<label>
								    	  	<input type="checkbox" [#if stxx.sfbd == 'true']checked="checked"[/#if] name="sfbd" class="is-mandatory"> 必填题
								    		</label>
							  			</span>
										<span class="">每行选项个数：
											<select name="mhxxgs" class="chosen-size" data-value="${stxx.mhxxgs}">
												<option value="1" [#if stxx.mhxxgs == '1']selected="selected"[/#if]>1</option>
												<option value="2" [#if stxx.mhxxgs == '2']selected="selected"[/#if]>2</option>
												<option value="3" [#if stxx.mhxxgs == '3']selected="selected"[/#if]>3</option>
												<option value="4" [#if stxx.mhxxgs == '4']selected="selected"[/#if]>4</option>
												<option value="5" [#if stxx.mhxxgs == '5']selected="selected"[/#if]>5</option>
												<option value="6" [#if stxx.mhxxgs == '6']selected="selected"[/#if]>6</option>
												<option value="7" [#if stxx.mhxxgs == '7']selected="selected"[/#if]>7</option>
												<option value="8" [#if stxx.mhxxgs == '8']selected="selected"[/#if]>8</option>
											</select>
										</span>
										</br>
										<span class="">最多可选个数：
											<select name="kxgs" id="" class="chosen-size" data-value="${stxx.xxkzdxzs}" onchange="onchange_kxgs(this)">
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
											</select>
										</span>
										<span class="">是否可排序：
											<select name="sfyxpx" id="" class="chosen-size" data-value="${stxx.sfyxpx}" onchange="onchange_sfyxpx(this)">
												<option value="0" [#if stxx.sfyxpx == '0']selected="selected"[/#if]>否</option>
												<option value="1" [#if stxx.sfyxpx == '1']selected="selected"[/#if]>是</option>
											</select>
										</span>
										<span class="">题目提示内容: <input name="ts" type="text" class="" value="${stxx.ts}" placeholder=""/>
										</span>
								</div>
								<div class="wj-setting-content">
									[#list xxxxList as item]
										[#if item.stid == stxx.stid]
										<div class="checkbox">
											<div class="setting-block">
												<label class="no-sort">
									    	    <input type="checkbox" option${item_index} xxfz="${item.xxfz}"><span>${item.xxmc}</span>
									        	</label>
											</div>
										</div>						
										[/#if]				
									[/#list]
								</div>
							</div>
							[#elseif stxx.stlx==4]
							<!-- 多选组合题 -->
							<div class="item topic-type" data-type="${stxx.stlx}" data-issue="duxzh">
									<div class="wj-setting-title no-sort">
										<span class="wj-question-type"><span class="current-number">${stxx.xssx}</span>、<span class="official-title">${stxx.stmc}</span></span>
										<span class="checkbox">
								    		<label>
								      			<input type="checkbox" [#if stxx.sfbd == 'true']checked="checked"[/#if] name="sfbd" class="is-mandatory"> 必填题
								    		</label>
							  			</span>
										<span class="">每行选项个数：
											<select name="mhxxgs" class="chosen-size" data-value="${stxx.mhxxgs}">
												<option value="1" [#if stxx.mhxxgs == '1']selected="selected"[/#if]>1</option>
												<option value="2" [#if stxx.mhxxgs == '2']selected="selected"[/#if]>2</option>
												<option value="3" [#if stxx.mhxxgs == '3']selected="selected"[/#if]>3</option>
												<option value="4" [#if stxx.mhxxgs == '4']selected="selected"[/#if]>4</option>
												<option value="5" [#if stxx.mhxxgs == '5']selected="selected"[/#if]>5</option>
												<option value="6" [#if stxx.mhxxgs == '6']selected="selected"[/#if]>6</option>
												<option value="7" [#if stxx.mhxxgs == '7']selected="selected"[/#if]>7</option>
												<option value="8" [#if stxx.mhxxgs == '8']selected="selected"[/#if]>8</option>
											</select>
										</span>
										</br>
										<span class="">最多可选个数：
											<select name="kxgs" class="chosen-size" data-value="${stxx.xxkzdxzs}">
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
											</select>
										</span>
										<span class="">
											题目提示内容:<input name="ts" type="text" class="" value="${stxx.ts}" placeholder=""/>
										</span>
									</div>
									<div class="wj-setting-content">
									[#list xxxxList as item]
										[#if item.stid == stxx.stid]
										<div class="checkbox">
											<div class="setting-block">
												<label class="no-sort">
													<input type="checkbox" option${item_index} xxfz="${item.xxfz}"><span>${item.xxmc}</span>
												</label>
											</div>
										</div>
										[/#if]
									[/#list]
									</div>
							</div>
							[#elseif stxx.stlx==5]
							<!-- 文本题-->
							<div class="item topic-type" data-type="${stxx.stlx}" data-issue="wbt">
								<div class="wj-setting-title no-sort">
									<span class="wj-question-type"><span class="current-number">${stxx.xssx}</span>、<span class="official-title">${stxx.stmc}</span></span>
									<span class="checkbox">
								    	<label><input type="checkbox" [#if stxx.sfbd == 'true']checked="checked"[/#if] class="is-mandatory"> 必填题  </label>
							  		</span>
									<span class="">最大字数：<input type="text" name="zdzs" data-rules='{"required":true,"range":[1,500],"digits":true}' value="${stxx.zdzs}"/></span>
									<span class="">文本框高度：
										<select name="wbgd" class="wbms_select">
											<option value="1" [#if stxx.wbgd==1]selected="selected"[/#if]>一行</option>
											<option value="2" [#if stxx.wbgd==2]selected="selected"[/#if]>两行</option>
											<option value="3" [#if stxx.wbgd==3]selected="selected"[/#if]>三行</option>
											<option value="4" [#if stxx.wbgd==4]selected="selected"[/#if]>四行</option>
											<option value="5" [#if stxx.wbgd==5]selected="selected"[/#if]>五行</option>
											<option value="6" [#if stxx.wbgd==6]selected="selected"[/#if]>六行</option>
										</select>
									</span>
									</br>
									<span class="">文本类型：
										<select name="wblx" class="chosen-size" data-value="${stxx.wblx}">
											<option value="1" [#if stxx.wblx == '1']selected="selected"[/#if]>文本</option>
											<option value="2" [#if stxx.wblx == '2']selected="selected"[/#if]>数字</option>
										</select>
									</span>
									<span class="">
										题目提示内容:<input name="ts" type="text" class="" value="${stxx.ts}" placeholder="" style="max-width:160px;"/>
									</span>
								</div>
								<div class="wj-setting-content">
									[#assign wbgd=(stxx.wbgd) ?number * 40] 
									<textarea name="tkly" cols=""  rows="${stxx.wbgd}" class="form-control no-sort" placeholder="请输入..." style="height:${wbgd}px;"></textarea>
								</div>
							</div>
							[#elseif stxx.stlx==0]
							<!-- 描述说明 -->
							<div class="item"  data-type="${stxx.stlx}" data-issue="mssm">
								<div class="wj-setting-title no-sort">
									<span class="">对齐方式：
										<select name="dqfs" class="chosen-size" data-value="${stxx.dqfs}">
											<option value="center" [#if stxx.dqfs == 'center']selected="selected"[/#if]>居中</option>
											<option value="left" [#if stxx.dqfs == 'left']selected="selected"[/#if]>居左</option>
											<option value="right" [#if stxx.dqfs == 'right']selected="selected"[/#if]>居右</option>
										</select>
									</span>
								</div>
								<div class="wj-setting-content">
									<textarea name="tkly" cols="" rows="4" class="form-control no-sort" data-rules='{"required":true,"rangelength":[1,200]}'>${stxx.stmc}</textarea>
								</div>
							</div>
							[#else]
							<!--未定义类型-->	
							[/#if]
						[/#list]
						</div>
						</form>
					</div>
					<div class="wj-chosen-module" style="display: none;">
						<div class="radio">
							<div class="setting-block">
								<label class="no-sort">
					          <input type="radio" value="option1"><span>选项n</span>
					        </label>
							</div>
						</div>
						<div class="checkbox">
							<div class="setting-block">
								<label class="no-sort">
					          <input type="checkbox" value="option1"><span>选项n</span>
					        </label>
							</div>
						</div>
						<div class="add-chosen clearfix">
							<i class="tjxx no-sort"></i>
							<div class="float_r no-sort">
								<!-- 跳转设置 start-->
								<div class="number-jump ydz-jump">
									共<span class="wj-topic-number"></span>题，当前为<span class="current-index"></span>题，跳转至
									<div class="inline analog-select analog-select-tz-st">
										<input type="text" name="" id="" value="" value="" placeholder="请选择" class="analog-input"/> 
										<div class="select-content jump-to tz-st-list">
										</div>
									</div>
									题
									<button type="button" class="btn btn-primary btn-xs btn_tz" href="javascript:void(0);"><i class="icon-map-marker"></i>跳转</button>
								</div>
								<!-- 跳转设置 end-->
								
								<!-- 后台设置start -->
								<div class="number-jump htsz-jump hide">
									答案选项
									<div class="inline analog-select analog-select-xx">
										<input type="text" name="" id="" value="" readonly="" value="请选择" placeholder="请选择" class="analog-input"/> 
										<div class="select-content xx-list">
										</div>
									</div>

									跳转至
									<div class="inline analog-select analog-select-xx-st">
										<input type="text" name="" value="" readonly="" value="" placeholder="请选择" class="analog-input"/> 
										<div class="select-content jump-to xx-st-list">
										</div>
									</div>
									题
								</div>
								<!-- 后台设置end -->
								<div class="btn-group">
									<button type="button" class="btn btn-success btn-sm btn_sc" href="javascript:void(0);"><i class="icon-trash"></i>删除</button>
									<button type="button" class="btn btn-info btn-sm btn_fz"  href="javascript:void(0);"><i class="icon-copy"></i>复制</button>
									<button type="button" class="btn btn-warning btn-sm btn_ydz" href="javascript:void(0);"><i class="icon-move"></i>移动至</button>
								</div>
							</div>
						</div>
						<!-- 编辑框 -->
						<div class="edit-area no-sort">
							<div class="position-absolute">
								<input type="text" name="xxmc" value="" />
								<span class="fa fa-arrow-circle-o-up"></span>
								<span class="fa fa-arrow-circle-o-down"></span>
								<span class="fa fa-trash"></span>
								<span class="fa">选项分值:</span>
								<input type="text" name="xxfz" data-rules='{"required":false,"range":[0,100],"digits":true}' value="0" />
							</div>
						</div>
						<div class="topic-module">
							<input type="text" name="" id="" value="" class="edit-title" />
							<div class="item" data-issue="dxxz" data-type="1">
								<input type="hidden" name="tzstInfo" value=""/>
								<div class="wj-setting-title no-sort">
									<span class="wj-question-type"><span class="current-number"></span>、<span class="official-title">这是一道单项选择题，这个是题目内容</span></span>
									<span class="checkbox">
								    	<label>
								      		<input type="checkbox" checked="" name="sfbd" class="is-mandatory"> 必填题
								    	</label>
							  		</span>
							  		<span class="">每行选项个数：
										<select name="mhxxgs" class="chosen-size" data-value="">
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
											<option value="5">5</option>
											<option value="6">6</option>
											<option value="7">7</option>
											<option value="8">8</option>
										</select>
									</span>
									</br>
									<span class="">
										题目提示内容:<input name="ts" type="text" class="" value="" placeholder=""/>
									</span>
								</div>
								<div class="wj-setting-content">
									<div class="radio">
										<div class="setting-block">
											<label class="no-sort">
								          <input type="radio" value="option1" xxfz="0"><span>选项1</span>
								        </label>
										</div>
									</div>
									<div class="radio">
										<div class="setting-block">
											<label class="no-sort">
								          <input type="radio" value="option1" xxfz="0"><span>选项2</span>
								        </label>
										</div>
									</div>
									<div class="radio">
										<div class="setting-block">
											<label class="no-sort">
								          <input type="radio" value="option1" xxfz="0"><span>选项3</span>
								        </label>
										</div>
									</div>
								</div>
							</div>
							<div class="item" data-issue="dxzhe" data-type="2">
								<div class="wj-setting-title no-sort">
									<span class="wj-question-type"><span class="current-number"></span>、<span class="official-title">这是一道单项组合题，这个是题目内容</span></span>
									<span class="checkbox">
								    	<label>
								      		<input type="checkbox" checked="" name="sfbd" class="is-mandatory"> 必填题
								    	</label>
							  		</span>
							  		<span class="">每行选项个数：
										<select name="mhxxgs" class="chosen-size" data-value="">
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
											<option value="5">5</option>
											<option value="6">6</option>
											<option value="7">7</option>
											<option value="8">8</option>
										</select>
									</span>
									</br>
									<span class="">
										题目提示内容:<input name="ts" type="text" class="" value="" placeholder=""/>
									</span>
								</div>
								<div class="wj-setting-content">
									<div class="radio">
										<div class="setting-block">
											<label class="no-sort">
								          <input type="radio" value="option1" xxfz="0"><span>选项1</span>
								        </label>
										</div>
									</div>
									<div class="radio">
										<div class="setting-block">
											<label class="no-sort">
								          <input type="radio" value="option1" xxfz="0"><span>选项2</span>
								        </label>
										</div>
									</div>
									<div class="radio other">
										<div class="setting-block">
											<label class="no-sort">
								          <input type="radio" value="option1" xxfz="0"><span>选项3</span>
								        </label>
											<input type="text" name="" id="" value="" class="no-sort" />
										</div>
									</div>
								</div>
							</div>
							<div class="item" data-issue="duxxz" data-type="3">
								<div class="wj-setting-title no-sort">
									<span class="wj-question-type"><span class="current-number"></span>、<span class="official-title">这是一道多项选择题，这个是题目内容</span></span>
									<span class="checkbox">
								   		<label>
								      		<input type="checkbox" checked="" name="sfbd" class="is-mandatory"> 必填题
								    	</label>
							  		</span>
							  		<span class="">每行选项个数：
										<select name="mhxxgs" class="chosen-size" data-value="">
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
											<option value="5">5</option>
											<option value="6">6</option>
											<option value="7">7</option>
											<option value="8">8</option>
										</select>
									</span>
									</br>
									<span class="">最多可选个数：
										<select name="kxgs" id="" class="chosen-size" onchange="onchange_kxgs(this)">
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3" selected="selected">3</option>
										</select>
									</span>
									<span class="">是否可排序：
										<select name="sfyxpx" id="" class="chosen-size" data-value="0" onchange="onchange_sfyxpx(this)">
											<option value="0">否</option>
											<option value="1">是</option>
										</select>
									</span>
									<span class="">
										题目提示内容:<input name="ts" type="text" class="" value="" placeholder=""/>
									</span>
								</div>
								<div class="wj-setting-content">
									<div class="checkbox">
										<div class="setting-block">
											<label class="no-sort">
								          <input type="checkbox" value="option1" xxfz="0"><span>选项1</span>
								        </label>
										</div>
									</div>
									<div class="checkbox">
										<div class="setting-block">
											<label class="no-sort">
								          <input type="checkbox" value="option1" xxfz="0"><span>选项2</span>
								        </label>
										</div>
									</div>
									<div class="checkbox">
										<div class="setting-block">
											<label class="no-sort">
								          <input type="checkbox" value="option1" xxfz="0"><span>选项3</span>
								        </label>
										</div>
									</div>
								</div>
							</div>
							<div class="item" data-issue="duxzh" data-type="4">
								<div class="wj-setting-title no-sort">
									<span class="wj-question-type"><span class="current-number"></span>、<span class="official-title">这是一道多项组合题，这个是题目内容</span></span>
									<span class="checkbox">
								    	<label>
								      		<input type="checkbox" checked="" name="sfbd" class="is-mandatory"> 必填题
								   	 	</label>
							  		</span>
							  		<span class="">每行选项个数：
										<select name="mhxxgs" class="chosen-size" data-value="">
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
											<option value="5">5</option>
											<option value="6">6</option>
											<option value="7">7</option>
											<option value="8">8</option>
										</select>
									</span>
									</br>
									<span class="">最多可选个数：
										<select name="kxgs" id="" class="chosen-size">
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3" selected="selected">3</option>
										</select>
									</span>
									<span class="">
										题目提示内容:<input name="ts" type="text" class="" value="" placeholder=""/>
									</span>
								</div>
								<div class="wj-setting-content">
									<div class="checkbox">
										<div class="setting-block">
											<label class="no-sort">
								          <input type="checkbox" value="option1" xxfz="0"><span>选项1</span>
								        </label>
										</div>
									</div>
									<div class="checkbox">
										<div class="setting-block">
											<label class="no-sort">
								          <input type="checkbox" value="option1" xxfz="0"><span>选项2</span>
								        </label>
										</div>
									</div>
									<div class="checkbox other">
										<div class="setting-block">
											<label class="no-sort">
								          <input type="checkbox" value="option1" xxfz="0"><span>选项3</span>
								        </label>
											<input type="text" name="" id="" value="" class="no-sort" />
										</div>
									</div>
								</div>
							</div>
							<div class="item" data-issue="wbt" data-type="5">
								<div class="wj-setting-title no-sort">
									<span class="wj-question-type"><span class="current-number"></span>、<span class="official-title">这是一道文本题，这个是题目内容</span></span>
									<span class="checkbox">
								    <label>
								      <input type="checkbox" checked="" name="" class="is-mandatory"> 必填题
								    </label>
							  	</span>
									<span class="">最大字数：<input type="text" name="zdzs" value="100" data-rules='{"required":true,"range":[1,500],"digits":true}'/></span>
									<span class="">文本框高度：
										<select name="wbgd" class="wbms_select">
											<option value="1" selected="selected">一行</option>
											<option value="2">两行</option>
											<option value="3">三行</option>
											<option value="4">四行</option>
											<option value="5">五行</option>
											<option value="6">六行</option>
										</select>
									</span>
									</br>
									<span class="">文本类型：
										<select name="wblx" class="chosen-size" data-value="">
											<option value="1" >文本</option>
											<option value="2" >数字</option>
										</select>
									</span>
									<span class="">
										题目提示内容:<input name="ts" type="text" class="" value="" placeholder="" style="max-width:160px;"/>
									</span>
								</div>
								<div class="wj-setting-content">
									<textarea name="tkly" cols="" rows="1" class="form-control no-sort" style="height:40px;" placeholder="请输入..."></textarea>
								</div>
							</div>
							<div class="item" data-issue="mssm" data-type="0">
								<div class="wj-setting-title no-sort">
									<span class="">对齐方式：
										<select name="dqfs" class="chosen-size" data-value="">
											<option value="center" selected="selected">居中</option>
											<option value="left">居左</option>
											<option value="right">居右</option>
										</select>
									</span>
								</div>
								<div class="wj-setting-content">
									<textarea name="tkly" cols="" rows="4" class="form-control no-sort" data-rules='{"required":true,"rangelength":[1,200]}'></textarea>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript" charset="utf-8" src="${base}/js/wjdc/wjsj.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</body>
</html>