<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>${pageTitle}</title>
		
		[#include "/globalweb/head/niutal-ui-base.ftl" /]
		[#include "/globalweb/head/niutal-ui-bs.ftl" /]
		[#include "/globalweb/head/niutal-ui-v5.ftl" /]
		[#include "/globalweb/head/niutal-ui-bootbox.ftl" /]
		[#include "/globalweb/head/niutal-ui-validation.ftl" /]
		[#include "/globalweb/head/niutal-ui-layer.ftl" /]
		
		<link href="${messageUtil("system.stylePath")}/assets/css/niutal-ui-base.css?ver=${messageUtil("niutal.jsVersion")}"  rel="stylesheet" type="text/css"/>
		<link type="text/css" rel="stylesheet"  href="${base}/css/wjdc/yhdj.css?ver=${messageUtil("niutal.cssVersion")}" />
	</head>
	<body>
		[#if isShowHeader == true]
		<header class="header-3">调查问卷</header>
		[/#if]
		<div class="container wjdc">
			<div class="row">
				<div class="col-md-12">
					<div class="text-center wj-blue-h3">${model.wjmc}</div>
					<div class="tip">${model.jsy}</div>
					<!-- 问卷列表 -->
					<form action="${base}/wjdc/wjgl/submitYhdj.zf" data-toggle="validation"  role="form" class="form-horizontal sl_all_form"  id="submitAjaxForm" method="post" >
						<input type="hidden" value="${model.wjid}" name="wjid" id="wjid"/>
						<input type="hidden" value="${url}" name="url" id="url"/>
					<div class="content">
						[#assign stNo = 0]
						[#list stxxList as stxx]
							[#if stxx.stlx==1]
							[#assign stNo = stNo + 1]
							<!-- 单项选择题 -->
							<div class="item [#if stxx.sfwc == '1'] st_finish[/#if]" stid="${stxx.stid}" data-type="${stxx.stlx}" data-issue="dxxz" [#if stxx.sfbd == 'true']sfbd="true"[/#if] style="display:${stxx.display}">
								<div class="question">
									<ul class="question_title_ul">
										<li class="stmc_title">${stNo}，${stxx.stmc}</li>
										<li class="st_red">[#if stxx.sfbd == 'true']*[/#if]</li>
										[#if stxx.ts??]<li class="st_tips">( ${stxx.ts} )</li>[/#if]
									</ul>
								</div>
								<div class="answer">
									<div class="form-group  clearfix">
									[#assign mhxxgs = stxx.mhxxgs?number]
									[#assign mhxxgs = mhxxgs + 1]
									[#assign width = 768 / stxx.mhxxgs?number]
									[#list xxxxList as item]
										[#if item.stid == stxx.stid]
										[#assign mhxxgs = mhxxgs-1]
										<div class="col-sm-12" [#if mhxxgs = 0]style="clear:left;width:${width}px;"[#else]style="width:${width}px;"[/#if]>
											<input class="default-radio primary-radio" type="radio" name="${item.stid}" value="${item.xxid}" id="${item.xxid}" xxid="${item.xxid}" tzstid="${item.tzstid}" [#if item.checked == '1']checked="checked"[/#if] [#if stxx.sfbd == 'true']required [/#if]>
											<label for="${item.xxid}">${item.xxmc}</label>
										</div>
										[#if mhxxgs = 0]
											[#assign mhxxgs = stxx.mhxxgs?number]
										[/#if]
										[/#if]
									[/#list]
									</div>
								</div>
							</div>
							[#elseif stxx.stlx==2]
							[#assign stNo = stNo + 1]
							<!-- 单选组合题 -->
							<div class="item [#if stxx.sfwc == '1'] st_finish[/#if]" stid="${stxx.stid}" data-type="${stxx.stlx}" data-issue="dxzhe" [#if stxx.sfbd == 'true']sfbd="true"[/#if] style="display:${stxx.display}">
								<div class="question">
									<ul class="question_title_ul">
										<li class="stmc_title">${stNo}，${stxx.stmc}</li>
										<li class="st_red">[#if stxx.sfbd == 'true']*[/#if]</li>
										[#if stxx.ts??]<li class="st_tips">( ${stxx.ts} )</li>[/#if]
									</ul>
								</div>
								<div class="answer">
									<div class="form-group  clearfix">
										[#assign mhxxgs = stxx.mhxxgs?number]
										[#assign mhxxgs = mhxxgs + 1]
										[#assign width = 768 / stxx.mhxxgs?number]
										[#list xxxxList as item]
											[#if item.stid == stxx.stid]
											[#assign mhxxgs = mhxxgs-1]
											<div class="col-sm-12" [#if mhxxgs = 0]style="clear:left;width:${width}px;"[#else]style="width:${width}px;"[/#if]>
												<input class="default-radio primary-radio" type="radio" name="${item.stid}" value="${item.xxid}" id="${item.xxid}" xxid="${item.xxid}" tzstid="${item.tzstid}" [#if item.checked == '1']checked="checked"[/#if] [#if stxx.sfbd == 'true']required [/#if]>
												<label for="${item.xxid}">${item.xxmc}
												[#if item.isLastxx == 'true']
													<input style='height:  20px;width: 250px;' type='text' value='${item.lastxxOption}' name='${item.stid}_txnr' class='default-text primary-text'/>
												[/#if]
												</label>
											</div>
											[#if mhxxgs = 0]
												[#assign mhxxgs = stxx.mhxxgs?number]
											[/#if]
											[/#if]	
										[/#list]
									</div>
								</div>
							</div>
							[#elseif stxx.stlx==3]
							[#assign stNo = stNo + 1]
							<!-- 多选题 -->
							<div class="item [#if stxx.sfwc == '1'] st_finish[/#if]" stid="${stxx.stid}" data-type="${stxx.stlx}" data-issue="duxxz" kxgs="${stxx.xxkzdxzs}" [#if stxx.sfbd == 'true']sfbd="true"[/#if] style="display:${stxx.display}" sfyxpx="${stxx.sfyxpx}">
								<div class="question">
									<ul class="question_title_ul">
										<li class="stmc_title">${stNo}，${stxx.stmc}</li>
										<li class="st_red">[#if stxx.sfbd == 'true']*[/#if]</li>
										[#if stxx.ts??]<li class="st_tips">( ${stxx.ts} )</li>[/#if]
									</ul>
								</div>
								<div class="answer">
									<div class="form-group  clearfix">
										[#assign mhxxgs = stxx.mhxxgs?number]
										[#assign mhxxgs = mhxxgs + 1]
										[#assign width = 768 / stxx.mhxxgs?number]
										[#list xxxxList as item]
											[#if item.stid == stxx.stid]
											[#assign mhxxgs = mhxxgs-1]
												<div class="col-sm-12" [#if mhxxgs = 0]style="clear:left;width:${width}px;"[#else]style="width:${width}px;"[/#if]>
													<input class="default-checkbox primary-checkbox" type="checkbox" name="${item.stid}" value="${item.xxid}" id="${item.xxid}" xxid="${item.xxid}" tzstid="${item.tzstid}" [#if item.checked == '1']checked="checked"[/#if] [#if stxx.sfbd == 'true']required [/#if] maxlength="${stxx.xxkzdxzs}" >
													<label for="${item.xxid}">${item.xxmc}</label>
												</div>
											[#if mhxxgs = 0]
												[#assign mhxxgs = stxx.mhxxgs?number]
											[/#if]
											[/#if]
										[/#list]
										[#if stxx.sfyxpx == '1']
										<!-- 排序表格 -->
										[#assign height = stxx.xxkzdxzs*25]
										<div class="form-group  clearfix sort_div" style="min-height:75px;height:${height}px;display:none;">
											<div class="sort_div_left" style="min-height:75px;height:${height}px;">
												<ul stid="${stxx.stid}">
												</ul>
											</div>
											<div class="sort_div_right">
												<button class="up" type="button" onclick="javascript:void(0);">上移</button>
												<button class="down" type="button" onclick="javascript:void(0);">下移</button>
											</div>
										</div>
										[/#if]
									</div>
								</div>
							</div>
							[#elseif stxx.stlx==4]
							[#assign stNo = stNo + 1]
							<!-- 多选组合题 -->
							<div class="item [#if stxx.sfwc == '1'] st_finish[/#if]" stid="${stxx.stid}" data-type="${stxx.stlx}" data-issue="duxzh" kxgs="${stxx.xxkzdxzs}" [#if stxx.sfbd == 'true']sfbd="true"[/#if] style="display:${stxx.display}">
								<div class="question">
									<ul class="question_title_ul">
										<li class="stmc_title">${stNo}，${stxx.stmc}</li>
										<li class="st_red">[#if stxx.sfbd == 'true']*[/#if]</li>
										[#if stxx.ts??]<li class="st_tips">( ${stxx.ts} )</li>[/#if]
									</ul>
								</div>
								<div class="answer">
									<div class="form-group  clearfix">
										[#assign mhxxgs = stxx.mhxxgs?number]
										[#assign mhxxgs = mhxxgs + 1]
										[#assign width = 768 / stxx.mhxxgs?number]
										[#list xxxxList as item]
											[#if item.stid == stxx.stid]
											[#assign mhxxgs = mhxxgs-1]
												<div class="col-sm-12" [#if mhxxgs = 0]style="clear:left;width:${width}px;"[#else]style="width:${width}px;"[/#if]>
													<input class="default-checkbox primary-checkbox" type="checkbox" name="${item.stid}" value="${item.xxid}" id="${item.xxid}" xxid="${item.xxid}" tzstid="${item.tzstid}" [#if item.checked == '1']checked="checked"[/#if] [#if stxx.sfbd == 'true']required [/#if] maxlength="${stxx.xxkzdxzs}">
													<label for="${item.xxid}">${item.xxmc}
													[#if item.isLastxx == 'true']
														<input style='height:  20px;width: 250px;' type='text' value='${item.lastxxOption}' name='${item.stid}_txnr' class='default-text primary-text'/>
													[/#if]
													</label>
												</div>
											[#if mhxxgs = 0]
												[#assign mhxxgs = stxx.mhxxgs?number]
											[/#if]
											[/#if]									
										[/#list]
									</div>
								</div>
							</div>
							[#elseif stxx.stlx==5]
							[#assign stNo = stNo + 1]
							<!-- 文本题-->
							<div class="item [#if stxx.sfwc == '1'] st_finish[/#if]" stid="${stxx.stid}" data-type="${stxx.stlx}" data-issue="wbt" [#if stxx.sfbd == 'true']sfbd="true"[/#if] style="display:${stxx.display}"> 
								<div class="question">
									<ul class="question_title_ul">
										<li class="stmc_title">${stNo}，${stxx.stmc}</li>
										<li class="st_red">[#if stxx.sfbd == 'true']*[/#if]</li>
										[#if stxx.ts??]<li class="st_tips">( ${stxx.ts} )</li>[/#if]
									</ul>
								</div>
								<div class="answer">
									<div class="form-group clearfix">
										<div class="col-md-12">
											[#assign wbgd=(stxx.wbgd) ?number * 40] 
											[#assign txnr = '']
											[#list xxxxList as item]
												[#if item.stid == stxx.stid]
													[#assign txnr = item.txnr]
												[/#if]
											[/#list]
											<textarea name="${stxx.stid}" rows="${stxx.wbgd}" placeholder="在文本框内输入内容...." class="form-control" style="height:${wbgd}px;" [#if stxx.sfbd == 'true']required [/#if] [#if stxx.wblx == '2'] digits="true"[/#if] maxlength="${stxx.zdzs}">${txnr}</textarea>
										</div>
									</div>
								</div>
							</div>
							[#elseif stxx.stlx==0]
							<!-- 描述说明 -->
							<div class="item" stid="${stxx.stid}" data-type="${stxx.stlx}" data-issue="mssm">
								<div class="question"></div>
								<div class="answer">
									<div class="form-group clearfix">
										<div class="col-md-12">
											<h4 style="text-align:${stxx.dqfs}">${stxx.stmc}</h4>
										</div>
									</div>
								</div>
							</div>
							[#else]
							<!--未定义类型-->
							[/#if]
						[/#list]
					</div>
					</form>
					<div class="tip">
						${model.jwy}
					</div>
					<div class="submit">
						<button class="btn zf-btn btn-prihov middle" id="submitButton" [#if acceptSubmit == 'false']disabled="disabled"[/#if]>提交</button>
					</div>
				</div>
			</div>
			<!--ajax提交保存用户答卷试题-->
			<form action="${base}/wjdc/wjgl/ajaxSaveYhdjStxx.zf" id="ajaxSaveYhdjStxxForm" role="form" class="form-horizontal sl_all_form" value="" method="post" style="display:none;">
				<input type="hidden" value="${model.wjid}" name="wjid"/>
				<div id="ajaxSaveYhdjStxxForm_content">
				
				</div>
			</form>
		</div>
		<script type="text/javascript">
			var useAsyncSubmit = "${useAsyncSubmit}";
			var contextPath = "${contextPath}";
		</script>
		<script type="text/javascript" charset="utf-8" src="${base}/js/wjdc/yhdj.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</body>
</html>