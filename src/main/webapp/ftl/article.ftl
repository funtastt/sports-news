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
                            <span class="author-label font-weight-bold">Author: </span>
                            <a href="/user?userId=${article.authorId}">
                                <img width="48" height="48" src="${article.authorProfilePicture}"
                                     alt="${article.author}"
                                     title="${article.author}" class="rounded-circle">
                            </a>
                            <div class="author-name">
                                <a href="/user?userId=${article.authorId}" rel="author" class="author-link">
                                    <span class="author-name font-weight-bold">${article.author}</span>
                                </a>
                            </div>
                        </div>
                        <div class="published-date">
                            <time>${article.publishTime?string("dd MMMM yyyy, HH:mm")}</time>
                        </div>
                    </div>
                    <div class="d-flex justify-content-between text-wrap">
                        <p class="article-content text-break text-justify" style="color: #000; margin-top: 10px;">${article.content}</p>
                    </div>
                    <p>Views: ${article.views}</p>

                    <#if isLoggedIn>
                        <button type="button" id="articleLikeButton"
                                class="btn btn-xs btn-primary" <#if isArticleLiked> style="background-color: #fc3f00;" </#if>>
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                 fill="none"
                                 stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                                 class="feather feather-heart">
                                <path d="M20 4.58C18.62 3.2 16.76 2.48 14.8 2.97C14.05 3.18 13.35 3.55 12.74 4.04L12 4.74L11.26 4.04C10.65 3.55 9.95 3.17 9.2 2.96C7.24 2.47 5.38 3.19 4 4.57C2.3 6.29 2.3 9.72 4 11.44L12 19.41L20 11.44C21.7 9.72 21.7 6.29 20 4.58z"></path>
                            </svg>
                            <span id="likesCount">${article.likes}</span>
                        </button>
                    </#if>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div id="commentsSection">
                    <#if comments?has_content>
                        <#list comments as comment>
                            <div class="comment card"
                                 style="border: 1px solid #ccc; padding: 10px; margin: 10px 0;">
                                <a href="/user?userId=${comment.userId}">
                                    <div class="justify-content-between d-flex">
                                        <div class="user-avatar">
                                            <img src="<#if comment.profilePictureUrl?has_content>${comment.profilePictureUrl}<#else>https://dummyimage.com/40x40/dee2e6/6c757d.jpg</#if>"
                                                 alt="User Avatar" width="48" height="48" style="margin-right: 8px;"
                                                 class="rounded-circle">
                                            <span class="user-id font-weight-bold"
                                                  style="color: #000;">${comment.username}</span>
                                        </div>
                                        <p class="timestamp font-weight-light m-1 text-right"
                                           style="font-size: 12px;">${comment.sendingTime?string("dd MMMM yyyy, HH:mm")}</p>
                                    </div>

                                </a>
                                <div class="comment-details">
                                    <div class="d-flex justify-content-between text-wrap">
                                        <p class="comment-text text-break text-justify m-1"
                                           style="color: #000;">${comment.text}</p>
                                    </div>
                                    <div style="text-align: right;">
                                        <#if isLoggedIn>
                                            <button type="button" class="btn btn-xs btn-primary comment-like-button"
                                                    data-comment-id="${comment.commentId}"
                                                    style="background-color:  <#if comment.isLiked> #fc3f00<#else> #007bff</#if>;">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                                     viewBox="0 0 24 24" fill="none" stroke="currentColor"
                                                     stroke-width="2"
                                                     stroke-linecap="round" stroke-linejoin="round"
                                                     class="feather feather-heart">
                                                    <path d="M20 4.58C18.62 3.2 16.76 2.48 14.8 2.97C14.05 3.18 13.35 3.55 12.74 4.04L12 4.74L11.26 4.04C10.65 3.55 9.95 3.17 9.2 2.96C7.24 2.47 5.38 3.19 4 4.57C2.3 6.29 2.3 9.72 4 11.44L12 19.41L20 11.44C21.7 9.72 21.7 6.29 20 4.58z"></path>
                                                </svg>
                                                <span class="likes-count">${comment.likes}</span>
                                            </button>
                                        </#if>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </#if>
                </div>
            </div>
        </div>
    </div>
    <#if isLoggedIn>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-4">
                    <form id="commentForm" method="POST" action="/article">
                        <div class="form-group">
                        <textarea class="form-control" id="comment" name="comment" rows="3"
                                  placeholder="Send your comment :)" style="resize: none"></textarea>
                        </div>
                        <div class="text-center">
                            <button type="submit" id="submit-btn" class="btn btn-primary">Send</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </#if>
    <script>
        $("#commentForm").submit(function (event) {
            event.preventDefault();

            var commentText = $("textarea[name='comment']").val();

            $.ajax({
                type: "POST",
                url: "/article",
                data: {
                    "action": "comment",
                    "comment": commentText
                },
                dataType: "json",
                success: function (response) {
                    $("textarea[name='comment']").val('');

                    var commentDiv = $("<div>").addClass("comment").css({
                        border: "1px solid #ccc",
                        borderRadius: "5px",
                        padding: "10px",
                        margin: "10px 0"
                    });

                    var userAvatar = $("<a>").attr("href", "/user?userId=" + response.userId).append(
                        $("<div>").addClass("user-avatar").css("display", "flex").append(
                            $("<img>").attr("src", response.profilePictureUrl || "https://dummyimage.com/40x40/dee2e6/6c757d.jpg")
                                .attr("alt", "User Avatar")
                                .attr("width", "48")
                                .attr("height", "48")
                                .css("vertical-align", "middle"),
                            $("<span>").addClass("user-id font-weight-bold").css("vertical-align", "middle").css("color", "#000").text(response.username)
                        ).append(
                            $("<p>").addClass("timestamp font-weight-light m-1 text-right").css("font-size", "12px").text(response.sendingTime)
                        )
                    );

                    var commentDetails = $("<div>").addClass("comment-details").append(
                        $("<div>").addClass("d-flex justify-content-between text-wrap").append(
                            $("<p>").addClass("comment-text text-break text-justify m-1").css("color", "#000").text(commentText)
                        ),
                    );

                    var likeButton = $("<button>").attr("type", "button").addClass("btn btn-xs btn-primary comment-like-button")
                        .attr("data-comment-id", response.commentId).css("background-color", response.isLiked ? "#fc3f00" : "")

                    var likeIcon = '<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-heart"><path d="M20 4.58C18.62 3.2 16.76 2.48 14.8 2.97C14.05 3.18 13.35 3.55 12.74 4.04L12 4.74L11.26 4.04C10.65 3.55 9.95 3.17 9.2 2.96C7.24 2.47 5.38 3.19 4 4.57C2.3 6.29 2.3 9.72 4 11.44L12 19.41L20 11.44C21.7 9.72 21.7 6.29 20 4.58z"></path></svg>';

                    likeButton.html(likeIcon + " " + 0);

                    var buttonDiv = $("<div>").css("text-align", "right").append(likeButton);

                    commentDetails.append(buttonDiv);
                    commentDiv.append(userAvatar, commentDetails);

                    $("#commentsSection").append(commentDiv);
                },
                error: function (error) {
                    console.error("Error:", error);
                }
            });
        });

        $("#articleLikeButton").click(function (event) {
            event.preventDefault();

            $.ajax({
                type: "POST",
                url: "/article",
                data: {
                    "action": "articleLike"
                },
                dataType: "json",
                success: function (resp) {
                    var likeIcon = '<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-heart"><path d="M20 4.58C18.62 3.2 16.76 2.48 14.8 2.97C14.05 3.18 13.35 3.55 12.74 4.04L12 4.74L11.26 4.04C10.65 3.55 9.95 3.17 9.2 2.96C7.24 2.47 5.38 3.19 4 4.57C2.3 6.29 2.3 9.72 4 11.44L12 19.41L20 11.44C21.7 9.72 21.7 6.29 20 4.58z"></path></svg>';
                    var likesCount = resp.likesCount;

                    $("#articleLikeButton").html(likeIcon + " " + likesCount);

                    if (resp.isArticleLiked) {
                        $("#articleLikeButton").css("background-color", "#fc3f00");
                    } else {
                        $("#articleLikeButton").css("background-color", "#007bff");
                    }
                },
                error: function (error) {
                    console.error("Ошибка:", error);
                }
            });
        });

        $(document).on("click", ".comment-like-button", function (event) {
            event.preventDefault();

            var commentId = $(this).data("comment-id");
            var $commentButton = $(this);

            $.ajax({
                type: "POST",
                url: "/article",
                data: {
                    "action": "commentLike",
                    "commentId": commentId
                },
                dataType: "json",
                success: function (resp) {
                    var likeIcon = '<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-heart"><path d="M20 4.58C18.62 3.2 16.76 2.48 14.8 2.97C14.05 3.18 13.35 3.55 12.74 4.04L12 4.74L11.26 4.04C10.65 3.55 9.95 3.17 9.2 2.96C7.24 2.47 5.38 3.19 4 4.57C2.3 6.29 2.3 9.72 4 11.44L12 19.41L20 11.44C21.7 9.72 21.7 6.29 20 4.58z"></path></svg>';
                    var likesCount = resp.likesCount;

                    $commentButton.html(likeIcon + " " + likesCount);

                    if (resp.isCommentLiked) {
                        $commentButton.css("background-color", "#fc3f00");
                    } else {
                        $commentButton.css("background-color", "#007bff");
                    }
                },
                error: function (error) {
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


        function handleCommentWidthChange() {
            $('.comment').each(function () {
                var textElement = $(this).find('.comment-text');
                textElement.css('white-space', 'normal');
            });
        }

        function handleArticleWidthChange() {
            var article = $('.article');
            var articleContent = article.find('.article-content');
            var thresholdWidth = 500;

            if (article.width() < thresholdWidth) {
                articleContent.css('white-space', 'normal');
            } else {
                articleContent.css('white-space', 'nowrap');
            }
        }

        $(window).on('resize', handleArticleWidthChange);
        $(window).on('resize', handleCommentWidthChange);

        handleArticleWidthChange();
        handleCommentWidthChange();

    </script>

</#macro>
