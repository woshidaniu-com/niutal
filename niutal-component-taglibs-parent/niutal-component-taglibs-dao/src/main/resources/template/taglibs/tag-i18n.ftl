<!--国际化js库 -->
<#if parameters.i18nList?exists &&  parameters.i18nList?size != 0>
<#-- 当前功能页面国际化js. -->
<#list parameters.i18nList as model> 
<#if model.resource?exists>
<script type="text/javascript" src="${parameters.systemPath?html}/${model.resource?html}?ver=${parameters.jsVersion?html}" charset="utf-8"></script>
</#if><#rt/>
</#list>
</#if><#rt/>
