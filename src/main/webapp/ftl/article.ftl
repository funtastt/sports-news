<#include "base.ftl">
<#macro title>${article.title}</#macro>
<#macro content>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-9">
                <div class="article">
                    <img src="${article.imageUrl}" alt="${article.title}" class="img-fluid mb-4 mx-auto">
                    <h2 class="display-5">${article.title}</h2>
                    <div class="article-details">
                        <div class="author">
                            <span class="author-label">Author: </span>
                            <a href="#">
                                <img src="${article.authorProfilePicture}" alt="${article.author}" title="${article.author}">
                            </a>
                            <div class="author-name">
                                <a href="#" rel="author" class="author-link">
                                    <span class="author-name">${article.author}</span>
                                </a>
                            </div>
                        </div>
                        <div class="published-date">
                            <time>${article.publishTime?string("dd MMMM yyyy, HH:mm")}</time>
                        </div>
                    </div>
                    <p class="article-content" style="color: #000; margin-top: 10px;">${article.content}</p>
                    <p>Views: ${article.views}</p>

                    <button type="button" id="articleLikeButton"
                            class="btn btn-xs btn-primary" <#if isArticleLiked> style="background-color: #fc3f00;" </#if>>
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-heart">
                            <path d="M20 4.58C18.62 3.2 16.76 2.48 14.8 2.97C14.05 3.18 13.35 3.55 12.74 4.04L12 4.74L11.26 4.04C10.65 3.55 9.95 3.17 9.2 2.96C7.24 2.47 5.38 3.19 4 4.57C2.3 6.29 2.3 9.72 4 11.44L12 19.41L20 11.44C21.7 9.72 21.7 6.29 20 4.58z"></path>
                        </svg>
                        <span id="likesCount">${article.likes}</span>
                    </button>

                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="comments">
                    <div id="commentsSection">
                        <#if comments?has_content>
                            <#list comments as comment>
                                <div class="comment" style="border: 1px solid #ccc; border-radius: 5px; padding: 10px; margin: 10px 0;">
                                    <div class="user-avatar" style="display: flex;">
                                        <img src="<#if comment.profilePictureUrl?has_content>${comment.profilePictureUrl}<#else>https://dummyimage.com/40x40/dee2e6/6c757d.jpg</#if>" alt="User Avatar" width="40" height="40" style="vertical-align: middle;">
                                        <p class="user-id" style="vertical-align: middle; color: #000;">${comment.username}</p>
                                    </div>
                                    <div class="comment-details">
                                        <div class="d-flex justify-content-between">
                                            <div class="user-info">
                                                <p class="comment-text" style="color: #000; word-wrap: break-word;">${comment.text}</p>
                                            </div>
                                            <p class="timestamp" style="color: #888; font-size: 12px;">${comment.sendingTime?string("dd MMMM yyyy, HH:mm")}</p>
                                        </div>
                                        <div style="text-align: right;">
                                            <button type="button" id="commentLikeButton" class="btn btn-xs btn-primary">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-heart">
                                                    <path d="M20 4.58C18.62 3.2 16.76 2.48 14.8 2.97C14.05 3.18 13.35 3.55 12.74 4.04L12 4.74L11.26 4.04C10.65 3.55 9.95 3.17 9.2 2.96C7.24 2.47 5.38 3.19 4 4.57C2.3 6.29 2.3 9.72 4 11.44L12 19.41L20 11.44C21.7 9.72 21.7 6.29 20 4.58z"></path>
                                                </svg>
                                                <span id="likesCount">${comment.likes}</span>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-4">
                <form id="commentForm" method="POST" action="/article">
                    <div class="form-group">
                        <textarea class="form-control" id="comment" name="comment" rows="3" placeholder="Send your comment :)" style="resize: none"></textarea>
                    </div>
                    <div class="text-center">
                        <button type="submit" id="submit-btn" class="btn btn-primary">Send</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script>
        $("#commentForm").submit(function (event) {
            event.preventDefault();

            $.ajax({
                type: "POST",
                url: "/article",
                data: {
                    "action" : "comment",
                    "comment": $("textarea[name='comment']").val()
                },
                success: function (response) {
                    $("textarea[name='comment']").val('');
                    $("#commentsSection").empty();

                    $.each(response, function (index, comment) {
                        var commentDiv = $("<div>").addClass("comment").css({
                            border: "1px solid #ccc",
                            borderRadius: "5px",
                            padding: "10px",
                            margin: "10px 0"
                        });

                        var userAvatar = $("<div>").addClass("user-avatar").css("display", "flex").append(
                            $("<img>").attr("src", comment.profilePictureUrl || "https://dummyimage.com/40x40/dee2e6/6c757d.jpg")
                                .attr("alt", "User Avatar")
                                .attr("width", "40")
                                .attr("height", "40")
                                .css("vertical-align", "middle"),
                            $("<p>").addClass("user-id").css("vertical-align", "middle").text(comment.username)
                        );

                        var commentDetails = $("<div>").addClass("comment-details").append(
                            $("<div>").addClass("d-flex justify-content-between").append(
                                $("<div>").addClass("user-info").append(
                                    $("<p>").addClass("comment-text").text(comment.text)
                                ),
                                $("<p>").addClass("timestamp").text(comment.sendingTime)
                            ),
                            $("<p>").addClass("comment-likes").text("Likes: " + comment.likes)
                        );

                        commentDiv.append(userAvatar, commentDetails);
                        $("#commentsSection").append(commentDiv);
                    });
                },
                error: function (error) {
                    console.error("Error:", error);
                }
            });
        });

        $("#articleLikeButton").click(function(event) {
            event.preventDefault();

            $.ajax({
                type: "POST",
                url: "/article",
                data: {
                    "action": "like"
                },
                dataType: "json",
                success: function(resp) {
                    // Создаем элементы и вставляем их в кнопку
                    var likeIcon = '<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-heart"><path d="M20 4.58C18.62 3.2 16.76 2.48 14.8 2.97C14.05 3.18 13.35 3.55 12.74 4.04L12 4.74L11.26 4.04C10.65 3.55 9.95 3.17 9.2 2.96C7.24 2.47 5.38 3.19 4 4.57C2.3 6.29 2.3 9.72 4 11.44L12 19.41L20 11.44C21.7 9.72 21.7 6.29 20 4.58z"></path></svg>';
                    var likesCount = resp.likesCount;

                    // Обновляем содержимое кнопки
                    $("#articleLikeButton").html(likeIcon + " " + likesCount);

                    if (resp.isArticleLiked) {
                        // Если myBooleanParam равен true, меняем цвет кнопки
                        $("#articleLikeButton").css("background-color", "#fc3f00");
                    } else {
                        $("#articleLikeButton").css("background-color", "#007bff");
                    }
                },
                error: function(error) {
                    // Обработка ошибки (если необходимо)
                    console.error("Ошибка:", error);
                }
            });
        });


        $("textarea[name='comment']").keydown(function (event) {
            if (event.keyCode === 13 && !event.shiftKey) {
                event.preventDefault();
                $("#commentForm").submit();
            }
        });
    </script>

</#macro>
