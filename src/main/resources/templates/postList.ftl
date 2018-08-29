
<#if postList?size gt 0>
    <#list postList>
        <#items as post>
            <div class="card mb-4 below-shadow">
                <div class="card-header">
                    <h5 class="card-title"><strong>${ post.user.name }</strong> hizo una publicación </h5>
                    <h6 class="card-subtitle text-muted"><time datetime="${ post.date }">${ post.date }</time></span></h6>
                    <#if post.taggedUser??>
                        <span class="card-subtitle text-muted"><i class="fas fa-tag"></i> Usuario etiquetado: ${post.taggedUser.name} ${post.taggedUser.lastname}</span>
                    </#if>
                </div>

                <#if post.image?has_content>
                    <img style="width: 100%; display: block;" src="${ post.image.path }" alt="Imagen publicada">

                    <#if post.content?has_content>
                        <div class="card-body">
                            <p class="card-text">${ post.content }</p>
                        </div>
                    </#if>
                <#else>
                    <div class="card-body">
                        <h4 class="card-title">${ post.content }</h4>
                    </div>
                </#if>

                <hr>

                <div class="my-panel">
                    <form class="ajax" action="/post/vote" method="POST" style="display: inline;" data-id="${post.id}" data-type="like">
                        <button class="btn btn-sm btn-outline-success" name="like" value="like">
                            <i class="fa fa-thumbs-up"></i>&nbsp;Me gusta&nbsp;
                            <span id="badge-like${post.id}" class="badge badge-light">${ post.getLikesCount() }</span>
                        </button>
                    </form>
                    <form class="ajax" action="/post/vote" method="POST" style="display: inline;" data-id="${post.id}" data-type="dislike">
                        <button class="btn btn-sm btn-outline-danger" name="dislike" value="dislike">
                            <i class="fa fa-thumbs-down"></i>&nbsp;No me gusta&nbsp;
                            <span id="badge-dislike${post.id}" class="badge badge-light">${ post.getDislikesCount() }</span>
                        </button>
                    </form>
                </div>

                <hr>

                <div class="my-panel">
                    <form action="#">
                        <div class="form-group">
                            <textarea name="comment" class="form-control rounded" placeholder="Escribe un comentario" rows="1"></textarea>
                        </div>
                        <button class="btn btn-sm btn-primary">Comentar</button>
                    </form>
                    <p>No hay comentarios por ahora.</p>
                </div>
            </div>

        </#items>
    </#list>

    <#--<button class="btn btn-link justify-content-center" type="submit">Cargar más publicaciones</button>-->
<#else>

    <div class="card mb-3">
        <div class="card-body">
            <h4 class="card-title">No hay publicaciónes</h4>
            <p class="card-text">¡Crea una nueva publicación para llenar tu muro de ideas!</p>
        </div>
    </div>

</#if>