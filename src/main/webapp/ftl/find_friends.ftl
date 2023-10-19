<#include "base.ftl">
<#macro title>Find friends</#macro>
<#macro content>
    <style>
        .tab-content {
            border: none;
            padding: 0;
        }
    </style>

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-9">
                <ul class="nav justify-content-center">
                    <li class="tab-button" onclick="showTab('friendsTab')">Friends</li>
                    <li class="tab-button" onclick="showTab('allUsersTab')">All Users</li>
                </ul>

                <div class="tab-content" id="friendsTab">
                    <h2 class="mb-4">Friends</h2>

                    <div class="row" id="user-list">
                        <#list friends as friend>
                            <div class="col-md-4 mb-3">
                                <div class="card">
                                    <a href="/user?userId=${friend.userId}">
                                        <div class="user-avatar" style="display: flex; align-items: center;">
                                            <img src="<#if friend.profilePicture?has_content>${friend.profilePicture}<#else>https://dummyimage.com/64x64/dee2e6/6c757d.jpg</#if>"
                                                 alt="User Avatar" width="64" height="64" class="rounded-circle"
                                                 style="margin-right: 16px;">
                                            <span class="user-id font-weight-bold"
                                                  style="color: #000;">${friend.username}</span>
                                        </div>
                                    </a>
                                </div>
                            </div>
                        </#list>
                    </div>
                </div>

                <div class="tab-content" id="allUsersTab">
                    <div class="input-group d-flex align-items-center mb-3">
                        <input type="text" class="form-control" id="searchFriendInput" placeholder="Find friends">
                        <div class="input-group-append">
                            <svg class="search-icon" style="margin-left: 8px;" fill="#000000" height="24px"
                                 width="24px" version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg"
                                 xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 488.4 488.4"
                                 xml:space="preserve"></g>
                                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                <g id="SVGRepo_iconCarrier">
                                    <g>
                                        <g>
                                            <path d="M0,203.25c0,112.1,91.2,203.2,203.2,203.2c51.6,0,98.8-19.4,134.7-51.2l129.5,129.5c2.4,2.4,5.5,3.6,8.7,3.6 s6.3-1.2,8.7-3.6c4.8-4.8,4.8-12.5,0-17.3l-129.6-129.5c31.8-35.9,51.2-83,51.2-134.7c0-112.1-91.2-203.2-203.2-203.2 S0,91.15,0,203.25z M381.9,203.25c0,98.5-80.2,178.7-178.7,178.7s-178.7-80.2-178.7-178.7s80.2-178.7,178.7-178.7 S381.9,104.65,381.9,203.25z"></path>
                                        </g>
                                    </g>
                                </g></svg>
                        </div>
                    </div>
                    <div class="row" id="user-list">
                        <#list users as user>
                            <div class="col-md-4 mb-3">
                                <div class="card">
                                    <a href="/user?userId=${user.userId}">
                                        <div class="user-avatar" style="display: flex; align-items: center;">
                                            <img src="<#if user.profilePicture?has_content>${user.profilePicture}<#else>https://dummyimage.com/64x64/dee2e6/6c757d.jpg</#if>"
                                                 alt="User Avatar" width="64" height="64" class="rounded-circle"
                                                 style="margin-right: 16px;">
                                            <span class="user-id font-weight-bold"
                                                  style="color: #000;">${user.username}</span>
                                        </div>
                                    </a>
                                </div>
                            </div>
                        </#list>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        function showTab(tabId) {
            const tabs = document.querySelectorAll('.tab-content');
            tabs.forEach(tab => tab.style.display = 'none');
            document.getElementById(tabId).style.display = 'block';
        }

        showTab('friendsTab');

        $(document).on("click", ".search-icon", function () {
            sendSearchRequest();
        });

        $("#searchFriendInput").on("keyup", function (event) {
            if (event.key === "Enter") {
                sendSearchRequest();
            }
        });

        function sendSearchRequest() {
            const searchInput = $("#searchFriendInput");
            const searchText = searchInput.val();

            $.ajax({
                type: "POST",
                url: "/find_friends",
                data: {
                    "searchText": searchText
                },
                dataType: "json",
                success: function (response) {
                    searchInput.val('');
                    $("#user-list").empty()

                    response.forEach(function (user) {
                        var userCard = $("<div>").addClass("col-md-6 mb-4").append(
                            $("<div>").addClass("card").append(
                                $("<a>").attr("href", "/user?userId=" + user.userId).append(
                                    $("<div>").addClass("user-avatar").css("display", "flex").append(
                                        $("<img>").attr("src", user.profilePicture)
                                            .attr("alt", "User Avatar")
                                            .attr("width", "64")
                                            .attr("height", "64")
                                            .addClass("rounded-circle")
                                            .css("margin-right", "16px"),
                                        $("<span>").addClass("user-id font-weight-bold").css("color", "#000").text(user.username)
                                    )
                                )
                            )
                        );

                        $("#user-list").append(userCard);
                    });
                },
                error: function (error) {
                    console.error("Error:", error);
                }
            });
        }

    </script>
</#macro>
