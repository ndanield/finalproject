<#import "../base.ftl" as b>

<@b.base>
<#include "navbar.ftl">

<div class="content">
    <div class="row">
        <div class="col-lg-6">

        </div>
        <div class="col-lg-6">
            <div class="row">
                <div class="card border-primary mb-3 mx-auto" style="max-width: 20rem;">
                    <div class="card-body">
                        <h4 class="card-title" align="center" style="font-family: leaf; font-size: 50px; color: #316a3a">BanaGreen</h4>
                        <p class="card-text">Registrate en la red donde puedes compartir tus pensamientos</p>
                        <form action="/register" autocomplete="off" method="post">
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <input class="form-control" name="personName" placeholder="Nombre" type="text">
                                </div>
                                <div class="form-group col-md-6">
                                    <input class="form-control" name="lastName" placeholder="Apellido" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <input class="form-control" name="username" placeholder="Usuario" type="text">
                            </div>
                            <div class="form-group">
                                <input class="form-control" name="password" placeholder="Contraseña" type="password">
                            </div>
                            <div class="form-group">
                                <input class="form-control" name="confirmPassword" placeholder="Confirmar contraseña" type="password">
                            </div>
                            <div class="form-group">
                                <input class="form-control" name="bornDate" type="date">
                            </div>
                            <div class="form-group">
                                <input class="form-control" name="bornPlace" placeholder="Lugar de nacimiento" type="text">
                            </div>
                            <div class="form-group">
                                <div class="autocomplete">
                                    <input class="form-control" id="citylist" name="city" placeholder="Ciudad" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <button id="registerButton" type="submit" class="btn btn-success mx-auto"><strong>Registrarse</strong></button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="card border-primary mb-3 col-lg-6 mx-auto" style="max-width: 20rem;">
                    <div class="card-body" align="center">
                        <!-- <h4 class="card-title">Primary card title</h4> -->
                        <p class="card-text">¿Tienes Cuenta? <a href="/login" class="card-link">Inicia Sesión</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/js/city.js"></script>

<#include "footer.ftl">
</@b.base>