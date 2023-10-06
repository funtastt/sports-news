<#include "base.ftl">
<#macro title>Index</#macro>
<#macro content>
    <h1>News</h1>
    <ul>
        <#list all_news as article>
            <li>
                <a href="article?title=${article.title}"><h3>${article.title}</h3></a>
                <p>${article.content}</p>
                <p>Author: ${article.author}</p>
                <p>Published Date: ${article.publishTime?string("dd.MM.yyyy - HH:mm")}</p>
                <p>Category: ${article.category}</p>
                <p>Source: <a href="http://${article.sourceUrl}">${article.sourceUrl}</a></p>
                <p>Views: ${article.views}</p>
                <p>Likes: ${article.likes}</p>
            </li>
        </#list>
    </ul>
</#macro>
