

//点击统计按钮，统计数据
function tjlb(){	
	var gltj = getTjlbGltjValue();
	jQuery("#newGltj").val(gltj);
	
	//设置是否固定合计总数
	var lockInitial = jQuery('input[name="lockInitial"]').attr('checked');
	
	if(lockInitial){
		base_on_initial_zs = true;
	}else{
		base_on_initial_zs = false;
	}
	onShow();
}


