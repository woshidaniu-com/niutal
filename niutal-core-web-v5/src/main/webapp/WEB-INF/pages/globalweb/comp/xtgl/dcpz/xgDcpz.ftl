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
				debugger;
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
		<form  id="ajaxForm" action="modifyDcpz.zf" data-toggle="validation" role="form"  class="form-horizontal sl_all_form" method="post" theme="simple">
		    <input type="hidden" id="jsdms" name="jsdms" value="${model.sqzStrList}"/>
		    <input type="hidden" id="dcclbh" name="dcclbh" value="${model.dcclbh}"/>      
		    <input type="hidden" id="zd" name="zd" value="${model.zd}"/>
		    <div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							配置编号
						</label>
						<div class="col-sm-8 ">
							<p class="form-control-static">${model.dcclbh }</p>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							配置名称
						</label>
						<div class="col-sm-8">
							<p class="form-control-static">${model.dcclmc }</p>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							字段代码
						</label>
						<div class="col-sm-8">
							<p class="form-control-static">${model.zd }</p>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							字段名称
						</label>
						<div class="col-sm-8">
							<p class="form-control-static">${model.zdmc }</p>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							显示顺序
						</label>
						<div class="col-sm-8">
							<p class="form-control-static">${model.xssx }</p>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							启用状态
						</label>
						<div class="col-sm-8">
							<p class="form-control-static">[#if model.zt==1]启用[/#if][#if model.zt==0]停用[/#if]</p>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							职工号
						</label>
						<div class="col-sm-8">
							<p class="form-control-static">${model.zgh }</p>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							是否默认字段
						</label>
						<div class="col-sm-8">
							<p class="form-control-static">[#if model.sfmrzd==1]是[/#if][#if model.sfmrzd==0]否[/#if]</p>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group form-group-sm">
						<label for="" class="col-sm-2 control-label">
							分配角色
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
			</div>
		</form>
	</body>
</html>