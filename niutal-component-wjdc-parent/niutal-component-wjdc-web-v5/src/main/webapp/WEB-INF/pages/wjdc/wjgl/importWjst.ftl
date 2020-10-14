<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${base}/css/wjdc/wjsj.css?ver=${messageUtil("niutal.cssVersion")}" />
		<script type="text/javascript">
			(function($){
				$.extend($,{
					wjdc: {
						qType : /\[题目类型[:：][^\]]*\]/, //试题类型标记
						qTStart : /\[题目类型[:：]/, //试题类型值开始标记
						qRequ : /\[是否必答[:：][^\]]*\]/, //试题必答标记
						qRStart : /\[是否必答[:：]/, //是否必答值开始标记
						qAConut : /\[可选项个数[:：][^\]]*\]/, //可选项个数标记
						qAStart : /\[可选项个数[:：]/, //可选项个数开始标记
						
						oStart: /^[A-Z]*[\、. :：]/,
						endStr : /[\]]/, //通用]结束标记
						beforStr : /^[\[]/, //通用[开始标记
						format:function(content){
						 	var option =$.wjdc;
							var _formatQueStyle = function(n){
								return n.match(option.qType)[0].replace(option.qTStart,'').replace(option.endStr,'').replace(/\s*/g,'');
							};
							var _formatQueReq = function(n){
								return n.match(option.qRequ)[0].replace(option.qRStart,'').replace(option.endStr,'').replace(/\s*/g,'');
							};
							var _formatACount = function(n){
								return n.match(option.qAConut)[0].replace(option.qAStart,'').replace(option.endStr,'').replace(/\s*/g,'');
							};
							var _formatQueTitle = function(n){
								return n.replace(/\[[^\]]*\]/g,'');
							};
							
							var wjst = [];
							var index = 1 ;
							var lines = content.split("\n");
							$.each(lines,function(i,n){
								var line = $.trim(n) ;
								if (line == "") return;
								//选项
								if (option.oStart.test(line)){	
									if (wjst.length > 0){
										wjst[wjst.length-1]["stxx"].push(line);
									}
								} else if (option.qType.test(line)){ //题目信息
									var stlx = _formatQueStyle(line);
									var sfbt = _formatQueReq(line) == "是";
									var title = _formatQueTitle(line);
									var type,kxgs;
									
									if (stlx == "文本题"){
										wjst.push({type:"5",title:title,sfbt:sfbt,xssx:index++,zdzs:500,wbgd:2});
									} else {
										if (stlx == "单选题"){
											type="1";
											kxgs="1";
										} else if (stlx == "单选组合"){
											type="2";
											kxgs="1";
										} else if (stlx == "多选题"){
											type="3";
											kxgs=_formatACount(line);
										} else if (stlx == "多选组合"){
											type="4";
											kxgs=_formatACount(line);
										}  else {
											return;
										}
										wjst.push({type:type,title:title,sfbt:sfbt,xssx:index++,stxx:[],kxgs:kxgs});
									}
								} else {//描述说明
									wjst.push({type:"0",mssm:line});
								}
							});
							return wjst;
						}
					}
				});
			}(jQuery));
		
			$(function(){
				$(".wj-conetnt>textarea").change(function(){
					$(".wj-conetnt").children(":not(textarea)").remove();
					var wjst = $.wjdc.format($(this).val());
					 $("#wjst").val(JSON.stringify(wjst));
					
					$.each(wjst,function(i,n){
						if (n["type"] == "0"){
							$(".wj-conetnt").append('<div class="wj-topic-title">'+n["mssm"]+'</div>')
						} else {
							var item = $('<div class="item" data-type="'+n["type"] +'"></div>');
							var topic = $('<div class="wj-topic"></div>');
							topic.append(n["xssx"]+"、"+n["title"]);
							if (n["sfbt"]){
								topic.append('<span class="red">*</span>');
							}
							var content = $('<div class="wj-topic-content"></div>');	
							
							if (n["type"]=="5"){
								content.append('<textarea name="tkly" rows="1" class="form-control no-sort" style="height:80px;"></textarea>');
							} else {
								$.each(n["stxx"],function(x,y){
									var div;
									if (n["type"]=="1" || n["type"]=="2"){
										div = $('<div class="radio"><label><input type="radio" name="st'+i+'" value="option'+x+'" >'+y+'</label></div>');
									} else {
										div = $('<div class="checkbox"><label><input type="checkbox" name="st'+i+'" value="option'+x+'" >'+y+'</label></div>');
									}
									
									if ((n["type"]=="2" || n["type"]=="4") && x==n["stxx"].length -1){
										div.append('<input type="text" name="text" class="no-sort" />');
									}
									content.append(div);
								});
							}
							item.append(topic).append(content);
							$(".wj-conetnt").append(item);
						}
					});
				});
				
				var v = [];
				$.each($("#tkly").val().split("\n"),function(i,n){
					v.push($.trim(n))
				})
				$("#tkly").val(v.join("\n"));
				$(".wj-conetnt>textarea").change();
				$('[data-toggle*="validation"]').trigger("validation");
				
				$(".edit-paper-icon").click(function(){
					 submitForm("ajaxForm",function(responseData,statusText){
						 var wjid= $("#wjid").val();
						 $("#modifyModal").reloadDialog({
							 href:"editWjst/"+wjid+".zf?type=0"
						 })
					 });
				 });
			});
		</script>
	</head>
	<body>
		<form class="form-horizontal" role="form" data-toggle="validation" action="saveWjst.zf" id="ajaxForm" method="post">
			<input type="hidden" name="wjid" value="${model.wjid}" id="wjid"/>
			<input type="hidden" name="wjst" id="wjst"/>
		</form>
		<div class="no-response-container design-question-cont">
		
			<div class="title row">
				<div class="col-lg-10">
					<h4 class="text-center">${model.wjmc}</h4>
				</div>
				<div class="col-lg-2 text-right"><label class="edit-paper-icon"><i class="fa fa-pencil-square-o"></i></label><span class="edit-paper">编辑问卷</span></div>
			</div>
					
			<div class="export-text  wj clearfix">
			
				<div class="wj-content">
					<div class="wj-content-title clearfix">
						<div class="left">
							<span class="wj-blue-h3">添加问卷内容</span>
							<span class="wj-normal-font">
								将问卷文本内容直接复制到下面文本框内
							</span>
						</div>
						<div class="right">
							<span class="wj-btn btn-orange"  onclick="window.open('${base}/download/wjdc_template.txt')">
								<i class="glyphicon glyphicon-download-alt"></i>下载模板
							</span>
						</div>
					</div>
					<div class="wj-conetnt">
						<textarea name="tkly"  class="form-control no-sort" id="tkly">
							[#list stxxList as stxx]
								[#if stxx.stlx=='0']
									${stxx.stmc}
								[#else]
									${stxx.stmc}[题目类型：[#if stxx.stlx==1]单选题[#elseif stxx.stlx==2]单选组合[#elseif stxx.stlx==3]多选题[#elseif stxx.stlx==4]多选组合[#elseif stxx.stlx==5]文本题[/#if]][是否必答：[#if stxx.sfbd == 'true']是[#else]否[/#if]][#if stxx.stlx==3 || stxx.stlx==4][可选项个数：${stxx.xxkzdxzs}][/#if]
								[/#if]
								[#list xxxxList as item]
									[#if item.stid == stxx.stid]
										${item.xxmc}
									[/#if]
								[/#list]
							[/#list]
						</textarea>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>