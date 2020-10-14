[#assign zf=JspTaglibs["/woshidaniu-tags"] /]
<!doctype html>
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
	<head>
		<script type="text/javascript">
			$(function(){
				var jsdmArr = $("#jsdms").val().split(",");
				$.each(jsdmArr,function(i,n){
					if (n != ""){
						$("span[name=cbvjsxx][jsdm="+n+"]").addClass("active");
					}
				});
				
				var jsdmArr=$("#jsdms").val()=="" ? [] : $("#jsdms").val().split(",");
				$(document).off('click','#choose_role_tags .item').on('click','#choose_role_tags .item',function(){
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
		<form  id="ajaxForm" action="modifyYhxx.zf" data-toggle="validation" role="form"  class="form-horizontal sl_all_form" method="post" theme="simple">
		    [#assign jsdms='']
		    [#list yhjsList as list]
		    	[#if list_index != 0]
		    		 [#assign jsdms=jsdms+',']
		    	[/#if]
		    	[#assign jsdms=jsdms+list.jsdm]
		    [/#list]
		    
		    <input type="hidden" id="jsdms" name="jsdms" value="${jsdms}"/>  
		    <input type="hidden" id="zgh" name="zgh" value="${model.zgh}"/>      
		    <div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							用户名
						</label>
						<div class="col-sm-8 ">
							<p class="form-control-static">${model.zgh }</p>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							<span class="red">*</span>姓名
						</label>
						<div class="col-sm-8">
							<input type="text" name="xm" id="xm" maxlength="20" class="form-control input-sm" value="${model.xm}" validate="{required:true}">
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label"  for="dzyx">
							Email
						</label>
						<div class="col-sm-8">
							<input type="text" name="dzyx" id="dzyx" maxlength="40" class="form-control input-sm" value="${model.dzyx}" validate="{email2:true}">
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm" >
						<label for="" class="col-sm-2 control-label" for="lxdh" >
							联系电话	
						</label>
						<div class="col-sm-8">
							<input type="text" name="lxdh" id="lxdh" maxlength="30" validate="{mobile:true}" value="${model.lxdh}"  class="form-control input-sm"/>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label" for="sjhm" >
							<span class="red">*</span>部门	
						</label>
						<div class="col-sm-8">
							[@zf.select id="jgdm" name="jgdm" defaultValue="${model.jgdm!}" dataRules='{"required":true}' provider="bmdmService" listKey="bmdm_id" listValue="bmmc" cssClass="form-control input-sm chosen-select"/]
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							所属角色
						</label>
						<div class="col-sm-8">
							<div class="tab-cnt" id="choose_role_tags">
								[#list jsxxList as s]
									<span class="item" name="cbvjsxx" jsdm="${s.jsdm}">${s.jsmc}</span>
								[/#list] 
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group">
						<label for="" class="col-sm-2 control-label">
							是否启用
						</label>
						<div class="col-sm-8">
							<label class="radio-inline">
								<input type="radio" style="cursor: pointer;" [#if model.sfqy==1]checked="checked"[/#if] id="sfqy" name="sfqy" value="1" /><span> 启 用 </span>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" style="cursor: pointer;" [#if model.sfqy==0]checked="checked"[/#if] id="sfqy" name="sfqy" value="0" /><span> 停 用 </span>
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
								<input type="radio" style="cursor: pointer;" [#if model.yhlx=='admin']checked="checked"[/#if] id="yhlx" name="yhlx" value="admin"  /><span> 管理员 </span>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" style="cursor: pointer;" [#if model.yhlx=='teacher']checked="checked"[/#if] id="yhlx" name="yhlx" value="teacher"/><span> 教师 </span>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" style="cursor: pointer;" [#if model.yhlx=='student']checked="checked"[/#if] id="yhlx" name="yhlx" value="student"/><span> 学生 </span>
							</label>
						</div>
					</div>
				</div>
				[/#if]
			</div>
		</form>
	</body>
	
</html>