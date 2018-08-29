<#--
<#import "../base.ftl" as b>

<@b.base>
<#include "../navbar.ftl">

<div class="content">
    <div class="card col-lg-10 mx-auto">
        <div class="card-body">
            <div class="album-row">
                <div class="album-column">
                    <img src="playa.jpg" style="height: 200px; width: 100%; display: block;">
                </div>
                <div class="album-column">
                    <img src="playa2.jpg" style="height: 200px; width: 100%; display: block;">
                </div>
                <div class="album-column">
                    <img src="Tesla.jpg" style="height: 200px; width: 100%; display: block;">
                </div>
                <div class="album-column">
                    <img src="android.jpg" style="height: 200px; width: 100%; display: block;">
                </div>
            </div>
        </div>
    </div>
</div>

</@b.base>-->

<#import "/base.ftl" as b>

<@b.base>
    <#include "/navbar.ftl">
<#--<#include "/menu.ftl">-->

<div class="container">

<#-- WARN /!\ Cuidado que este div de con clase portrait contiene el fondo de portada :V -->
    <#--<#if wallOwner.portraitImage??>
        <div class="portrait" style="background: url('${ wallOwner.portraitImage.path }') no-repeat; background-size: cover;">
        &lt;#&ndash;<button class="btn btn-sm btn-secondary">Cambiar portada</button>&ndash;&gt;
        </div>
    <#else >
        <div class="portrait" style="background: url('/images/default-portrait.png' ) no-repeat; background-size: cover;">
        &lt;#&ndash;<button class="btn btn-sm btn-secondary">Cambiar portada</button>&ndash;&gt;
        </div>
    </#if>-->

    <#--<div class="portrait-footer below-shadow">-->
        <#--<#if wallOwner.profileImage??>-->
            <#--<img src="${ wallOwner.profileImage.path }" class="profile-pic" alt="Avatar">-->
        <#--<#else>-->
            <#--<img src="/images/zeldris.jpg" alt="avatar" class="profile-pic">-->
        <#--</#if>-->
        <#--<h1 class="profile-text d-inline">${ wallOwner.name } ${ wallOwner.lastname }</h1>-->
    <#--</div>-->

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

            <#include "friendListAlb.ftl">
        </div>

        <div class="col-lg-5 mx-auto">
            <div class="card friends-post" >
                <div class="card-body">
                    <h4 class="card-title">Crea un album nuevo</h4>
                    <form id="postForm" action="/post" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="redirectPath" value="${ previousRoute }">
                        <div class="form-group">
                            <textarea name="albumDesc" class="form-control" id="exampleTextarea" placeholder="Escribe aquí" rows="4"></textarea>
                        </div>
                        <div class="form-inline">
                <#if wallOwner??>
                    <input type="hidden" name="tag" value="${ wallOwner.username }">
                <#else >
                    <div class="form-group">
                        <select class="form-control custom-select custom-select-sm" name="tag" id="tag" title="Etiqueta a un amigo">
                            <option value="#">Etiqueta a un amigo</option>
                            <#list currentUser.friendList>
                                <#items as friend>
                                    <option value="${ friend.username }">${friend.name} ${friend.lastname}</option>
                                </#items>
                            </#list>
                        </select>
                    </div>
                </#if>
                            <div class="form-group upload-btn-wrapper">
                                <button type="button" class="btn btn-secondary mx-1"><i class="fa fa-lg fa-image"></i></button>
                                <input type="file" name="uploadImage">
                            </div>
                            <button type="submit" class="btn btn-success">Publicar</button>
                        </div>
                    </form>
                </div>
            </div>

        </div>

        <div class="col-lg-4">
            <#if !currentUser.isFriend(wallOwner)>
                <#include "/newPostForm.ftl">
            </#if>
        </div>

    </div>
</div>

</@b.base>