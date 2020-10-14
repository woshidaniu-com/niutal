/*******************************************************************************
* 
* Copyright (R) 2014 我是大牛软件股份有限公司。
*
* 上传word文件插件
* 上传之后，返回html字符串，填充到编辑器区域内部
*
* @author zhidong weiyue888999@126.com
* 
*******************************************************************************/
KindEditor.plugin('wordupload', function(K) {
	var self = this, name = 'wordupload',lang = self.lang(name + '.');
	
		var uploadWordUrl = K.undef(self.wordUploadUrl, {});
		var extraParams = K.undef(self.extraFileUploadParams, {});
		var filePostName = K.undef(self.filePostName, 'wordFile');
		
		if(!uploadWordUrl){
			console.log("没有配置uploadWordUrl参数!!!");
		}
		
	self.plugin.worduploadDialog = function(options) {
		var clickFn = options.clickFn;
		var target = 'kindeditor_upload_iframe_' + new Date().getTime();
		var hiddenElements = [];
		for(var k in extraParams){
			hiddenElements.push('<input type="hidden" name="' + k + '" value="' + extraParams[k] + '" />');
		}
		var html = [
			'<div style="padding:20px;">',
			//local upload - start
			'<iframe name="' + target + '" style="display:none;"></iframe>',
			'<form class="ke-upload-area ke-form" method="post" enctype="multipart/form-data" target="' + target + '" action="' + K.addParam(uploadWordUrl, 'dir=word') + '">',
			//file
			'<div class="ke-dialog-row">',
			hiddenElements.join(''),
			'<label style="width:60px;">上传</label>',
			'<input type="text" name="localUrl" class="ke-input-text" tabindex="-1" style="width:200px;" readonly="true" /> &nbsp;',
			'<input type="button" class="ke-upload-button" value="浏览" />',
			'</div>',
			'</form>',
			//local upload - end
			'</div>'
		].join('');
		var dialog = self.createDialog({
			name : name,
			width : 450,
			height : 300,
			title : '从word导入',
			body : html,
			yesBtn : {
				name : '确定',
				click : function(e) {
					if (dialog.isLoading) {
						return;
					}
					if (uploadbutton.fileBox.val() == '') {
						alert(self.lang('pleaseSelectFile'));
						return;
					}
					dialog.showLoading(self.lang('uploadLoading'));
					uploadbutton.submit();
					localUrlBox.val('');
					return;
				}
			},
			beforeRemove : function() {
				viewServerBtn.unbind();
			}
		}),
		div = dialog.div;

		var urlBox = K('[name="url"]', div),
			localUrlBox = K('[name="localUrl"]', div),
			viewServerBtn = K('[name="viewServer"]', div);

		var uploadbutton = K.uploadbutton({
			button : K('.ke-upload-button', div)[0],
			fieldName : filePostName,
			form : K('.ke-form', div),
			target : target,
			width: 60,
			afterUpload : function(data) {
				dialog.hideLoading();
				self.exec('inserthtml', data, true);
				self.hideDialog();
			},
			afterError : function(html) {
				dialog.hideLoading();
				self.exec('inserthtml', html, true);
				self.hideDialog();
			}
		});
		uploadbutton.fileBox.change(function(e) {
			localUrlBox.val(uploadbutton.fileBox.val());
		});
		
		viewServerBtn.hide();
		return dialog;
	};
	self.plugin.wordupload = {
			show : function() {
				self.plugin.worduploadDialog({});
			}
		};
	self.clickToolbar(name, self.plugin.wordupload.show);
});
