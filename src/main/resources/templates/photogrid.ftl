<div class="card border-0">
    <div class="card-header">
        <span class="card-title">Albumes</span>
    </div>
    <div class="card-body">
        <#list albumes>
            <#items as album>
                <div class="">
                    ${ album.name }
                </div>
            </#items>
        </#list>
    </div>
</div>