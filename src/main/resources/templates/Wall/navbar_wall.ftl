<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand" href="/" style="font-family: leaf; font-size: 22px;">Banagreen</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarColor01">
        <!-- <ul class="navbar-nav mr-auto">
            <!-- <li class="nav-item active">
                <a class="nav-link" href="#">Register <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Features</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Pricing</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">About</a>
            </li> -->
        <!-- </ul>
         -->

        <!-- <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" placeholder="user" type="text" name="user">
            <input class="form-control mr-sm-2" placeholder="password" type="password" name="passwd">
            <button class="btn btn-secondary my-2 my-sm-0" type="submit">Login</button>
        </form> -->
    </div>
    <div>
        <ul class="nav nav-pills">
            <li class="nav-item">
                <a class="nav-link active" href="/wall" data-toggle="tooltip" data-placement="top" title="Perfil" data-original-title="Tooltip on top" style="border-right: 1px solid"><img src="/images/monkey-face.png" alt="Avatar" class="avatar"/> <strong>${currentUser.name}</strong></a>
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
