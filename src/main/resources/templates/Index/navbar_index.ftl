<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand" href="/" style="font-family: leaf; font-size: 22px;">Banagreen</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse"></div>
    <div>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link active" href="/walls/${ currentUser.username }" data-toggle="tooltip" data-placement="top" title="Perfil" data-original-title="Tooltip on top">
                    <strong>${ currentUser.name }</strong>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#" data-toggle="tooltip" data-placement="top" title="Solicitudes de amistad" data-original-title="Tooltip on top">
                    <i class="fa fa-users"></i>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#" data-toggle="tooltip" data-placement="top" title="Notificaciones" data-original-title="Tooltip on top">
                    <i class="fa fa-bell" aria-hidden="true"></i>
                </a>
            </li>
            <a href="/logout"><button class="btn btn-secundary btn-sm">Cerrar Sesión</button></a>
        </ul>
    </div>
</nav>
