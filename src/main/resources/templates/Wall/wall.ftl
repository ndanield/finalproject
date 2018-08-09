<#import "../base.ftl" as b>

<@b.base>
    <#include "../navbar.ftl">
    <#include "../menu.ftl">

<div class="container">

    <div class="portrait mb-3">
        <img src="/images/playa.jpg" alt="Imagen de portada">
        <div class="profile-pic d-flex">
            <img src="/images/monkey-face.png" class="image-avatar image-special" alt="Avatar">
            <h1 class="mt-5 ml-2">${ wallOwner.name }</h1>
        </div>

        <#if wallOwner.username != currentUser.username>
            <#if friendRequest??>
                <form action="/friendRequest/${ wallOwner.username }" method="post">
                    <button type="submit" class="btn btn-success mr-3" style="float: right;"> Solicitar amistad</button>
                </form>
            <#else>
                <#if friendRequest.isAccepted>
                    <form action="#" method="delete">
                        <button type="submit" class="btn btn-danger mr-3" style="float: right;"> Eliminar amistad</button>
                    </form>
                <#else>
                    <button class="btn btn-info mr-3 disabled" style="float: right;"> Respuesta pendiente</button>
                </#if>
            </#if>
        </#if>


    </div>

    <div class="row">

        <div class="col">
            <div class="card mb-3">
                <div class="card-body">
                    <h3><strong>${ wallOwner.name }</strong></h3>
                    <h6>${ wallOwner.username }</h6>
                    <button type="button" class="btn btn-link"><i class="fa fa-images"> Crear Album</i></button>
                    <button type="button" class="btn btn-link"><i class="fa fa-images"> Crear Album</i></button>
                    <button type="button" class="btn btn-link"><i class="fa fa-images"> Crear Album</i></button>
                    <button type="button" class="btn btn-link"><i class="fa fa-images"> Crear Album</i></button>
                </div>
                <div class="card-body" id="gpsPos">
                    <#--<p id="gpsPos">...</p>-->
                </div>
            </div>
        </div>

        <div id="PostColumn" class="col-lg-5 mx-auto">
            <#list postList>
                <#items as post>
                    <div class="card mb-3">
                        <div class="card-header">
                            <h5 class="card-title"><strong>${ post.user.name }</strong> compartio una publicación </h5>
                            <h6 class="card-subtitle text-muted"><time datetime="${ post.date }" style="float: right">${ post.date }</time></span></h6>
                        </div>

                        <#if post.content?has_content>
                            <div class="card-body">
                                <p class="card-text">${ post.content }</p>
                            </div>
                        </#if>

                        <#if post.image.path?has_content>
                            <div class="card-body">
                                <img style="width: 100%; display: block;" src="${ post.image.path }" alt="Imagen publicada">
                            </div>
                        </#if>

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
            <div class="card friends-post" >
                <div class="card-body">
                    <h4 class="card-title">Comparte lo que piensas</h4>
                    <form action="/post" method="POST" enctype="multipart/form-data">
                        <fieldset>
                            <div class="form-group">
                                <textarea name="post-content" class="form-control" id="exampleTextarea" placeholder="Escribe aquí" rows="5"></textarea>
                            </div>
                            <div class="form-inline">
                                <div class="upload-btn-wrapper">
                                    <button type="button" class="btn btn-link"><i class="fa fa-image"></i>  Sube una Foto</button>
                                    <input type="file" name="unploadImage">
                                </div>
                                <button type="submit" class="btn btn-success mx-sm-3"><i class="fa fa-share-alt"></i> Publicar</button>
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