<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand" href="/" style="font-family: leaf; font-size: 22px;">Banagreen</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse"></div>
    <div>
        <ul class="nav nav-pills">
            <li class="nav-item">
                <a class="nav-link active" href="/wall" data-toggle="tooltip" data-placement="top" title="Perfil" data-original-title="Tooltip on top" style="border-right: 1px solid">
                    <img src="/images/playa.jpg" alt="Avatar" class="avatar"/> <strong>${ currentUser.name }</strong>
                </a>
            </li>
            <li class="nav-item" style="border-right: 1px solid">
                <a class="nav-link" href="#" data-toggle="tooltip" data-placement="top" title="Solicitudes de amistad" data-original-title="Tooltip on top"><i class="fa fa-users    "></i></a>
            </li>
            <li class="nav-item" style="border-right: 1px solid">
                <a class="nav-link" href="#" data-toggle="tooltip" data-placement="top" title="Notificaciones" data-original-title="Tooltip on top"><i class="fa fa-bell" aria-hidden="true"></i></a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false"></a>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="#">Ajustes</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="/logout">Cerrar Sesión</a>
                </div>
            </li>
        </ul>
    </div>
</nav>
