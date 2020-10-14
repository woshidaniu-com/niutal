 <!--
<ul class="nav nav-tabs nav-justified" role="tablist">
	[#list wjffList as item]
		<li role="presentation" [#if item_index ==0]class="active"[/#if]>
			<a href="#${item.dxid}" aria-controls="${item.dxid}" role="tab" data-toggle="tab">${item.dxmc}</a>
		</li>
	[/#list]
 </ul>

 <form class="form-horizontal sl_all_form" role="form">
	 <div class="tab-content">
 		[#list wjffList as item]
 			<div class="tab-pane row  [#if item_index ==0]active[/#if]" id="${item.dxid}"  role="tabpanel">
 				[#list item.tjtjs as tj]
 					<div class="col-md-4 col-sm-6">
					    <div class="form-group">
					      <label for="" class="col-sm-4 control-label">${tj.name}</label>
					      <div class="col-sm-8">
						        <select class="form-control" name="${tj.id}" id="${item.dxid}_${tj.id}">
						        	<option value="">--请选择--</option>
						        	 [#assign key="${tj.key!}"]
									 [#assign value="${tj.value!}"]
							         [#list tj.itemList as option]
							         	<option value="${option[key]!}">${option[value]!}</option>
							         [/#list]
						        </select>
						        <script type="text/javascript">
						        	$("#${item.dxid}_${tj.id}").chosen();
						        </script>
					      </div>
					    </div>
				 	</div>
 				[/#list]
			</div>
 		[/#list]
	</div>
</form>

<div class="row sl_aff_btn">
  <div class="col-sm-12">
    <button type="button" class="btn btn-primary btn-sm">查询</button>
  </div>
</div>
-->
[#assign stNo = 0]
[#list stglList as stgl]
	[#if stgl.stlx == '1' ||  stgl.stlx == '2' ||  stgl.stlx == '3' ||  stgl.stlx == '4' || stgl.stlx == '5']
		[#assign stNo = stNo + 1]
	[/#if]
	[#if stgl.stlx == '1' ||  stgl.stlx == '2' ||  stgl.stlx == '3' ||  stgl.stlx == '4']
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">${stNo}、${stgl.stmc}</h3>
			</div>
			<div class="panel-body table-responsive" style="display: block;">
				<table class="table table-hover">
					<thead>
						<tr class="active">
							<th style="color: #7c8d87 !important;">选项</th>
							<th style="color: #7c8d87 !important;">人数</th>
							<th style="color: #7c8d87 !important;">比例</th>
						</tr>
					</thead>
					<tbody>
						[#list wjtjList as wjtj]
							[#if stgl.stid == wjtj.STID]
								<tr>
									<td>${wjtj.XXMC}</td>
									<td>${wjtj.HDS}</td>
									<td>${wjtj.HDL}%</td>
								</tr>
							[/#if]
						[/#list]
					</tbody>
				</table>
			</div>			
		</div>
	[/#if]
[/#list]