<#import "../base.ftl" as b>

<@b.base>
    <#include "navbar_index.ftl">
    <#include "../menu.ftl">
    <div class="content">

        <div class="card border-secondary mx-auto mb-3" style="width: 750px">
            <div class="card-body">
                <a href="" ><h4 class="card-title">Usuario</h4></a>
                <h6 class="card-subtitle text-muted">Actualizó su foto de portada  <time datetime="2018-07-20" style="float: right"> 20-07-2018 1:08 AM</time></h6>
                <!--Aquí va el contenido del usuario-->
            </div>
            <img style="height: 200px; width: 100%; display: block;" src="images/playa.jpg" alt="Card image">
            <div class="card-footer">
                <h4 class="card-title">Comentarios</h4>
                <div class="list-group mg-0">
                    <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
                        <div class="d-flex w-100 justify-content-between">
                            <h5 class="mb-1"><strong> Usuario1</strong></h5>
                            <small class="text-muted">3 days ago</small>
                        </div>

                        <p class="mb-1">Donec id elit non mi porta gravida at eget metus. Maecenas sed diam eget risus varius blandit.</p>

                        <div>
                            <button class="btn btn-sm btn-outline-success" name="vote" value="like">
                                <i class="fa fa-thumbs-up"></i>&nbsp;Me gusta&nbsp;<span class="badge badge-light">4</span>
                            </button>

                            <button class="btn btn-sm btn-outline-danger" name="vote" value="dislike">
                                <i class="fa fa-thumbs-down"></i>&nbsp;No me gusta&nbsp;<span class="badge badge-light">5</span>
                            </button>
                        </div>
                    </a>
                    <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
                      <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1"><strong> Usuario2</strong></h5>
                        <small class="text-muted">3 days ago</small>
                      </div>
                      <p class="mb-1">Donec id elit non mi porta gravida at eget metus. Maecenas sed diam eget risus varius blandit.</p>
                      <small class="text-muted">Donec id elit non mi porta.</small>
                    </a>
                </div>
            </div>
        </div>

    </div>
</@b.base>
