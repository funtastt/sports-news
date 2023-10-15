<#include "base.ftl">
<#macro title>Index</#macro>
<#macro content>
    <div class="container">
        <div class="row">
            <div class="col-lg-12 mb-4">
                <div class="card">
                    <img style="height: 600px;" class="card-img-top" src="${mostViewed.imageUrl}"
                         alt="${mostViewed.title}"/>

                    <div class="card-body">
                        <small class="text-muted">
                            ${mostViewed.publishTime?string("d MMMM, HH:mm")}
                            <span class="float-right">${mostViewed.category}</span>
                        </small>
                        <a href="/article?title=${mostViewed.title}">
                            <h2 class="card-title" style="color: #000; transition: all .2s ease-out 0s;"
                                onmouseover="this.style.color='#fc3f00';"
                                onmouseout="this.style.color='#000';">${mostViewed.title}</h2>
                        </a>
                        <a href="/article?title=${mostViewed.title}">
                            <p class="card-text">${mostViewed.summary}</p>
                        </a>
                        <a class="btn btn-primary" href="article?title=${mostViewed.title}">Read more</a>
                    </div>
                </div>
            </div>

            <#list all_news as article>
                <div class="col-lg-6 mb-4">
                    <div class="card">
                        <img class="card-img-top" src="${article.imageUrl}" alt="Image">
                        <div class="card-body">
                            <small class="text-muted">
                                ${article.publishTime?string("d MMMM, HH:mm")}
                                <span class="float-right">${article.category}</span>
                            </small>
                            <a href="article?title=${article.title}" class="text-decoration-none">
                                <h2 class="card-title h4" style="color: #000; transition: all .2s ease-out 0s;"
                                    onmouseover="this.style.color='#fc3f00';"
                                    onmouseout="this.style.color='#000';">${article.title}</h2>
                            </a>
                            <a href="article?title=${article.title}" class="text-decoration-none">
                                <p class="card-text">${article.summary}</p>
                            </a>
                            <a class="btn btn-primary" href="article?title=${article.title}">Read more</a>
                        </div>
                    </div>
                </div>
            </#list>


        </div>
    </div>
</#macro>