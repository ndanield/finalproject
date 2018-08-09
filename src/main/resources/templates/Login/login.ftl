<#import "../base.ftl" as b>

<@b.base>
    <#include "navbar_login.ftl">
<div class="content" id="loginBgImg">
    <div class="row justify-content-around">
        <div class="col-lg-8">
            <h2 id="quoteText" class="centeredText" style="color: white"></h2>
        </div>
        <div class="col-lg-3">
            <#if authenticationFailed!false>
                <div class="alert alert-dismissible alert-danger">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <strong>¡Ay no!</strong> La contraseña o el nombre de usuario es incorrecto.
                </div>
            <#elseif authenticationSucceeded!false>
                <div class="alert alert-dismissible alert-success">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <strong>¡Bien!</strong> Has iniciado sesión satisfactoriamente.
                </div>
            </#if>
            <div class="card border-primary mb-3 mx-auto">
                <div class="card-body">
                    <h4 class="card-title" align="center" style="font-family: leaf; font-size: 50px; color: #316a3a">BanaGreen</h4>
                    <!-- <p class="card-text">Registrate en la red donde puedes compartir tus pensamientos</p> -->
                    <form action="/login" method="post">
                        <div class="form-group row">
                            <div class="col-md-12" style="margin: 0 auto;">
                                <input class="form-control" name="username" placeholder="Usuario" type="text">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-md-12" style="margin: 0 auto;">
                                <input  class="form-control" name="password" placeholder="Contraseña" type="password">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="custom-control custom-checkbox mx-auto">
                                    <input class="custom-control-input" id="remember-me" name="remember-me" type="checkbox">
                                    <label class="custom-control-label" for="remember-me">Recuerdame</label>
                            </div>
                        </div>
                        <div class="form-group row">
                            <button type="submit" class="btn btn-success mx-auto"><i class="fa fa-sign-in-alt"> <strong>Entrar</strong></i></button>
                        </div>

                        <#if loginRedirect?has_content>
                            <input type="hidden" name="loginRedirect" value="${ loginRedirect }">
                        </#if>

                    </form>
                </div>
            </div>
            <div class="card border-primary mx-auto">
                <div class="card-body" align="center">
                    <p class="card-text">¿No tienes Cuenta? <a href="/register" class="card-link">registrate</a></p>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="/js/TypingEffect.js"></script>
</@b.base>