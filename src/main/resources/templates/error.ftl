<#-- @ftlvariable name="code" type="java.lang.String" -->
<#-- @ftlvariable name="message" type="java.lang.String" -->
<#import "base.ftl" as b>

<@b.base>
    <div class="container" align="center">
        <h1 class="text-danger">${ code }</h1>
        <h3>${ message }</h3>
        <img src="/images/monkey-face.png" alt="monkey chip is smiling" width="300px" height="300px">
    </div>
</@b.base>