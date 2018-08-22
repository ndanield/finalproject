<#import "../base.ftl" as b>

<@b.base>
<#include "navbar_auth.ftl">

<div class="content">
    <div class="row justify-content-around">
        <div class="col-lg-8">
            <h2 class="card-text" style="color: white">Registrate en la red donde puedes compartir tus pensamientos</h2>
        </div>

        <div class="col-lg-3">
            <div class="card border-primary mb-3 mx-auto">
                <div class="card-body">
                    <h4 class="card-title" align="center" style="font-family: leaf,serif; font-size: 50px; color: #316a3a">BanaGreen</h4>

                    <form id="registerForm" action="/register" method="POST" autocomplete="off">

                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <input class="form-control fullName" name="firstName" placeholder="Nombres" type="text">
                            </div>
                            <div class="form-group col-md-6">
                                <input class="form-control fullName" name="lastName" placeholder="Apellidos" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <input class="form-control" name="username" placeholder="Nombre de usuario" type="text">
                        </div>

                        <div class="form-group">
                            <input id="password" class="form-control" name="password" minlength="8"  placeholder="Contraseña" type="password">
                        </div>

                        <div class="form-group">
                            <input id="confirmPassword" class="form-control" name="confirmPassword" minlength="8"  placeholder="Confirmar contraseña" type="password">
                        </div>

                        <div class="form-group">
                            <input class="form-control" name="bornDate" type="date">
                        </div>

                        <div class="form-group">
                            <div class="autocomplete">
                                <input class="form-control" id="citylist" name="city" placeholder="Ciudad" type="text">
                            </div>
                        </div>

                        <div class="form-group row">
                            <button type="submit" class="btn btn-success mx-auto "><strong>Registrarte</strong></button>
                        </div>

                    </form>
                </div>
            </div>

            <div class="card border-primary mx-auto">
                <div class="card-body" align="center">
                    <p class="card-text">¿Tienes Cuenta? <a href="/login" class="card-link">Inicia Sesión</a></p>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/js/city.js"></script>
<script type="text/javascript" src="/js/jquery.validate.js"></script>
<script src="/js/register.js"></script>

<#include "footer.ftl">
</@b.base>