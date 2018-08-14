<div class="card my-3">
    <div class="card-header">
        Lista de amigos
    </div>
    <div class="card-body">
        <#list friendList>
            <ul class="list-group">
                <#items as friend>
                    <li>${ friend.name + friend.lastname }</li>
                </#items>
            </ul>
        </#list>
    </div>
</div>