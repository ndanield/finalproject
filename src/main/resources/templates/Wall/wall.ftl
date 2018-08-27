<#import "/base.ftl" as b>

<@b.base>
    <#include "/navbar.ftl">
    <#--<#include "/menu.ftl">-->

<div class="container">

    <#if wallOwner.portraitImage??>
        <div class="portrait" style="background: url('${ wallOwner.portraitImage.path }') no-repeat; background-size: cover;">
            <button class="btn btn-sm btn-secondary">Cambiar portada</button>
        </div>
    <#else >
        <div class="portrait" style="background: url('/images/default-portrait.png' ) no-repeat; background-size: cover;">
            <button class="btn btn-sm btn-secondary">Cambiar portada</button>
        </div>
    </#if>

    <div class="portrait-footer below-shadow">
        <#if wallOwner.profileImage??>
            <img src="${ wallOwner.profileImage.path }" class="profile-pic" alt="Avatar">
        <#else>
            <img src="/images/zeldris.jpg" alt="avatar" class="profile-pic">
        </#if>
        <h1 class="profile-text d-inline">${ wallOwner.name } ${ wallOwner.lastname }</h1>
    </div>

    <div class="row">
        <div class="col">
            <#if wallOwner.username != currentUser.username>
                <div class="card">
                    <#if friendRequest?has_content>
                        <#if isFriend>
                            <form action="#" method="get">
                                <button type="submit" class="btn btn-sm btn-danger mr-3" > Eliminar amistad</button>
                            </form>
                        <#else>
                            <#if wallOwner.username == friendRequest.requestUser.username>
                                <button class="btn btn-info btn-sm mr-3"> Solicitud pendiente</button>
                            <#elseif wallOwner.username == friendRequest.targetUser.username>
                                <#--<button class="btn btn-info btn-sm mr-3"> Respuesta pendiente</button>-->
                                <span class="card-text">Respuesta pendiente </span>
                            </#if>
                        </#if>
                    <#else>
                        <form action="/friendRequest/${ wallOwner.username }" method="post">
                            <button type="submit" class="btn btn-sm btn-success mr-3"> Solicitar amistad</button>
                        </form>
                    </#if>
                </div>
            </#if>

            <#include "friendList.ftl">
        </div>

        <div class="col-lg-5 mx-auto">
            <#include "/postList.ftl">
        </div>

        <div class="col-lg-4">
            <#include "/newPostForm.ftl">
        </div>

    </div>
</div>

</@b.base>