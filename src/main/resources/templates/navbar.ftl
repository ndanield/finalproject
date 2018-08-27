<#--noinspection ALL-->
<nav class="navbar navbar-expand-lg navbar-dark bg-success">
    <a class="navbar-brand" href="/" style="font-family: leaf; font-size: 22px;">Banagreen</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarColor01">
        <ul class="navbar-nav ml-auto">
            <#if currentUser.profileImage??>
                <img src="${ currentUser.profileImage.path }" alt="Avatar" class="avatar"/>
            <#else>
                <img src="/images/zeldris.jpg" class="avatar" alt="avatar">
            </#if>
            <li class="nav-item active">
                <a class="nav-link" href="/walls/${ currentUser.username }" data-toggle="tooltip" data-placement="top" title="Perfil" data-original-title="Tooltip on top">
                    <span>${ currentUser.name }</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/" data-toggle="tooltip" data-placement="bottom" title="Ir a inicio">
                    <i class="fa fa-home fa-lg"></i>
                    <span>Inicio</span>
                </a>
            </li>
            <li class="nav-item">
                <a id="friendRequestPopover" class="nav-link" data-toggle="popover" data-container="body" data-placement="bottom" data-title="Solicitudes de amistad" >
                    <#if friendRequestList?size gt 0>
                        <span class="badge badge-notify">${ friendRequestList?size }</span>
                    </#if>
                    <i class="fa fa-users fa-lg"></i>
                    <span>Solicitudes</span>
                </a>
            </li>
            <#--<li class="nav-item">-->
                <#--<a class="nav-link" data-toggle="popover" data-placement="bottom" title="Notificaciones">-->
                    <#--<#if notificationList?size gt 0 >-->
                        <#--<span class="badge badge-notify">${ notificationList?size }</span>-->
                    <#--</#if>-->
                    <#--<i class="fa fa-bell fa-lg" aria-hidden="true"></i>-->
                    <#--<span>Notificaciones</span>-->
                <#--</a>-->
            <#--</li>-->
            <#if currentUser.administrator >
                <li class="nav-item">
                    <a class="nav-link" href="/userlist" data-toggle="tooltip" data-placement="bottom" title="Ajustes">
                        <i class="fa fa-cog fa-lg"></i>
                        <span>Ajustes</span>
                    </a>
                </li>
            </#if>
            <li class="nav-item">
                <a class="nav-link" href="/logout" data-toggle="tooltip" data-placement="top" title="Cerrar sesión" data-original-title="Tooltip on top">
                    <i class="fa fa-sign-out-alt fa-lg"></i>
                    <span>Cerrar Sesión</span>
                </a>
            </li>
        </ul>
    </div>
</nav>

<div id="popover_content_wrapper" class="d-none">
    <#list friendRequestList>
        <#items as friendRequest>
            <div class="friend-request">
                <#if friendRequest.requestUser.profileImage??>
                    <img class="avatar" src="${ friendRequest.requestUser.profileImage.path }" alt="perfil">
                <#else >
                    <img src="/images/zeldris.jpg" alt="avatar">
                </#if>
                <span><strong>${ friendRequest.requestUser.name + " " +  friendRequest.requestUser.lastname }</strong> quiere ser t&uacute; amigo</span>
                <form action="/friendRequest/accept/${ friendRequest.requestUser.username }" method="post" class="d-inline">
                    <button type="submit" class="btn btn-sm btn-primary">Aceptar</button>
                    <a href="/friendRequest/delete/${ friendRequest.requestUser.username }" class="btn btn-sm btn-secondary">Rechazar</a>
                </form>
                    <#--TODO - este input solo tiene propositos de prueba-->
                    <#--<input type="hidden" name="loginRedirect" value="${ re }">-->
            </div>
        </#items>
    </#list>
</div>


