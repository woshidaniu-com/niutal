<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<meta name="decorator" content="default"/>

	[#include "/globalweb/head/niutal-ui-echarts.ftl" /]
	<!--WebSocket插件-->
	[#include "/globalweb/head/niutal-ui-sockjs.ftl" /]
	<script type="text/javascript" src="${base}/js/monitor/wacth.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	<style type="text/css">
	.input-group-addon {
	    background-color: #FFF;
	    border-left: 0px;
	}
	
	.panel .table {
		margin-bottom: 0;
	}
	.table > tbody > tr > td, 
	.table > tbody > tr > th, 
	.table > tfoot > tr > td, 
	.table > tfoot > tr > th, 
	.table > thead > tr > td, 
	.table > thead > tr > th {
		padding: 2px;
		border: 0px solid transparent !important;
	}
	
	.td-left{width:120px;}
	.td-left-val{width: 40%;}
	</style>
</head>
<body class="" style="">


	
	<!-- Nav tabs -->
	<ul class="nav nav-tabs sl_nav_tabs" role="tablist"  >
		<li class="active">
			<a href="#home" role="tab" data-toggle="tab">实时监控</a>
		</li>
		<li>
			<a href="#profile" role="tab" data-toggle="tab">服务器信息</a>
		</li>
		<li>
			<a href="#threshold" role="tab" data-toggle="tab">预警设置</a>
		</li>
	</ul>
	<!-- Tab panes -->
	<div class="tab-content">
		<div class="tab-pane active padding-t10" id="home">
			<div class="row">
				<div class="col-sm-12 col-md-12">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<i class="glyphicon glyphicon-time "></i> 实时监控
						</div>
						<div class="panel-body">
							<div class="col-sm-4 col-md-4"><div id="jvm_chart" style="height: 240px;"></div></div>
							<div class="col-sm-4 col-md-4"><div id="cpu_chart" style="height: 240px;"></div></div>
							<div class="col-sm-4 col-md-4"><div id="ram_chart" style="height: 240px;"></div></div>
						</div>
					</div>
				</div>
				<div class="col-sm-12 col-md-12">
					<div class="panel panel-info">
						<div class="panel-heading">
							<i class="fa fa-th-list"></i> 使用量监控（最近10秒钟内存使用情况采样）
						</div>
						<div class="panel-body" style="padding: 0px">
							<div class="col-sm-4 col-md-4"><div id="jvm_memory" style="height: 240px;"></div></div>
							<div class="col-sm-4 col-md-4"><div id="ram_memory" style="height: 240px;"></div></div>
							<div class="col-sm-4 col-md-4"><div id="swap_memory" style="height: 240px;"></div></div>
						</div>
					</div>
				</div>
				<div class="col-sm-12 col-md-12">
				  	<div class="panel panel-danger">
						<div class="panel-heading">
							<i class="fa fa-fire"></i> 使用率监控
						</div>
						<div class="panel-body">
							<div id="usage_chart" style="height: 300px;"></div>
						</div>
					</div>
				</div>
				
			</div>
		</div>
		<div class="tab-pane padding-t10" id="profile">
			 [#include "/monitor/watch/jvm.ftl" /]
		</div>
		<div class="tab-pane padding-t10" id="threshold">
			
			<div class="row animated fadeInRight">
				<div class="col-sm-6">
				   <div class="panel panel-danger">
						<div class="panel-heading">
							<i class="fa fa-briefcase"></i> 警告设置
						</div>
						<form class="form-horizontal" id="thresholdForm" method="post" action="${base}/monitor/watch/threshold.zf">
							<table class="table table-striped table-bordered table-hover" width="100%" style="vertical-align: middle;">
								<thead>
									<tr style="background-color: #faebcc; text-align: center;">
										<td width="100">监控对象</td>
										<td width="100">使用率</td>
										<td width="200">预警阈值</td>
									</tr>
								</thead>
								<tbody id="tbody">
									<tr>
										<td style='padding-left: 10px; text-align: left;vertical-align: middle;'>服务器CPU</td>
										<td style='padding-left: 10px; text-align: center;vertical-align: middle;'>
											<span id="td_cpuUsage" style="color: red;">50</span>%
										</td>
										<td>
											<div class="form-group">
											    <div class="input-group input-group-sm">
											      <input type='text' class="form-control input-sm" name='cpu' id='cpu' validate="{required:true,digits:true}" value='${props["watch.cup.threshold"]}' data-toggle="float"  placeholder="CPU使用率"/> 
											      <div class="input-group-addon">%</div>
											      <span class="input-group-btn">
											         <button class="btn btn-sm btn-default btn-watch" type="button" data-key="watch.cup.threshold" data-href="#cpu">修改</button>
											      </span>
											    </div>
											</div>
										</td>
									</tr>
									<tr>
										<td style='padding-left: 10px; text-align: left;vertical-align: middle;'>服务器内存</td>
										<td style='padding-left: 10px; text-align: center;vertical-align: middle;'>
											<span id="td_serverUsage" style="color: blue;">50</span>%
										</td>
										<td>
											<div class="form-group">
											    <div class="input-group input-group-sm">
											      <input type='text' class="form-control input-sm" name='ram' id='ram' validate="{required:true,digits:true}" value='${props["watch.ram.threshold"]}' data-toggle="float" placeholder="内存使用率"/> 
											      <div class="input-group-addon">%</div>
											      <span class="input-group-btn">
											         <button class="btn btn-sm btn-default btn-watch" type="button" data-key="watch.ram.threshold" data-href="#ram">修改</button>
											      </span>
											    </div>
											</div>
										</td>
									</tr>
									<tr>
										<td style='padding-left: 10px; text-align: left;vertical-align: middle;'>JVM内存</td>
										<td style='padding-left: 10px; text-align: center;vertical-align: middle;'>
											<span id="td_jvmUsage" style="color: green;">50</span>%
										</td>
										<td>
											<div class="form-group">
											    <div class="input-group input-group-sm">
											      <input type='text' class="form-control input-sm" name='jvm' id='jvm' validate="{required:true,digits:true}" value='${props["watch.jvm.threshold"]}' data-toggle="float"  placeholder="JVM使用率"/> 
											      <div class="input-group-addon">%</div>
											      <span class="input-group-btn">
											         <button class="btn btn-sm btn-default btn-watch" type="button" data-key="watch.jvm.threshold" data-href="#jvm">修改</button>
											      </span>
											    </div>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
				</div>
				<div class="col-sm-6">
				   <div class="panel panel-success">
						<div class="panel-heading">
							<i class="fa fa-briefcase"></i> 通知方式
						</div>
						<div class="panel-body">
							<form class="form-horizontal" id="ajaxForm" method="post" action="${base}/monitor/watch/notice.zf">
								 <!-- 防止浏览器自动填充:增加 class="ignore"忽略校验-->
								 <input type="text" class="ignore" style="display: none;" /> 
								 <input type="password" class="ignore" style="display: none;" />
								 <div class="form-group">  
									<label for="inputEmail" class="col-sm-2 control-label">预警服务状态：</label>
								    <div class="col-sm-10">
								    	<label class="radio-inline">
										  	<input type="radio" name="watch.notice.status" validate="{required:true}" id="noticeStatus1" value="on" [#if props["watch.notice.status"]?contains("on")]checked="checked"[/#if]/> 开启
										</label>
										<label class="radio-inline">
										  	<input type="radio" name="watch.notice.status" validate="{required:true}" id="noticeStatus2" value="off" [#if props["watch.notice.status"]?contains("off")]checked="checked"[/#if]/> 关闭
										</label>
								    </div>
								 </div>
								 <div class="form-group">  
									<label for="inputEmail" class="col-sm-2 control-label">预警周期：</label>
								    <div class="col-sm-10">
								       <input type="text" class="form-control" name="watch.notice.period" validate="{required:true,pattern:'^([1-9][0-9]*\.?[0-9]*)\s*([smhd]{1})$'}" value="${props["watch.notice.period"]}" id="inputPeriod" placeholder="指定多少时间内进行一次预警通知；格式如：1s、1m、1h、1d；分别表示：1秒、1分钟、1小时、1天"/>
								    </div>
								 </div>
								 <div class="form-group">
								    <label class="col-sm-2 control-label">预警信息通知方式：</label>
								    <div class="col-sm-10">
								    	<label class="checkbox-inline">
										  	<input type="checkbox" name="noticeType" validate="{required:true}" id="noticeType1" value="email" [#if props["watch.notice.type"]?contains("email")]checked="checked"[/#if]/> 电子邮件
										</label>
										<label class="checkbox-inline">
										  	<input type="checkbox" name="noticeType" validate="{required:true}" id="noticeType2" value="sms" [#if props["watch.notice.type"]?contains("sms")]checked="checked"[/#if]/> 手机短信
										</label>
										<label class="checkbox-inline">
										  	<input type="checkbox" name="noticeType" validate="{required:true}" id="noticeType3" value="app" [#if props["watch.notice.type"]?contains("app")]checked="checked"[/#if]/> 移动校园消息推送（待实现）
										</label>
								    </div>  
								 </div> 
								 <div class="form-group">  
									<label for="inputEmail" class="col-sm-2 control-label">预警信息接收邮件：</label>
								    <div class="col-sm-10">
								       <input type="text" class="form-control" name="watch.mail.to" validate="{required:true,email2:true}" value="${props["watch.mail.to"]}" id="inputEmail" placeholder="邮箱账户名;如：woshidaniu@163.com。该预留邮箱将在开启了邮件通知的情况下接收到预警邮件。"/>
								    </div>
								 </div>
								 <div class="form-group">
								    <label for="inputSms" class="col-sm-2 control-label">预警短信接收号码：</label>
								    <div class="col-sm-10">
								      <input type="text" class="form-control" name="watch.sms.to" validate="{required:true,phone:true}"  value="${props["watch.sms.to"]}" id="inputSms" placeholder="该预留号码将在开启了短信通知的情况下接收到预警短信。"/>
								    </div>
								 </div>
								 <div class="form-group">
								    <div class="col-sm-offset-2 col-sm-10">
								      <button type="button" class="btn btn-default btn-notice"> 修改 </button>
								    </div>
								 </div>
							</form>
				  		</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	[#include "/globalweb/head/niutal-ui-validation.ftl" /]
</body>
</html>
