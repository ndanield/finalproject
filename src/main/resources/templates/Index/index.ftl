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
                        <div class="card-header">
                            <span class="card-title">Sugerencias de amistad</span>
                        </div>

                        <#list suggestedFriendList>
                        <div class="friend-list">
                            <#items as suggestedFriend>
                            <div class="friend-list-group">
                                <#if suggestedFriend.profileImage??>
                                    <img src="${ suggestedFriend.profileImage.path }" alt="Avatar" class="friend-list-pic">
                                <#else>
                                    <img src="/images/zeldris.jpg" alt="avatar">
                                </#if>
                                <a href="/walls/${ suggestedFriend.username }" class="friend-list-item">
                                    ${ suggestedFriend.name } ${ suggestedFriend.lastname }
                                </a>
                                </div>
                            </#items>
                        </div>
                        </#list>

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
