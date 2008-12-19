<#global CSSCLASS_PREFIX="Pager" />
<#global JSFUNC_PREFIX="pager_" />
<#include "common.ftl"/>
<#function genPageLink index>
    <#local link="">
    <#if method?lower_case = "get">
        <#local link=baseLink + "?">
        <#local link=link + pageIndexText + "=" + index>
        <#if visiblePageSize>
            <#local link=link + "&" + pageSizeText + "=" + pageSize>
        </#if>
        <#if builtParams?exists && builtParams != ''>
            <#local link=link +  builtParams>
        </#if>
    <#else>
        <#local link="javascript:" + genJSFuncName("goto") + "(" + index + ")">
    </#if>
    <#return link>
</#function>
<#function getPageLinkInfo key>
    <#switch key>
        <#case "first">
            <#return [genPageLink(1), firstText, previousPageAvailable]>
        <#case "previous">
            <#return [genPageLink(pageIndex-1), previousText, previousPageAvailable]>
        <#case "next">
            <#return [genPageLink(pageIndex+1), nextText, nextPageAvailable]>
        <#case "last">
            <#return [genPageLink(totalPages), lastText, nextPageAvailable]>
    </#switch>
</#function>
<#function getRealMessage key>
    <#local msg=key>
    <#local msg=msg?replace("#pageIndex#", pageIndex)>
    <#local msg=msg?replace("#pageSize#", pageSize)>
    <#local msg=msg?replace("#totalPages#", totalPages)>
    <#local msg=msg?replace("#itemName#", itemName)>
    <#local msg=msg?replace("#startItemIndex#", startItemIndex)>
    <#local msg=msg?replace("#endItemIndex#", endItemIndex)>
    <#local msg=msg?replace("#totalItems#", totalItems)>
    <#return msg>
</#function>
<#function getInfoMessage key>
    <#local msg="">
    <#switch key>
        <#case "info">
            <#local msg=getRealMessage(info)>
            <#break>
        <#case "info1">
            <#local msg=getRealMessage(info1)>
            <#break>
        <#case "info2">
            <#local msg=getRealMessage(info2)>
            <#break>
        <#case "info3">
            <#local msg=getRealMessage(info3)>
            <#break>
        <#case "info4">
            <#local msg=getRealMessage(info4)>
            <#break>
        <#case "info5">
            <#local msg=getRealMessage(info5)>
            <#break>
    </#switch>
    <#return msg>
</#function>
<#global elements=layoutElements>
<div id="${id?html}"<#rt/>
<#if cssClass?exists>
 class="${cssClass?html}"<#rt/>
</#if>
>
<#if totalItems gt 0>
    <#if elements?seq_contains("input") || elements?seq_contains("goto")>
    <script language="javascript">
    function ${genJSFuncName("input")}(event) {
        if(event.keyCode == 13) {
            ${genJSFuncName("goto")}(0);
            event.cancelBubble = true;
            if(document.all) {
                event.returnValue = false;
                event.cancel = true;
            } else {
                event.preventDefault();
                event.stopPropagation();
            }
        }
    }
    function ${genJSFuncName("goto")}(pageIndex) {
    <#if method?lower_case = "get">
        if(pageIndex == 0) {
            var input = document.getElementById("${genElementId("input")}");
            var pageIndex;
            pageIndex = (input) ? parseInt(input.value) : ${pageIndex};
            if(isNaN(pageIndex) || pageIndex < 1 || pageIndex > ${totalPages} ) {
                alert("${getRealMessage(invalidPageInputAlert)}");
                input.value = "${pageIndex}";
                input.select();
                return false;
            }
        }
        var link = "${genPageLink(0)}";
        window.location.href = link.replace("${pageIndexText}=0", "${pageIndexText}=" + pageIndex);
        return false;
    <#else>
        if(!pageIndex) {
            var input = document.getElementById(\"" + makeElementIdName(INPUT_ELEMENT) + "
            pageIndex = (input) ? parseInt(input.value) : ${pageIndex};
            if(isNaN(pageIndex) || pageIndex < 1 || pageIndex > ${totalPages} ) {
                alert("${getRealMessage(invalidPageInputAlert)}");
                input.value = "${pageIndex}";
                input.select();
                return false;
            }
        }
        // set current pageIndex
        var currIndex = document.getElementById("${pageIndex}");

        currIndex.value = pageIndex;
        currIndex.form.submit();
    </#if>
    }
    </script>
    </#if>
    <#if method?lower_case = "post">
    <input type="hidden" id="${pageIndexText}" name="${pageIndexText}" value="${pageIndex}" />
        <#if visiblePageSize>
    <input type="hidden" id="${pageSizeText}" name="${pageSizeText}" value="${pageSize}" />
        </#if>
    </#if>
    <#list elements as element>
        <#if element="info" || element="info1" || element="info2" || element="info3" || element="info4" || element="info5">
    <span id="${genElementId(element)}">${getInfoMessage(element)}</span>
        <#elseif element="first" || element="previous" || element="next" || element="last">
            <#assign info=getPageLinkInfo(element)>
            <#if info[2]>
    <a id="${genElementId(element)}" href="${info[0]}">${info[1]}</a>
            <#else>
    <a id="${genElementId(element)}" href="javascript:void(0)" class="${genCssClass('Disable')}">${info[1]}</a>
            </#if>
        <#elseif element="input">
    <input id="${genElementId(element)}" name="${genElementId(element)}" value="${pageIndex}" onkeypress="return ${genJSFuncName('input')}(event);" />
        <#elseif element="indexes" || element="indexes2">
            <#assign midd = (maxPageIndex/2)?int>
            <#assign begin = pageIndex - midd>
            <#assign end = pageIndex + midd>
            <#if pageIndex lte midd>
                <#assign begin = 1>
                <#if maxPageIndex lt totalPages>
                    <#assign end=maxPageIndex>
                <#else>
                    <#assign end=totalPages>
                </#if>
            </#if>
            <#if (pageIndex + midd) gte totalPages>
                <#if maxPageIndex lt totalPages>
                    <#assign begin=totalPages-maxPageIndex>
                <#else>
                    <#assign begin=1>
                </#if>
                <#assign end=totalPages>
            </#if>
            <#assign showRange=((element=="indexes2") && (totalPages gt maxPageIndex) && (end lt totalPages))>
            <#if showRange>
                <#assign end=end-maxLastPageIndex>
            </#if>
            <#list begin..end as i>
                <#if i != pageIndex>
    <a href="${genPageLink(i)}" class="${genCssClass('Indexes')}">${i}</a>
                <#else>
    <span id="${genElementId('current')}">${i}</span>
                </#if>
            </#list>
            <#if showRange>
    <span id="${genElementId('range')}">...</span>
                <#list (totalPages - maxLastPageIndex + 1)..totalPages as i>
    <a href="${genPageLink(i)}" class="${genCssClass('Indexes')}">${i}</a>
                </#list>
            </#if>
        <#elseif element="goto">
    <input type="button" id="${genElementId(element)}" name="${genElementId(element)}" value="${gotoText}" onclick="return ${genJSFuncName('goto')}(0);" />
        </#if>
    </#list>
<#elseif noItemText?exists>
    <div id="${genElementId('noitem')}">
        ${getRealMessage(noItemText)}
    </div>
</#if>
</div>