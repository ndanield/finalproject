
<#if postList?size gt 0>
    <#list postList>
        <#items as post>
            <div class="card mb-3">
                <div class="card-header">
                    <#if wallOwner.username != currentUser.username>
                        <h5 class="card-title"><strong>${ post.user.name }</strong> hizo una publicación </h5>
                    <#else>
                        <h5 class="card-title"><strong>Tú</strong> hiciste una publicación </h5>
                    </#if>
                    <h6 class="card-subtitle text-muted"><time datetime="${ post.date }">${ post.date }</time></span></h6>
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

    <button class="btn btn-link justify-content-center" type="submit">Cargar más publicaciones</button>
<#else>

    <div class="card mb-3">
        <div class="card-body">
            <h4 class="card-title">No hay publicaciónes</h4>
            <p class="card-text">¡Crea una nueva publicación ahora para llenar este espacio!</p>
        </div>
    </div>

</#if>