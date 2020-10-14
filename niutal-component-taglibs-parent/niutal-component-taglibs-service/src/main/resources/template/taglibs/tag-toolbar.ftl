<div class="btn-toolbar" role="toolbar" style="float:right;">
	<div class="btn-group" id="but_ancd">");
	if(!BlankUtils.isBlank(list)){
		String path =  null;
		List<String> czdmList  = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			AncdModel _model  = list.get(i);
			if("1".equals(_model.getSfxs())){
				if(!BlankUtils.isBlank(czdms)){
					for (String czdm : czdms) {
						if( _model.getCzdm().equals(czdm)){
							buttons.append(_model.getButton());
							break;
						}
					}
				}else{
					buttons.append(_model.getButton());
				}
			}
			czdmList.add(_model.getCzdm());
			if(i==0){
				path = _model.getPath();
			}
		}
		
		session.setAttribute(WebRequestUtils.getFuncSessionKey(path, user.getJsdm()), czdmList);
	}else{
		ValueStack stack = ActionContext.getContext().getValueStack();
		buttons.append(TextProviderHelper.getText("i18n.unauthorized", "", stack));
	}
	${parameters.unauthorized}
	</div> 
</div>

<#if parameters.funcModel?exists>
<#if parameters.button_list?exists &&  parameters.button_list?size != 0>
<!-- tool bar-start  -->
<div id="${parameters.id?default('func_toolbar')}" <#rt/>
<#if parameters.name?exists> name="${parameters.name?html}" </#if><#rt/>
<#if parameters.cssClass?exists>class="row sl_add_btn ${parameters.cssClass?html}"<#else>class="row sl_add_btn " </#if><#rt/>
<#if parameters.cssStyle?exists> style="${parameters.cssStyle?html}" </#if><#rt/>
<#if parameters.title?exists> title="${parameters.title?html}" </#if><#rt/>
<#include "/${parameters.templateDir}/simple/common-attributes.ftl" />
<#include "/${parameters.templateDir}/simple/dynamic-attributes.ftl" /> >
	<div class="col-sm-12 col-md-12" id="toolbar_container">
		<input type="hidden" name="gnczStr" id="gnczStr" value="" />
		<div class="btn-toolbar" role="toolbar" style="float:right;">
			<div class="btn-group" id="but_ancd">
				<#list parameters.button_list as buttonModel> 
					<#if buttonModel.button_visible == '1'>
						<button type="button" class="btn btn-default ${buttonModel.button_class?default("")}" href="javascript:void(0);" id="${buttonModel.button_id?string}" name="${buttonModel.button_name?string}"><i class="bigger-100 ${buttonModel.button_icon_class?default("")}"></i>&nbsp;${buttonModel.button_text?default("")}</button>
					</#if>						
				</#list>
			</div> 
		</div>
	</div>
</div>
<!-- tool bar-end  -->
</#if>
</#if>