<#include "base.ftl">
<#macro title>${article.title}</#macro>
<#macro content>
    <h1>${article.title}</h1>
    <img src="${article.imageUrl}" alt="${article.title}">
    <p>${article.content}</p>
    <p>Author: ${article.author}</p>
    <p>Published Date: ${article.publishTime?string("dd.MM.yyyy - HH:mm")}</p>
    <p>Category: ${article.category}</p>
    <p>Source: <a href="http://${article.sourceUrl}">${article.sourceUrl}</a></p>
    <p>Views: ${article.views}</p>
    <p>Likes: ${article.likes}</p>

    <form id="commentForm" method="POST" action="/article">
        <div class="form-group">
            <input type="text" class="form-control" id="comment" name="comment" placeholder="Send your comment :)">
        </div>
        <input id="submit-btn" type="submit" value="Send">
    </form>

    <div id="commentsSection">
        <#if comments?has_content>
            <#list comments as c>
                ${c.text}  <br>
            </#list>
        </#if>
    </div>

    <script>
        $("#commentForm").submit(function (event) {
            event.preventDefault();

            $.ajax({
                type: "POST",
                url: "/article",
                data: {
                    "comment": $("input[name='comment']").val()
                },
                success: function (response) {
                    $("input[name='comment']").val('');
                    $("#commentsSection").empty();

                    $.each(response, function (index, comment) {
                        $("<div>").text(comment.text).appendTo("#commentsSection");
                    });
                },
                error: function (error) {
                    console.error("Error:", error);
                }
            });
        });
    </script>
</#macro>
