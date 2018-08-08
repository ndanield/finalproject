<nav class="navbar navbar-expand-lg navbar-dark bg-success">
    <a class="navbar-brand" href="/" style="font-family: leaf; font-size: 22px;">Banagreen</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarColor01">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <img src="/images/monkey-face.png" alt="Avatar" class="avatar p-0"/>
            </li>
            <li class="nav-item active">
                <a class="nav-link active" href="/walls/${ currentUser.username }" data-toggle="tooltip" data-placement="top" title="Perfil" data-original-title="Tooltip on top">
                    <strong>${ currentUser.name }</strong>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/" data-toggle="tooltip" data-placement="top" title="Ir a inicio" data-original-title="Tooltip on top">
                    <i class="fa fa-home"></i> Inicio
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#" data-toggle="tooltip" data-placement="top" title="Solicitudes de amistad" data-original-title="Tooltip on top">
                    <i class="fa fa-users"></i> Solicitudes
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#" data-toggle="tooltip" data-placement="top" title="Notificaciones" data-original-title="Tooltip on top">
                    <i class="fa fa-bell" aria-hidden="true"></i> Notificaciones
                </a>
                <span class="badge badge-notify">${ notificationList?size }</span>
            </li>
            <#if currentUser.administrator >
                <li class="nav-item">
                    <a class="nav-link" href="#" data-toggle="tooltip" data-placement="top" title="Ajustes" data-original-title="Tooltip on top">
                        <i class="fa fa-cog"></i> Ajustes
                    </a>
                </li>
            </#if>
            <li class="nav-item">
                <a href="/logout"><button class="btn btn-secundary">Cerrar Sesión</button></a>
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
