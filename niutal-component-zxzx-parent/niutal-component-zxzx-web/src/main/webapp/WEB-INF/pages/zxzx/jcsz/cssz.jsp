<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript">
			jQuery(function(){
				var kg = $("input:radio[name=kg]:checked").val();
				if(kg == "0"){
					$("#kfsj-tr").hide();
					$("#zxms-tr").hide();
				}
				
				$("input:radio[name=kg]").click(function(){
					if($("input:radio[name=kg]:checked").val() == "0"){
						$("#kfsj-tr").hide();
						$("#zxms-tr").hide();
					}else{
						$("#kfsj-tr").show();
						$("#zxms-tr").show();
					}
				});
				
				$("input:radio[name=rmzxjsfs]").click(function(){
					if($("input:radio[name=rmzxjsfs]:checked").val() == "1"){
						$("#rmzxjsfs-hyd-span").hide();
					}else{
						$("#rmzxjsfs-hyd-span").show();
					}
				});
				
				
				
			});
			
			function save(){
				//校验输入
				if($("input:radio[name=kg]:checked").length == 0){
					alertMessage('请设置开关！');
					return false;
				}
				
				if($("input:radio[name=kg]:checked").length == 1 && ($("input:text[name=kssj]").val() > $("input:text[name=jssj]").val())){
					alertMessage('开放时间段设置不正确！');
					return false;
				}
				
				if($("input:radio[name=kg]:checked").length == 1 && $("input:radio[name=zxms]:checked").length == 0){
					alertMessage('请选择咨询模式！');
					return false;
				}
				
				if($("input:radio[name=rmzxjsfs]:checked").length == 0){
					alertMessage('请选择热门咨询计算方式！');
					return false;
				}
				
				if($.trim($("input[name=rmzxxsts]").val()) == ''){
					alertMessage('请设置热门咨询显示条数！');
					return false;
				}else{
					if($.trim($("input[name=rmzxxsts]").val()).replace(/[^(\d]/g,'') != $.trim($("input[name=rmzxxsts]").val())){
						alertMessage('热门咨询显示条数字段必须为整数！');
						return false;
					}
				}
				
				ajaxSubFormWithFun("zxzxCsszForm","<%=systemPath%>/zxzx/jcsz_csszBc.html",{},function(data){
					alertInfo(data);
				});
			}
		</script>
	</head>
	<body>
		<div class="prompt">
			<h3>
				<span>参数设置说明：</span>
			</h3>
			<p>
				<font color="red">
				①开关设置，如果设置为关闭，开放时间段不起作用；
				</font>
			</p>
			<p>
				<font color="red">
				②登录咨询：需要用户登录后提交咨询信息，免登陆咨询：无需用户登录，在不登录的情况下为匿名方式，在登录的情况下取用户信息；
				</font>
			</p>
			<p>
				<font color="red">
				③热门咨询计算方式,总点击量代表发布之后到当前总的点击量排名，活跃度代表某段时间内点击量排名；
				</font>
			</p>
		</div>
		<s:form id="zxzxCsszForm" namespace="/zxzx" action="jcsz_csszBc" theme="simple">
			<div class="tab">
			<table class="formlist" width="93%">
				<thead>
					<tr>
						<th colspan="2">
							<span>基础设置<font color="#0f5dc2" style="font-weight: normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="2">
							<div class="btn">
								<button id="bnt0a" type="button" onclick="save()">
									<span>保 存</span>
								</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<tr id="kg-tr">
						<th width="20%">
							开关设置
						</th>
						<td>
							<s:radio name="kg" list="#{'1':'开启','0':'关闭'}"  theme="simple"></s:radio>
						</td>
					</tr>
					
					<tr id="kfsj-tr" style="display:<s:property value='kg=="0" ? "none":""'/>">
						<th>
							开放时间段
						</th>
						<td>
							<s:textfield id="kssj-text" class="Wdate" name="kssj" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\\'jssj-text\\')}'})" theme="simple"></s:textfield> 
							- 
							<s:textfield id="jssj-text" class="Wdate" name="jssj" onfocus="WdatePicker({minDate:'#F{$dp.$D(\\'kssj-text\\')}'})" theme="simple"></s:textfield>
						</td>
					</tr>
					<tr id="zxms-tr" style="display:<s:property value='kg=="0" ? "none":""'/>">
						<th>
							咨询模式
						</th>
						<td>
							<s:radio name="zxms" list="#{'1':'登录咨询','0':'免登录咨询'}"  theme="simple"></s:radio>
						</td>
					</tr>
					
					<tr id="rezxjsfs-tr" style="display:<s:property value='kg=="0" ? "none":""'/>">
						<th>
						 热门咨询计算方式
						</th>
						<td>
							<s:radio id="rmzxjsfs" name="rmzxjsfs" list="#{'1':'总点击量','2':'活跃度'}"  theme="simple"></s:radio>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<span id="rmzxjsfs-hyd-span" style="display:<s:property value='rmzxjsfs=="1" ? "none":""'/>">最近<s:select name="rmzxjssjd" list="#{'1':'1个月','2':'2个月','3':'3个月','4':'4个月','5':'5个月','6':'6个月','7':'7个月','8':'8个月','9':'9个月','10':'10个月','11':'11个月','12':'12个月'}"  theme="simple"></s:select></span>
						</td>
					</tr>
					
					<tr id="rezxxsts-tr" style="display:<s:property value='kg=="0" ? "none":""'/>">
						<th>
						 热门咨询显示条数
						</th>
						<td>
							<s:textfield id="rmzxxsts" name="rmzxxsts" maxLength="3" cssStyle="width:30px;text-align:right" ></s:textfield>
							条
						</td>
					</tr>
				</tbody>
				<thead>
					<tr>
						<th colspan="2">
							<span>联系方式<font color="#0f5dc2" style="font-weight: normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tbody>
					<tr id="lxr-tr">
						<th width="20%">
							联系人
						</th>
						<td>
							<s:textfield id="lxr" name="lxr" maxlength="50"></s:textfield>
						</td>
					</tr>
					<tr id="dh-tr">
						<th width="20%">
							电话
						</th>
						<td>
							<s:textfield id="dh" name="dh" maxlength="50"></s:textfield>
						</td>
					</tr>
					<tr id="dzyx-tr">
						<th width="20%">
							电子邮箱
						</th>
						<td>
							<s:textfield id="dzyx" name="dzyx" maxlength="200" cssStyle="width:300px"></s:textfield>
						</td>
					</tr>
					<tr id="dz-tr">
						<th width="20%">
							地址
						</th>
						<td>
							<s:textfield id="dz" name="dz" maxlength="500" cssStyle="width:300px"></s:textfield>
						</td>
					</tr>
				</tbody>
			</table>
			</div>
		</s:form>
	</body>
</html>
