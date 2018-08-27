<#-- @ftlroot ".." -->
<#import "/base.ftl" as b>

<@b.base>
    <#include "/navbar.ftl">
    <#--<#include "../menu.ftl">-->
    <div class="container mt-3">
        <div class="row">
            <div class="col">
                <#if suggestedFriendList?has_content>
                    <div class="card">
                        <div class="card-header">Sugerencias de amistad</div>
                        <div class="card-body">
                        <#list suggestedFriendList>
                            <div class="list-group my-3 ">
                            <#items as suggestedFriend>
                                <a href="/walls/${ suggestedFriend.username }" class="list-group-item d-flex justify-content-between align-items-center">
                                    ${ suggestedFriend.name + " " + suggestedFriend.lastname }
                                </a>
                            </#items>
                            </div>
                        </#list>
                        </div>
                    </div>
                </#if>
            </div>

            <div class="col-lg-5 mx-auto">
                <#include "/postList.ftl">
            </div>

            <div class="col-lg-4">
                <#include "/newPostForm.ftl">
            </div>
        </div>
    </div>
</@b.base>
