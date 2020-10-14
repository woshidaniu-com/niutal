<div class="row sl_add_btn" id="but_ancd">
    <div class="col-sm-12">
      	<!-- 加载当前菜单栏目下操作     -->
	      	[@buttonsUtil gnmkdm = "${gnmkdm}"]
				<div class="btn-toolbar" role="toolbar" style="float:right;">
					<div class="btn-group" >
						[#list ancdList as ancd]
							[#if ancd.czdm != "#"]
								<button type="button"  href="javascript:void(0);" class="btn btn-default btn_${ancd.czdm}" id="btn_${ancd.czdm}">
									<i class="bigger-100 ${ancd.anys}"></i>
									${ancd.czmc}
								</button>
							[/#if]
						[/#list]
					</div>
				</div>
			[/@buttonsUtil]
		<!-- 加载当前菜单栏目下操作 -->
    </div>
</div>




