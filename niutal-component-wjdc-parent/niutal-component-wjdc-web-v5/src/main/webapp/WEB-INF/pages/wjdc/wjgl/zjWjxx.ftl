[#assign zf=JspTaglibs["/woshidaniu-tags"] /]
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">		
		<style type="text/css">
			.create-question-cont {
				padding: 20px 0px;
				overflow: hidden;
			}
			
			.create-question-cont ul {
				padding-left: 0px;
			}
			
			.create-question-cont ul li {
				margin-bottom: 30px;
				overflow: hidden;
			}
			
			.create-question-cont ul li .name,
			.create-question-cont ul li .start-date,
			.create-question-cont ul li .total-days {
				margin-top: 5px;
			}
			
			.create-question-cont ul li .name span {
				margin-left: 5px;
			}
			
			.create-question-cont ul li .start-date>.to {
				margin-left: 10px;
			}
			
			.create-question-cont ul li:first-child input {
				width: 95%;
			}
			
			.create-question-cont .choose-template {
				margin-bottom: 20px;
				overflow: hidden;
			}
			
			.create-question-cont .choose-template a {
				position: relative;
				display: block;
				width: 85%;
			}
			
			.create-question-cont .choose-template a .fa-check-square {
				position: absolute;
				top: -3px;
				right: -1px;
				color: #337ab7;
				font-size: 24px;
				display: none;
			}
			
			.create-question-cont .choose-template a:hover,
			.create-question-cont .choose-template a:link,
			.create-question-cont .choose-template a:visited {
				text-decoration: none;
			}
			
			.create-question-cont .choose-template .template-bg {
				background-color: #eee;
				padding: 20px 0px;
			}
			
			.create-question-cont .choose-template .template-bg>.fa {
				color: #888;
				font-size: 45px;
			}
			
			.create-question-cont .choose-template .template-name {
				background-color: #bbb;
				padding: 10px 0px;
				color: #fff;
			}
			
			.create-question-cont .choose-template a.active {
				border: 2px solid #337ab7;
			}
			
			.create-question-cont .choose-template a.active .template-bg>.fa {
				color: #337ab7;
			}
			
			.create-question-cont .choose-template a.active .template-name {
				background-color: #337ab7;
			}
			
			.create-question-cont .choose-template a.active .fa-check-square {
				display: block;
			}
			
			.create-question-cont .yes-no input[type="checkbox"] {
				width: 15px;
				height: 15px;
				vertical-align: middle;
				margin: 0px 8px 0px;
			}
		</style>
	</head>
	<body>
	<form class="form-horizontal" role="form" data-toggle="validation" action="zjBcWjxx.zf" id="ajaxForm" method="post">
		<input type="hidden" value="${wjid}" name="wjid" id="wjid"/>
		<div class="create-question-cont clearfix">
			<div class="row">
				<div class="col-md-12 col-sm-12 form-group form-group-sm">
					<div class="name col-lg-2 col-md-2 col-sm-2 col-xs-12 text-right"><label class="red">*</label><span>问卷名称</span></div>
					<div class="question-name-input col-lg-9 col-md-9 col-sm-9 col-xs-12">
						<input type="text" maxlength="20" name="wjmc" id="wjmc"  value="" class="form-control input-sm span2"  data-rules='{"required":true}'/>
					</div>
				</div>
			</div>
			[#if enableSjyxj = 'true']
			<div class="row">
				<div class="col-md-12 col-sm-12 form-group form-group-sm">
					<div class="name col-lg-2 col-md-2 col-sm-2 col-xs-12 text-right"><label class="red">*</label><span>问卷优先级</span></div>
					<div class="question-name-input col-lg-9 col-md-9 col-sm-9 col-xs-12">
						<input type="text" max="999" min="1" digits="true" name="wjyxj" id="wjyxj"  value="1" class="form-control input-sm span2"  data-rules='{"required":true}'/>
					</div>
				</div>
			</div>
			[/#if]
			<div class="row">
				<div class="col-md-12 col-sm-12 form-group form-group-sm">
					<div class="name col-lg-2 col-md-2 col-sm-2 col-xs-12 text-right">有效日期</span></div>
					<div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
						<div class="start-date col-lg-3 col-md-3 col-sm-3 col-xs-5 padding-lr0"><span>2017-06-01</span><span class="to">至</span></div>
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-7 padding-lr0">
							<input type="text" maxlength="20" name="gqsj" id="gqsj"  placeholder="不设置表示该问卷不会过期"
								readonly="true"  class="form-control input-sm span2"  onfocus="laydate({format:'YYYYMMDD'})"/>
						</div>
					</div>
				</div>
			</div>		
			<div class="row">
				<div class="col-md-12 col-sm-12 form-group form-group-sm">
					<div class="name col-lg-2 col-md-2 col-sm-2 col-xs-12 text-right">卷首语</span></div>
					<div class="question-name-input col-lg-9 col-md-9 col-sm-9 col-xs-12">
						<textarea name="jsy" id="jsy" class="form-control " style="width:100%;height:50px" >${model.jsy}</textarea>
					</div>
				</div>
			</div>	
			<div class="row">
				<div class="col-md-12 col-sm-12 form-group form-group-sm">
					<div class="name col-lg-2 col-md-2 col-sm-2 col-xs-12 text-right">卷尾语</span></div>
					<div class="question-name-input col-lg-9 col-md-9 col-sm-9 col-xs-12">
						<textarea name="jwy" id="jwy" class="form-control "  style="width:100%;height:50px" >${model.jwy}</textarea>
					</div>
				</div>
			</div>
			<div class="row" style="display:none;">
				<div class="col-md-12 col-sm-12 form-group form-group-sm">
						<div class="name col-lg-2 col-md-2 col-sm-2 col-xs-12 text-right"><label class="red">*</label><span>问卷模板</span></div>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 padding-lr0">
							<div class="choose-template">
								<div class="text-center col-lg-6 col-md-6 col-sm-6 col-xs-6">
									<a href="#" class="active">
										<div class="template-bg"><i class="fa fa-file-o"></i></div>
										<div class="template-name">空白模板</div>
										<i class="fa fa-check-square"></i>
									</a>
								</div>
								<div class="text-center col-md-6 col-sm-6 col-lg-6 col-md-6 col-sm-6 col-xs-6">
									<a href="#">
										<div class="template-bg"><i class="fa fa-file-text"></i></div>
										<div class="template-name">样例模板</div>
										<i class="fa fa-check-square"></i>
									</a>
								</div>
							</div>
							<!-- <div class="yes-no"><span>是否匿名答卷</span><input type="checkbox" name="fblx" value="1"></div> -->
						</div>
					</div>
				</div>
			</div>
		</form>
	</body>
	<script type="text/javascript">
		$(function(){
			//选择模板
			$(".create-question-cont .choose-template a").click(function() {
				$(this).parents('.choose-template').find('a').removeClass('active');
				$(this).addClass('active');
			});
			KindEditor.create('textarea[name="jsy"]', simpleOption);
			KindEditor.create('textarea[name="jwy"]', simpleOption);
		});
	</script>
</html>