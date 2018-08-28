<div class="card friends-post below-shadow" >
    <div class="card-body">
        <h4 class="card-title">Comparte lo que piensas</h4>
        <form id="postForm" action="/post" method="POST" enctype="multipart/form-data">
            <div class="form-group">
                <textarea name="postContent" class="form-control" id="exampleTextarea" placeholder="Escribe aquí" rows="4"></textarea>
            </div>
            <div class="form-inline">
                <div class="form-group">
                    <select class="form-control form-control-sm" name="tag" id="tag" title="Etiqueta a un amigo">
                        <option value="#">Etiqueta a un amigo</option>
                        <#list friendList>
                            <#items as friend>
                                <option value="${ friend.username }">${friend.firstName} ${friend.lastName}</option>
                            </#items>
                        </#list>
                    </select>
                </div>
                <div class="form-group upload-btn-wrapper">
                    <button type="button" class="btn btn-link btn-sm"><i class="fa fa-2x fa-image"></i></button>
                    <input type="file" name="uploadImage">
                </div>
                <button type="submit" class="btn btn-success btn-sm">Publicar</button>
            </div>
        </form>
    </div>
</div>

