<#include "base.ftl">
<#macro title>Find friends</#macro>
<#macro content>
    <style>
        .add-friend-button {
            width: 92px !important;
        }

    </style>

    <div class="container">
        <div class="row">
            <div class="col-md-3">
                <h3 class="mb-4">Filters</h3>
                <div class="card">
                    <div class="card-body">
                        <div class="input-group align-items-center d-flex">
                            <input type="text" class="form-control" id="searchFriendInput" placeholder="Search">
                            <div class="input-group-append">
                                <svg class="search-icon" style="margin-left: 8px;" fill="#000000" height="24px"
                                     width="24px" version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg"
                                     xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 488.4 488.4"
                                     xml:space="preserve"><g id="SVGRepo_bgCarrier" stroke-width="0"></g>
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
                    </div>
                </div>
            </div>
            <div class="col-md-8">
                <h2 class="mb-4">Users</h2>
                <div class="row">
                    <#list users as user>
                        <div class="col-md-6 mb-4">
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

                                <button class="btn btn-primary add-friend-button ml-auto"
                                        style="background-color:  <#if user.isAdded> #fc3f00<#else> #007bff</#if>;"
                                        data-friend-id="${user.userId}">
                                    <svg fill="#fff" version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg"
                                         xmlns:xlink="http://www.w3.org/1999/xlink" width="24" height="24"
                                         viewBox="0 0 45.902 45.902" xml:space="preserve">
                                            <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                                        <g id="SVGRepo_tracerCarrier" stroke-linecap="round"
                                           stroke-linejoin="round"></g>
                                        <g id="SVGRepo_iconCarrier">
                                            <g>
                                                <g>
                                                    <path d="M43.162,26.681c-1.564-1.578-3.631-2.539-5.825-2.742c1.894-1.704,3.089-4.164,3.089-6.912 c0-5.141-4.166-9.307-9.308-9.307c-4.911,0-8.932,3.804-9.281,8.625c4.369,1.89,7.435,6.244,7.435,11.299 c0,1.846-0.42,3.65-1.201,5.287c1.125,0.588,2.162,1.348,3.066,2.26c2.318,2.334,3.635,5.561,3.61,8.851l-0.002,0.067 l-0.002,0.057l-0.082,1.557h11.149l0.092-12.33C45.921,30.878,44.936,28.466,43.162,26.681z"></path>
                                                    <path d="M23.184,34.558c1.893-1.703,3.092-4.164,3.092-6.912c0-5.142-4.168-9.309-9.309-9.309c-5.142,0-9.309,4.167-9.309,9.309 c0,2.743,1.194,5.202,3.084,6.906c-4.84,0.375-8.663,4.383-8.698,9.318l-0.092,1.853h14.153h15.553l0.092-1.714 c0.018-2.514-0.968-4.926-2.741-6.711C27.443,35.719,25.377,34.761,23.184,34.558z"></path>
                                                    <path d="M6.004,11.374v3.458c0,1.432,1.164,2.595,2.597,2.595c1.435,0,2.597-1.163,2.597-2.595v-3.458h3.454 c1.433,0,2.596-1.164,2.596-2.597c0-1.432-1.163-2.596-2.596-2.596h-3.454V2.774c0-1.433-1.162-2.595-2.597-2.595 c-1.433,0-2.597,1.162-2.597,2.595V6.18H2.596C1.161,6.18,0,7.344,0,8.776c0,1.433,1.161,2.597,2.596,2.597H6.004z"></path>
                                                </g>
                                            </g>
                                        </g>
                                        </svg>
                                </button>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>

    <script>
        $(document).on("click", ".add-friend-button", function (event) {
            event.preventDefault()

            var addFriendButton = $(this);

            $.ajax({
                type: "POST",
                url: "/user",
                data: {
                    "friendId": $(this).data("friend-id")
                },
                dataType: "json",
                success: function (resp) {
                    if (resp === true) {
                        addFriendButton.css("background-color", "#fc3f00");
                    } else {
                        addFriendButton.css("background-color", "#007bff");
                    }
                },
                error: function (error) {
                    console.error("Error:", error);
                }
            });
        });

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

                    response.users.forEach(function (user) {
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
                                ),
                                $("<button>").addClass("btn btn-primary add-friend-button ml-auto")
                                    .css("background-color", user.isAdded ? "#fc3f00" : "#007bff")
                                    .attr("data-friend-id", user.userId).append(
                                    $("<svg>").attr({
                                        "xmlns": "http://www.w3.org/2000/svg",
                                        "width": "24",
                                        "height": "24",
                                        "viewBox": "0 0 24 24",
                                        "fill": "none",
                                        "stroke": "currentColor",
                                        "stroke-width": "2",
                                        "stroke-linecap": "round",
                                        "stroke-linejoin": "round"
                                    }).addClass("feather feather-heart").append(
                                        $("<path>").attr("d", "M20 4.58C18.62 3.2 16.76 2.48 14.8 2.97C14.05 3.18 13.35 3.55 12.74 4.04L12 4.74L11.26 4.04C10.65 3.55 9.95 3.17 9.2 2.96C7.24 2.47 5.38 3.19 4 4.57C2.3 6.29 2.3 9.72 4 11.44L12 19.41L20 11.44C21.7 9.72 21.7 6.29 20 4.58z")
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
