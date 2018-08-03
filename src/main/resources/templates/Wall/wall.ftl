<#import "../base.ftl" as b>

<@b.base>
    <#include "navbar_wall.ftl">
    <#include "../menu.ftl">

<div class="container">

    <div class="portrait mb-3">
        <#--<img src="/images/playa.jpg" alt="Imagen de portada">-->
        <div class="profile-pic d-flex">
            <img src="/images/monkey-face.png" class="image-avatar image-special" alt="Avatar">
            <h1 class="mt-5 ml-2">Escanor</h1>
        </div>

        <form action="/friendRequest/${ user.username }" method="post">
            <button type="submit" class="btn btn-success mr-3" style="float: right;">Solicitar amistad</button>
        </form>
    </div>

    <div class="row">

        <div class="col">
            <div class="card mb-3">
                <div class="card-body">
                    <h3><strong>${ user.name }</strong></h3>
                    <h6>${ user.username }</h6>
                    <button type="button" class="btn btn-link"><i class="fa fa-images"> Crear Album</i></button>
                    <button type="button" class="btn btn-link"><i class="fa fa-images"> Crear Album</i></button>
                    <button type="button" class="btn btn-link"><i class="fa fa-images"> Crear Album</i></button>
                    <button type="button" class="btn btn-link"><i class="fa fa-images"> Crear Album</i></button>
                </div>
            </div>
        </div>

        <div id="PostColumn" class="col-lg-5 mx-auto">
            <#list postList>
                <#items as post>
                    <div class="card mb-3">
                        <div class="card-header">
                            <h5 class="card-title"><strong>${ post.user.name }</strong> compartio una publicación </h5>
                            <h6 class="card-subtitle text-muted"><time datetime="${ post.date }" style="float: right"> 20-07-2018 1:08 AM</time></span></h6>
                        </div>

                        <div class="card-body">
                            <p class="card-text">${ post.content }</p>
                        </div>

                        <div class="card-body">
                            <img style="width: 100%; display: block;" src="/images/playa.jpg" alt="Imagen publicada">
                        </div>

                    <#--<ul class="list-group list-group-flush">-->
                    <#--<li class="list-group-item"> A fulano y otros le ha gustado tu post</li>-->
                    <#--</ul>-->

                        <div class="card-body">
                            <div class="like-panel">
                                <form action="" method="POST" style="display: inline">
                                    <button class="btn btn-sm btn-outline-success" name="vote" value="like">
                                        <i class="fa fa-thumbs-up"></i>&nbsp;Me gusta&nbsp;<span class="badge badge-light">0</span>
                                    </button>
                                </form>
                                <form action="" method="POST" style="display: inline;">
                                    <button class="btn btn-sm btn-outline-danger" name="vote" value="dislike">
                                        <i class="fa fa-thumbs-down"></i>&nbsp;No me gusta&nbsp;<span class="badge badge-light">0</span>
                                    </button>
                                </form>
                            </div>
                        </div>


                        <div class="card-footer text-muted">
                            No se ha comentado este post
                        </div>
                    </div>

                </#items>
            </#list>

            <buttom class="btn btn-link justify-content-center" type="submit">Cargar más publicaciones</buttom>
        </div>

        <div class="col-lg-4">
            <div class="card friendsnpost" >
                <div class="card-body">
                    <h4 class="card-title">Comparte lo que piensas</h4>
                    <!-- <h6 class="card-subtitle mb-2 text-muted">Card subtitle</h6> -->
                    <form action="/post" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <!-- <label for="exampleTextarea">Example textarea</label> -->
                                <textarea name="post-content" class="form-control" id="exampleTextarea" placeholder="Escribe aquí" rows="5"></textarea>
                            </div>
                            <div class="form-inline">
                                <button type="button" class="btn btn-primary disabled"><i class="fa fa-image"> Sube una Foto</i></button>
                                <button type="submit" class="btn btn-success mx-sm-3"><i class="fa fa-share-alt"> Publicar</i></button>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>
            <ul class="list-group my-3 ">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Fulanito
                    <!-- <span class="badge badge-primary badge-pill">14</span> -->
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Pperencejo
                    <!-- <span class="badge badge-primary badge-pill">2</span> -->
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Pepito
                    <!-- <span class="badge badge-primary badge-pill">1</span> -->
                </li>
            </ul>
        </div>
    </div>
</div>

</@b.base>