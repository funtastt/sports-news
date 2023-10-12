<#include "base.ftl">
<#macro title>${article.title}</#macro>
<#macro content>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-9">
                <div class="article">
                    <img src="${article.imageUrl}" alt="${article.title}" class="img-fluid mb-4">
                    <h1 class="display-4">${article.title}</h1>
                    <p class="text-muted">Published Date: ${article.publishTime?string("dd.MM.yyyy - HH:mm")}, Author: ${article.author}</p>
                    <p>Category: ${article.category}</p>
                    <p class="article-content">${article.content}</p>
                    <p>Source: <a href="http://${article.sourceUrl}">${article.sourceUrl}</a></p>
                    <p>Views: ${article.views}</p>
                    <p>Likes: ${article.likes}</p>
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
                                <div class="comment"  style="border: 1px solid #ccc; border-radius: 5px; padding: 10px; margin: 10px 0;">
                                    <div class="user-avatar" style="display: flex;">
                                        <img src="<#if comment.profilePictureUrl?has_content>${comment.profilePictureUrl}<#else>https://dummyimage.com/40x40/dee2e6/6c757d.jpg</#if>" alt="User Avatar" width="40" height="40" style="vertical-align: middle;">
                                        <p class="user-id" style="vertical-align: middle;">${comment.username}</p>
                                    </div>
                                    <div class="comment-details">
                                        <div class="d-flex justify-content-between">
                                            <div class="user-info">
                                                <p class="comment-text">${comment.text}</p>
                                            </div>
                                            <p class="timestamp">Posted: ${comment.sendingTime?string("dd.MM.yyyy - HH:mm")}</p>
                                        </div>
                                        <p class="comment-likes">Likes: ${comment.likes}</p>
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

        $("textarea[name='comment']").keydown(function (event) {
            if (event.keyCode === 13 && !event.shiftKey) {
                event.preventDefault();
                $("#commentForm").submit();
            }
        });
    </script>


</#macro>
