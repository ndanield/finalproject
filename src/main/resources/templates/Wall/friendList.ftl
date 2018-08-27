<div class="card">
    <div class="card-header">
        <span class="card-title">Amigos</span>
    </div>
    <#if friendList?size gt 0>
    <#list friendList>
    <div class="friend-list">
        <#items as friend>
        <div class="friend-list-group">
            <#if friend.profileImage??>
                <img src="${ friend.profileImage.path }" alt="Avatar" class="friend-list-pic">
            <#else>
                <img src="/images/zeldris.jpg" alt="avatar">
            </#if>
            <a href="/walls/${ friend.username }" class="friend-list-item">
                ${ friend.name } ${ friend.lastname }
            </a>
        </div>
        </#items>
    </div>
    </#list>
    <#else >
        <div class="card-body">
            <p class="card-text">Aun no tienes amigos</p>
        </div>
    </#if>
</div>
