$(function() {
	var _getFfrs = function(){
		var ffdx = [];
		$(":input[name=dxid]").each(function(x,y){
			if ($(y).prop("checked")){
				if ($(y).parents("td").next().find(".active").size()>0){
					$(y).parents("td").next().find(".content .active").each(function(i,n){
						var dxid = $(n).data("dxid");
						var tjid = $(n).data("tjid");
						var tjz = $(n).data("key");
						ffdx.push({ffdx:dxid,dxtj:tjid,tjz:tjz.toString()});
					});
				} else {
					ffdx.push({ffdx:$(":input[name=dxid]").eq(x).val(),dxtj:"",tjz:""});
				}
			}
		});
		
		if (ffdx.length == 0){
			$("span[name=ffrs]").html("0");
		} else {
			console.log(JSON.stringify(ffdx));
			$.getJSON("getFfrs.zf",{ffdx:JSON.stringify(ffdx)},function(data){
				$("span[name=ffrs]").html(data);
			});
		}
	}
	
	$(document).off('click', '.item>.lable').on('click', '.item>.lable', function() {
		//点击标签
		console.log("点击标签")
		var item = $(this).closest('.item');
		$('.item>.lable').removeClass('active');
		$('.item .content').slideUp();
		$(this).addClass('active');
		item.find('.content').slideToggle();
		_getFfrs();
	}).off('click', '.item .content .block').on('click', '.item .content .block', function() {
		//点击选项
		var item = $(this).closest('.item');
		var _checked = item.find('.isChecked');
		if(!$(this).hasClass('active')) {
			$(this).addClass('active');
			if(!_checked.next().is('i')) {
				item.find('.isChecked').after('<i class="glyphicon glyphicon-remove"></i>');
			}
		} else {
			$(this).removeClass('active');
		}
		_getFfrs();
	}).off('mouseleave', '.item .content').on('mouseleave', '.item .content', function() {
		//鼠标离开
		var item = $(this).closest('.item');
		var checked = $(this).find('.block.active').clone();
		var lable = $(this).closest('.item').find('.lable');
		lable.find('.block.active').remove();
		item.find('.isChecked').html(checked);
		$('.item>.lable').removeClass('active');
		$('.item .content').slideUp();

		var pTd = item.closest('td').prev('td');
		if(item.find('.isChecked .block').size() > 0) {
			item.addClass('checked');
			pTd.find('input[type="checkbox"]').prop('checked', true);

		} else {
			item.removeClass('checked');
			item.find('.glyphicon-remove').remove();
			//pTd.find('input[type="checkbox"]').prop('checked', false);
		}
		_getFfrs();
	}).off('click', '.item .glyphicon-remove').on('click', '.item .glyphicon-remove', function() {
		//点击移除图标
		var item = $(this).closest('.item');
		var pTd = item.closest('td').prev('td');
		item.find('.isChecked').html('');
		item.find('.glyphicon-remove').remove();
		item.removeClass('checked');
		item.find('.block').removeClass('active');
		item.find('.glyphicon-remove').remove();
		pTd.find('input[type="checkbox"]').prop('checked', false);
		_getFfrs();
	}).off('click','.tab-cnt .item').on('click','.tab-cnt .item',function(){
		$(this).toggleClass("active");
	}).off('click',':input[name=dxid]').on('click',':input[name=dxid]',function(){
		//点击
		if (!$(this).prop("checked")){
			$(this).parents("td").next().find(".checked").removeClass("checked");
			$(this).parents("td").next().find(".isChecked").empty();
			$(".content .active[data-dxid="+$(this).val()+"]").removeClass("active");
		}
		_getFfrs();
	}); 
	
	$("span[name=yff]").each(function(i,n){
		var ffdx = $(n).data("ffdx");
		var dxtj = $(n).data("dxtj");
		var tjz = $(n).data("tjz");
		$(":input[name=dxid][value="+ffdx+"]").prop("checked",true);
		if (dxtj != ""){
			var node = $(".block[data-dxid="+ffdx+"][data-tjid="+dxtj+"][data-key="+tjz+"]");
			node.addClass("active")
			node.parents(".content").prev(".isChecked").append(node.clone());
			node.parents(".item").addClass("checked");
		}
	});

	_getFfrs();
	
});