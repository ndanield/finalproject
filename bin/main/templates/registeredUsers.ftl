<#import "base.ftl" as b>

<@b.base>
<#include "navbar.ftl">
<#include "menu.ftl">
<div class="content" align="center">
    <div class="col-lg-5" id="usersTable">
        <table class="table table-hover">
            <thead>
            <tr>
                <th colspan="5" style="text-align: center"><h2>Usuarios Registrados</h2></th>
            </tr>
            <tr>
                <th>Usuario</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Edad</th>
                <th>Administrador</th>
                <th>Elevar Permisos</th>
            </tr>
            </thead>
            <tbody>
                <#list usersList as u>
                <tr>
                    <td>${u.username}</td>
                    <td>${u.name}</td>
                    <td>${u.lastname}</td>
                    <td>${u.edad}</td>
                    <td>${u.administrator?c}</td>
                    <td><form method="post">
                        <div class="form-group" hidden>
                            <input class="form-control" name="username" value="${u.username}">
                        </div>

                        <div class="form-group">
                            <#if !u.administrator>
                                <button type="submit" class="btn btn-secondary" id="roleChange" title="Hacer este usuario administrador">Hacer Admin</button>
                            </#if>
                        </div>

                    </form>
                        <#--<form id="accion" ic-post-to="/userlist">
                            <input id="changeRole" value="${u.username}" hidden>
                            <#if !u.administrator>
                                <button class="btn btn-secondary" id="bootonAccion" ic-trigger-on="click">Hacer Admin</button>
                            </#if>
                        </form>-->
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>

</div>
<script type="text/javascript" src="/js/jquery-3.3.1.js"></script>
<#--<script type="text/javascript" src="/js/updateRole.js"></script>-->
<script type="text/javascript" src="/js/intercooler-1.2.1.min.js"></script>
</@b.base>