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

    <div id="commentsSection">
        <#if comments?has_content>
            <#list comments as c>
                ${c.text}  <br>
            </#list>
        </#if>
    </div>

    <form id="commentForm" method="POST" action="/article" enctype="multipart/form-data">
        <textarea name="commentText" rows="4" cols="50" placeholder="Send your comment :)"></textarea>
        <br>
        <input id="submitBtn" type="submit" value="Send">
    </form>

    <script>
        var form = document.getElementById("commentForm");
        var submitBtn = document.getElementById("submitBtn");

        form.addEventListener("submit", function (event) {
            event.preventDefault(); // предотвращает обновление страницы

            var commentText = document.querySelector("textarea[name='commentText']").value;

            var formData = new FormData(form);
            formData.append("commentText", commentText)

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/article", true);
            xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest"); // Добавьте заголовок, чтобы сервер мог определить AJAX-запрос
            function updateCommentsList(comments) {
                var commentsSection = document.getElementById("commentsSection");
                commentsSection.innerHTML = "";

                comments.forEach(function (comment) {
                    var commentElement = document.createElement("div");
                    commentElement.textContent = comment.text;
                    commentsSection.appendChild(commentElement);
                });
            }

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    document.querySelector("textarea[name='commentText']").value = '';
                    var response = JSON.parse(xhr.responseText);

                    updateCommentsList(response);
                }
            };

            xhr.send(formData);
        });


        submitBtn.disabled = false;
    </script>
</#macro>
