<#import "/base.ftl" as b>

<@b.base>
    <#include "/navbar.ftl">
    <#--<#include "/menu.ftl">-->

<div class="container">

    <#-- WARN /!\ Cuidado que este div de con clase portrait contiene el fondo de portada :V -->
    <#if wallOwner.portraitImage??>
        <div class="portrait" style="background: url('${ wallOwner.portraitImage.path }') no-repeat; background-size: cover;">
            <#--<button class="btn btn-sm btn-secondary">Cambiar portada</button>-->
        </div>
    <#else >
        <div class="portrait" style="background: url('/images/default-portrait.png' ) no-repeat; background-size: cover;">
            <#--<button class="btn btn-sm btn-secondary">Cambiar portada</button>-->
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
            <#if !wallOwner.equals(currentUser) && !currentUser.isFriend(wallOwner)>
                <div class="card my-1">
                    <div class="card-header">
                        <span class="card-title">Conoces a ${wallOwner.name}?</span>
                    </div>
                   <div class="card-body">
                        <#if friendRequest?has_content>
                            <#if !currentUser.isFriend(wallOwner)>
                                <#-- El que envia la solicitud -->
                                <#if wallOwner.equals(friendRequest.requestUser)>
                                    <div class="card-text"><i class="fab fa-mailchimp"></i> Solicitud pendiente</div>
                                <#-- El que la recibe -->
                                <#elseif wallOwner.username == friendRequest.targetUser.username>
                                    <div class="card-text"><i class="fas fa-envelope"></i> Respuesta pendiente</div>
                                </#if>
                            </#if>
                        <#else>
                            <form action="/friendRequest/${ wallOwner.username }" method="post">
                                <button type="submit" class="btn btn-sm btn-success mr-3"><i class="fas fa-user-friends"></i>Solicitar amistad</button>
                            </form>
                        </#if>
                   </div>
                </div>
            </#if>

            <#include "friendList.ftl">

            <div class="card my-2">
                <div class="card-header">
                    <span class="card-title"><i class="fas fa-map-marked-alt"></i>Tú ubicación</span>
                </div>
                <div class="card-body" id="gpsPos">
                    <#-- AQUI SE GENERA EL MAPA -->
                </div>
            </div>
        </div>

        <div class="col-lg-5 mx-auto">
            <#include "/postList.ftl">
        </div>

        <div class="col-lg-4">
            <#if !currentUser.isFriend(wallOwner)>
                <#include "/newPostForm.ftl">
            </#if>
        </div>

    </div>
</div>

</@b.base>