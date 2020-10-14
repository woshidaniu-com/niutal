[#assign zf=JspTaglibs["/woshidaniu-tags"] /]
<!DOCTYPE html>
	<style>
	
			.tab-cnt .item {
				display:inline-block;
				border:1px solid #ddd;
				margin:2px;
				padding:2px 5px;
			    background: #f0f0f0;
    			font-weight: 600;
    			font-size: 12px;
    			cursor:pointer;
			}
			.tab-cnt .item.active{background:#4a89dc;color:#fff;}
		
	</style>
<html>
<head>
	<script type="text/javascript">
		$(function(){
				var jsdmArr=[];
				$(document).off('click','.chose-tag .item').on('click','.chose-tag .item',function(){
					$(this).toggleClass("active");
					var jsdm = $(this).attr("jsdm");
					
					if($(this).hasClass('active') && $.inArray(jsdm,jsdmArr) == -1) {
						jsdmArr.push(jsdm);
					} else {
						jsdmArr.splice($.inArray(jsdm,jsdmArr),1);
					}
					$('#jsdms').val(jsdmArr.join(","));
				}); 
			});
	</script>
</head>
<body>
	<form action="saveYhxx.zf" data-toggle="validation"  data-widget='{"onkeyup": false}' role="form" class="form-horizontal sl_all_form"  id="ajaxForm" method="post" theme="simple">
		<input type="hidden" id="jsdms" name="jsdms" value=""/>  
		
		<div class="row">
			<div class="col-md-12 col-sm-12">
				<div class="form-group form-group-sm">
					<label for="" class="col-sm-2 control-label">
						<span class="red">*</span>用户名
					</label>
					<div class="col-sm-8">
						<input type="text" maxlength="20" name="zgh" id="zgh"  value="" class="form-control input-sm span2"  data-rules='{"required":true,"chrnum":true,"unique":["V0102"]}'/>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group form-group-sm">
					<label for="" class="col-sm-2 control-label">
						<span class="red">*</span>姓名
					</label>
					<div class="col-sm-8">
						<input type="text" maxlength="20" name="xm" id="xm" class="form-control  input-sm span3" data-rules='{"required":true}'/>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group form-group-sm">
					<label for="" class="col-sm-2 control-label"  for="dzyx">
						Email
					</label>
					<div class="col-sm-8">
						<input type="text" name="dzyx" id="dzyx" maxlength="40" data-rules='{"email2":true}'  class="form-control input-sm span4"/>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group form-group-sm">
					<label for="" class="col-sm-2 control-label" for="sjhm" >
						联系电话	
					</label>
					<div class="col-sm-8">
						<input type="text" name="lxdh" id="lxdh" maxlength="30" data-rules='{"mobile":true}'  class="form-control input-sm span5"/>
					</div>
				</div>
			</div>
			
			<div class="col-md-12 col-sm-12">
				<div class="form-group form-group-sm">
					<label for="" class="col-sm-2 control-label" for="sjhm" >
						<span class="red">*</span>部门	
					</label>
					<div class="col-sm-8">
						[@zf.select id="jgdm" name="jgdm" dataRules='{"required":true}' provider="bmdmService" listKey="bmdm_id" listValue="bmmc" cssClass="form-control input-sm chosen-select"/]
					</div>
				</div>
			</div>
			
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-sm-2 control-label">
						所属角色
					</label>
					<div class="col-sm-8">
						<div class="tab-cnt chose-tag" >
							[#list jsxxList as s]
								<span class="item" name="cbvjsxx" jsdm="${s.jsdm}">${s.jsmc}</span>
							[/#list] 
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group form-group-sm">
					<label for="" class="col-sm-2 control-label" for="sjhm" >
						初始密码
					</label>
					<div class="col-sm-8">
						${password!}
						<input type="hidden" name="mm" value="${password!}"/>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-sm-2 control-label">
						<span class="red">*</span>启用状态
					</label>
					<div class="col-sm-8">
							<label class="radio-inline">
								<input type="radio" style="cursor: pointer;" checked="checked" id="sfqy" name="sfqy" value="1"  /><span> 启 用 </span>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" style="cursor: pointer;" id="sfqy" name="sfqy" value="0"/><span> 停 用 </span>
							</label>
					</div>
				</div>
			</div>
			
			<!--用户类型字段-->
			[#if messageUtil("userManage.showUserType") == 'true']
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-sm-2 control-label">
						<span class="red">*</span>用户类型
					</label>
					<div class="col-sm-8">
							<label class="radio-inline">
								<input type="radio" style="cursor: pointer;" checked="checked" id="yhlx" name="yhlx" value="admin"  /><span> 管理员 </span>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" style="cursor: pointer;" id="yhlx" name="yhlx" value="teacher"/><span> 教师 </span>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" style="cursor: pointer;" id="yhlx" name="yhlx" value="student"/><span> 学生 </span>
							</label>
					</div>
				</div>
			</div>
			[/#if]
		</div>
	</form>
</body>
</html>