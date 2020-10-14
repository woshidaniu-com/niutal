<!doctype html>
<html>
	<head>
		[#include "/globalweb/head/head_v5.ftl" /]
		[#include "/globalweb/comp/xtgl/dymbgl/head.ftl" /]
		[#include "/globalweb/head/niutal-ui-ruler.ftl" /]
		<title>打印模板设计</title>
	</head>
	
	<body class="edit">
		<input type="hidden" id="op" name="op" value="zj"/>
		<div class="top-area">
			<div class="fr oprate">
				<div class="btn" id="save_btn"><span><img src="${messageUtil("system.stylePath")}/assets/images/print/save.png"/></span></div>
				<div class="btn" id="clear_btn"><span><img src="${messageUtil("system.stylePath")}/assets/images/print/clear.png"/></span></div>
			</div>
		</div>
		<div class="left-area">
			<div class="block">
				<span class="title">模板名称</span>
				<input type="text" name="" id="template-input" value="" class="form-control"/>
				<input type="hidden" name="mbid" id="mbid" value="" />
				<p class="template-name hide"></p>
			</div>
			<div class="block size">
				<span class="title">表单尺寸</span>
				<div>
					<input type="text" class="form-control" name="formWidth" id="formWidth" value="600px" placeholder="宽"><span>*</span>
					<input type="text" class="form-control" name="formHeight" id="formHeight" value="600px" placeholder="高">
				</div>
			</div>
			<div class="block">
				<span class="title" style="width:100%">打印模板类型</span>
				<select id="mblxdm" name="mblxdm" class="form-control">
					<option value="">---请选择---</option>
					[#list dymblxList as item]
						<option value="${item.dm}">${item.mc}</option>
					[/#list]
				</select>
			</div>
			
			<div class="block dataSource">
				<span class="title">数据源字段</span>
				<div class="content">
				</div>
				<div id="data-source" style="display: none;">
					<div class="text-module data">
						<div class="currentPosition" style="display: none;"></div>
						<span class="field data" style=""></span>
						<input type="hidden" name="lx" />
						<input type="hidden" name="dm" />
					</div>
				</div>
			</div>
			<div class="block module">
				<span class="title">自由控件</span>
				<div class="text-module free">
					<div class="currentPosition" style="display: none;"></div>
					<span class="field free" style="">自定义文本</span>
				</div>
			</div>
			<div class="block background">
				<span class="title">背景</span>
				<label for="bg">选择文件</label>
				<form action="uploadBg.zf" role="form" id="bgForm" method="post" enctype="multipart/form-data">
					<input type="hidden" name="id" id="id" value="" />
					<input name="bg" id="bg" type="file">
				</form>
			</div>
			<div class="block shift">
				<div class="moveBox">
					<ul>
						<li class="leftTop to-top to-left">
							<img src="${base}/images/arrow.png"/>
						</li>
						<li class="top to-top">
							<img src="${base}/images/arrow.png"/>
						</li>
						<li class="rightTop to-top to-right">
							<img src="${base}/images/arrow.png"/>
						</li>
						<li class="left to-left">
							<img src="${base}/images/arrow.png"/>
						</li>
						<li class="center">
							<input type="text" id="shift" value=""/>
						</li>
						<li class="right to-right">
							<img src="${base}/images/arrow.png"/>
						</li>
						<li class="leftDown to-bottom to-left">
							<img src="${base}/images/arrow.png"/>
						</li>
						<li class="down to-bottom">
							<img src="${base}/images/arrow.png"/>
						</li>
						<li class="rightDown to-bottom to-right">
							<img src="${base}/images/arrow.png"/>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="edit-area">
			<div class="demo" style="background-image: url(${messageUtil("system.stylePath")}/assets/images/print/bg.jpg)">
				<div class="edit-block"></div>
			</div>
		</div>
	</body>
</html>