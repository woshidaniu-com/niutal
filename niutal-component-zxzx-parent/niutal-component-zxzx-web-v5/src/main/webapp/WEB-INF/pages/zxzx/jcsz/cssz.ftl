<!doctype html>
<html lang="zh-CN">
	<head>
		[#include "/globalweb/head/niutal-ui-laydate.ftl" /]
	</head>
	<body>
		<div class="alert alert-dark" role="alert">
  			<strong>参数设置说明</strong>
			<p>
				①开关设置，如果设置为关闭，开放时间段不起作用；
			</p>
			<p>
				②登录咨询：需要用户登录后提交咨询信息，免登陆咨询：无需用户登录，在不登录的情况下为匿名方式，在登录的情况下取用户信息；
			</p>
		</div>
		<!--操作按钮 开始 -->
		[#include "/globalweb/comm/buttons.ftl" /]
		<!--操作按钮 结束  -->
		<form id="zxzxCsszForm" method="post" role="form" action="csszBc.zf" class="form-horizontal sl_all_form" 
				data-toggle="validation" >
				<div class="row">
					<div class="col-md-5 col-sm-6">
						<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							开关设置
						</label>
						<div class="col-sm-8">
							<label class="radio-inline" for="kg_1">
								<input id="kg_1" type="radio" name="kg" value="1" checked></input>
								<span class="label label-success">开启</span>
							</label>
							<label class="radio-inline" for="kg_0">
								<input id="kg_0" type="radio" name="kg" value="0"></input>
								<span class="label label-danger">关闭</span>
							</label>
						</div>
						</div>
					</div>
					
					<div class="col-md-5 col-sm-6">
					  	<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							开放时间段
						</label>
						<div class="col-sm-8">
							<input type="text" id="kssj-text" 
								placeholder="开始日期"  
								class="form-control form-control laydate-icon-zfcolor" 
								name="kssj" onfocus="laydate({format:'YYYY-MM-DD'})" 
								style="display:inline-block"></input> 
							-
							<input type="text" id="jssj-text"
								placeholder="结束日期" 
								class="form-control form-control laydate-icon-zfcolor" 
								name="jssj" onfocus="laydate({format:'YYYY-MM-DD'})" 
								style="display:inline-block""></input>
							
						</div>
						</div>
					</div>
					
				</div>
			
				<div class="row">
					<div class="col-md-5 col-sm-6">
						<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							是否前台显示'我的提问'栏目
						</label>
						<div class="col-sm-8">
							<label class="radio-inline" for="sfxswdwt_1">
								<input id="sfxswdwt_1" type="radio" name="sfxswdwt" value="1" checked></input>是
							</label>
							<label class="radio-inline" for="sfxswdwt_0">
								<input id="sfxswdwt_0" type="radio" name="sfxswdwt" value="0"></input>否
							</label>
						</div>
						</div>
					</div>
					<div class="col-md-5 col-sm-6">
						<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							是否前台显示'我要提问'栏目
						</label>
						<div class="col-sm-8">
							<label class="radio-inline" for="sfxswytw_1">
								<input id="sfxswytw_1" type="radio" name="sfxswytw" value="1" checked></input>是
							</label>
							<label class="radio-inline" for="sfxswytw_0">
								<input id="sfxswytw_0" type="radio" name="sfxswytw" value="0"></input>否
							</label>
						</div>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-5 col-sm-6">
						<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							是否允许前台未登录可查询问题
						</label>
						<div class="col-sm-8">
							<label class="radio-inline" for="sfyxwdlcxwt_1">
								<input id="sfyxwdlcxwt_1" type="radio" name="sfyxwdlcxwt" value="1" checked></input>是
							</label>
							<label class="radio-inline" for="sfyxwdlcxwt_0">
								<input id="sfyxwdlcxwt_0" type="radio" name="sfyxwdlcxwt" value="0"></input>否
							</label>
						</div>
						</div>
					</div>
				</div>
			
				<div class="row">
					<div class="col-md-5 col-sm-6">
						<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							咨询模式
						</label>
						<div class="col-sm-8">
							<label class="radio-inline" for="zxms_1">
								<input id="zxms_1" type="radio" name="zxms" value="1" checked></input>登录咨询
							</label>
							<label class="radio-inline" for="zxms_0">
								<input id="zxms_0" type="radio" name="zxms" value="0"></input>免登录咨询
							</label>
						</div>
						</div>
					</div>
					<div class="col-md-5 col-sm-6">
						<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							登陆模式
						</label>
						<div class="col-sm-8">
							<label class="radio-inline" for="dlms_0">
								<input id="dlms_0" type="radio" name="dlms" value="default" checked></input>打开新窗口（默认）
							</label>
							<label class="radio-inline" for="dlms_1">
								<input id="dlms_1" type="radio" name="dlms" value="embend"></input>嵌入
							</label>
						</div>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-5 col-sm-6">
						<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							 登陆路径
						</label>
						<div class="col-sm-8">
							<input type="text" id="login_url" name="loginUrl" class="form-control"></input>
						</div>
						</div>
					</div>
					<div class="col-md-5 col-sm-6 hidden">
						<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							 认证路径
						</label>
						<div class="col-sm-8">
							<input type="text" id="auth_url" name="authUrl" class="form-control"></input>
						</div>
						</div>
					</div>
				</div>
				
				
				<div class="row hidden">
					<div class="col-md-5 col-sm-6">
						<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							热门咨询计算方式
						</label>
						<div class="col-sm-8">
							<label class="radio-inline" for="rmzxjsfs_1">
								<input id="rmzxjsfs_1" type="radio" name="rmzxjsfs" value="1" checked></input>总点击量
							</label>
							<label class="radio-inline" for="rmzxjsfs_2">
								<input id="rmzxjsfs_2" type="radio" name="rmzxjsfs" value="2"></input>活跃度
							</label>
						</div>
						</div>
					</div>
					
					<div class="col-md-5 col-sm-6">
						<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							活跃度计算时间（最近）
						</label>
						<div class="col-sm-8">
							<select id="rmzxjssjd" name="rmzxjssjd" class="form-control">
								<option value="1">1个月</option>
								<option value="2">2个月</option>
								<option value="3">3个月</option>
								<option value="4">4个月</option>
								<option value="5">5个月</option>
								<option value="6">6个月</option>
								<option value="7">7个月</option>
								<option value="8">8个月</option>
								<option value="9">9个月</option>
								<option value="10">10个月</option>
								<option value="11">11个月</option>
								<option value="12">12个月</option>
							</select>
						</div>
						</div>
					</div>
				</div>
			
				<div class="row hidden">
					<div class="col-md-5 col-sm-6">
						<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							 热门咨询显示数量（条）
						</label>
						<div class="col-sm-8">
							<input type="text" id="rmzxxsts" name="rmzxxsts" maxLength="3" class="form-control"></input>
						</div>
						</div>
					</div>
					
				</div>
				
				<div class="row hidden">
					<div class="col-md-5 col-sm-6">
						<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							 联系人
						</label>
						<div class="col-sm-8">
							<input type="text" id="lxr" class="form-control" name="lxr" maxlength="50"></input>
						</div>
						</div>
					</div>
					
					<div class="col-md-5 col-sm-6">
						<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							 电话
						</label>
						<div class="col-sm-8">
							<input type="text" id="dh" class="form-control" name="dh" maxlength="50" ></input>
						</div>
						</div>
					</div>
					
					<div class="col-md-5 col-sm-6">
						<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							电子邮箱
						</label>
						<div class="col-sm-8">
							<input type="text" id="dzyx" class="form-control" name="dzyx" maxlength="200"></input>
						</div>
						</div>
					</div>
					
					<div class="col-md-5 col-sm-6">
						<div class="form-group">
						<label for="" class="col-sm-4 control-label">
							地址
						</label>
						<div class="col-sm-8">
							<input type="text" id="dz" class="form-control" name="dz" maxlength="500"></input>
						</div>
						</div>
					</div>
				</div>
				
		</form>
		
		[#include "/globalweb/head/niutal-ui-laydate.ftl" /]
		<script type="text/javascript">
			jQuery(function(){
				var kg ='${model.kg}';
				var kssj = '${model.kssj}';
				var jssj = '${model.jssj}';
				var zxms = '${model.zxms}';
				var dlms = '${model.dlms}';
				var login_url = '${model.loginUrl}';
				var auth_url = '${model.authUrl}';
				var rmzxjsfs = '${model.rmzxjsfs}';
				var rmzxjssjd = '${model.rmzxjssjd}';
				var rmzxxsts = '${model.rmzxxsts}';
				var lxr = '${model.lxr}';
				var dh = '${model.dh}';
				var dzyx = '${model.dzyx}';
				var dz = '${model.dz}';
				
				var sfxswdwt = '${model.sfxswdwt}';
				var sfxswytw = '${model.sfxswytw}'; 
				var sfyxwdlcxwt = '${model.sfyxwdlcxwt}'; 
				
				$('input:radio[name="sfxswdwt"][value="' + sfxswdwt + '"]').attr('checked','true');
				$('input:radio[name="sfxswytw"][value="' + sfxswytw + '"]').attr('checked','true');
				$('input:radio[name="sfyxwdlcxwt"][value="' + sfyxwdlcxwt + '"]').attr('checked','true');

				$('input:radio[name="kg"][value="' + kg + '"]').attr('checked','true');
				$('#kssj-text').val(kssj);
				$('#jssj-text').val(jssj);
				$('input:radio[name="zxms"][value="' + zxms + '"]').attr('checked','true');
				$('input:radio[name="dlms"][value="' + dlms + '"]').attr('checked','true');
				$('#login_url').val(login_url);
				$('#auth_url').val(auth_url);
				$('input:radio[name="rmzxjsfs"][value="' + rmzxjsfs + '"]').attr('checked','true');
				$('#rmzxjssjd').val(rmzxjssjd);
				$('#rmzxxsts').val(rmzxxsts);
				$('#lxr').val(lxr);
				$('#dh').val(dh);
				$('#dzyx').val(dzyx);
				$('#dz').val(dz);
				if(kg == "0"){
					$("#kssj-text, #jssj-text").attr("disabled", "disabled");
					$("#zxms_0, #zxms_1").attr("disabled", "disabled");
				}
				if(rmzxjsfs == "1"){
					$("#rmzxjssjd").attr("disabled", "disabled");
				}
				
				$("input:radio[name=kg]").click(function(){
					if($("input:radio[name=kg]:checked").val() == "0"){
						$("#kssj-text, #jssj-text").attr("disabled", "disabled");
						$("#zxms_0, #zxms_1").attr("disabled", "disabled");
					}else{
						$("#kssj-text, #jssj-text").removeAttr("disabled");
						$("#zxms_0, #zxms_1").removeAttr("disabled");
					}
				});
				
				$("input:radio[name=rmzxjsfs]").click(function(){
					if($("input:radio[name=rmzxjsfs]:checked").val() == "1"){
						$("#rmzxjssjd").attr("disabled", "disabled");
					}else{
						$("#rmzxjssjd").removeAttr("disabled");
					}
				});
				
				$('#btn_bc').click(function(){
					save();
				});
				
				
				function save(){
				//校验输入
				if($("input:radio[name=kg]:checked").length == 0){
					$.alert('请设置开关！');
					return false;
				}
				
				if($("input:radio[name=kg]:checked").length == 1 && ($("input:text[name=kssj]").val() > $("input:text[name=jssj]").val())){
					$.alert('开放时间段设置不正确！');
					return false;
				}
				
				if($("input:radio[name=kg]:checked").length == 1 && $("input:radio[name=zxms]:checked").length == 0){
					$.alert('请选择咨询模式！');
					return false;
				}
				
// 				if($("input:radio[name=rmzxjsfs]:checked").length == 0){
// 					$.alert('请选择热门咨询计算方式！');
// 					return false;
// 				}
				
// 				if($.trim($("input[name=rmzxxsts]").val()) == ''){
// 					$.alert('请设置热门咨询显示条数！');
// 					return false;
// 				}else{
// 					if($.trim($("input[name=rmzxxsts]").val()).replace(/[^(\d]/g,'') != $.trim($("input[name=rmzxxsts]").val())){
// 						$.alert('热门咨询显示条数字段必须为整数！');
// 						return false;
// 					}
// 				}
				
				submitForm("zxzxCsszForm",function(responseText,statusText){
					if(responseText["status"] == "success"){
						$.success(responseText["message"],function() {
						});
					}else if(responseText["status"] == "fail"){
						$.error(responseText["message"],function() {
						});
					} else{
						$.alert(responseText["message"],function() {
						});
					}
				});
			}
			});
		</script>
	</body>
</html>
