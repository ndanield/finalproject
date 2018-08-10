<nav class="navbar navbar-expand-lg navbar-dark bg-success">
    <a class="navbar-brand" href="/" style="font-family: leaf; font-size: 22px;">Banagreen</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarColor01">
        <ul class="navbar-nav ml-auto">
            <img src="/images/monkey-face.png" alt="Avatar" class="avatar"/>
            <li class="nav-item active">
                <a class="nav-link" href="/walls/${ currentUser.username }" data-toggle="tooltip" data-placement="top" title="Perfil" data-original-title="Tooltip on top">
                    <span>${ currentUser.name }</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/" data-toggle="tooltip" data-placement="top" title="Ir a inicio" data-original-title="Tooltip on top">
                    <i class="fa fa-home fa-lg"></i>
                    <span>Inicio</span>
                </a>
            </li>
            <#--<li class="nav-item">-->
                <#--<a class="nav-link" href="#" data-toggle="tooltip" data-placement="top" title="Solicitudes de amistad" data-original-title="Tooltip on top">-->
                    <#--<i class="fa fa-users fa-lg"></i> -->
                    <#--<span>Solicitudes</span>-->
                <#--</a>-->
            <#--</li>-->
            <li class="nav-item">
                <a class="nav-link" href="#" data-toggle="tooltip" data-placement="top" title="Notificaciones" data-original-title="Tooltip on top">
                    <#if notificationList?size gt 0 >
                        <span class="badge badge-notify">${ notificationList?size }</span>
                    </#if>
                    <i class="fa fa-bell fa-lg" aria-hidden="true"></i>
                    <span>Notificaciones</span>
                </a>
            </li>
            <#if currentUser.administrator >
                <li class="nav-item">
                    <a class="nav-link" href="/userlist" data-toggle="tooltip" data-placement="top" title="Ajustes" data-original-title="Tooltip on top">
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

    <#--<div>-->
        <#--<ul class="nav nav-pills">-->
            <#--<li class="nav-item">-->
                <#--<a class="nav-link active" href="/wall" data-toggle="tooltip" data-placement="top" title="Perfil" data-original-title="Tooltip on top" >-->
                    <#--<img src="/images/monkey-face.png" alt="Avatar" class="avatar"/> <strong>${currentUser.name}</strong>-->
                <#--</a>-->
            <#--</li>-->
            <#--<li class="nav-item">-->
                <#--<a class="nav-link" href="#" data-toggle="tooltip" data-placement="top" title="Solicitudes de amistad" data-original-title="Tooltip on top">-->
                    <#--<i class="fa fa-users"></i>-->
                <#--</a>-->
            <#--</li>-->
            <#--<li class="nav-item">-->
                <#--<a class="nav-link" href="#" data-toggle="tooltip" data-placement="top" title="Notificaciones" data-original-title="Tooltip on top">-->
                    <#--<i class="fa fa-bell" aria-hidden="true"></i>-->
                <#--</a>-->
            <#--</li>-->
            <#--<li class="nav-item dropdown">-->
                <#--<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false"></a>-->
                <#--<div class="dropdown-menu">-->
                    <#--<a class="dropdown-item" href="#">Ajustes</a>-->
                    <#--<div class="dropdown-divider"></div>-->
                    <#--<a class="dropdown-item" href="/logout">Cerrar Sesión</a>-->
                <#--</div>-->
            <#--</li>-->
        <#--</ul>-->
    <#--</div>-->
</nav>
