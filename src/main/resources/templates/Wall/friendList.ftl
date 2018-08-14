<div class="card my-3">
    <div class="card-header">
        Lista de amigos
    </div>
    <div class="card-body">
        <#list friendList>
            <div class="list-group">
                <#items as friend>
                    <a href="/walls/${ friend.username }" class="list-group-item list-group-action">
                        ${ friend.name + friend.lastname }
                    </a>
                </#items>
            </div>
        </#list>
    </div>
</div>
