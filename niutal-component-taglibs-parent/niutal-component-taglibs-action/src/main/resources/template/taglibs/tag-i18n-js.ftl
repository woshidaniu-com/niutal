<#if parameters.jsURL?exists> 
<#-- 当前功能页面国际化js. -->
<script type="text/javascript" src="${parameters.jsURL?html}?ver=${parameters.jsVersion?html}" charset="utf-8"></script>
</#if><#rt/>
<#if parameters.v5URL?exists> 
<#-- 当前功能页面国际化js. -->
<script type="text/javascript" src="${parameters.stylePath?html}${parameters.v5URL?html}?ver=${parameters.jsVersion?html}" charset="utf-8"></script>
</#if><#rt/>
<#if parameters.i18nURL?exists> 
<#-- 当前功能页面国际化js. -->
<script type="text/javascript" src="${parameters.systemPath?html}/${parameters.i18nURL?html}?ver=${parameters.jsVersion?html}" charset="utf-8"></script>
</#if><#rt/>
