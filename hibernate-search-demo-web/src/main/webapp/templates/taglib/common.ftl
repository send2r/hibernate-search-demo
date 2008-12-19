<#function genElementId key>
    <#local eid=id + "_" + key>
    <#return eid?lower_case>
</#function>
<#function genCssClass key>
    <#return CSSCLASS_PREFIX + key>
</#function>
<#function genJSFuncName key>
    <#local name=JSFUNC_PREFIX + id + "_" + key>
    <#return name?lower_case>
</#function>
<#function encodeText text="FREE_MARKER:DEFAULT_TEXT">
    <#if text != "" && text != "FREE_MARKER:DEFAULT_TEXT">
        <#return text?html>
    </#if>
    <#return "&nbsp;">
</#function>