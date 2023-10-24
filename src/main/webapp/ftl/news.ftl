<#include "base.ftl">
<#macro title>Index</#macro>
<#macro content>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-10 mb-4">
                <div class="dropdown">
                    <div id="dropdownFilter" style="cursor: pointer; text-align: right;" class="dropdown-toggle mb-3" data-bs-toggle="dropdown" aria-expanded="false">
                        <a loading="lazy">
                            ${sortType}
                        </a>
                    </div>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownFilter">
                        <li style="--delay: 1;"><a class="dropdown-item" href="/news?sort=0">Newest</a></li>
                        <li style="--delay: 2"><a class="dropdown-item" href="/news?sort=1">Most viewed</a></li>
                        <li style="--delay: 3"><a class="dropdown-item" href="/news?sort=2">Most liked</a></li>
                    </ul>
                </div>
                <div class="card">
                    <img style="height: 600px;" class="card-img-top" src="${mostViewed.imageUrl}"
                         alt="${mostViewed.title}"/>

                    <div class="card-body">
                        <small class="text-muted">
                            ${mostViewed.publishTime?string("d MMMM, HH:mm")}
                            <a href="/news?category=${mostViewed.category}">
                                <span class="float-right">${mostViewed.category}</span>
                            </a>
                        </small>
                        <a href="/article?articleId=${mostViewed.articleId}">
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

            <#list allNews as article>
                <div class="col-lg-6 mb-4">
                    <div class="card">
                        <img class="card-img-top" src="${article.imageUrl}" alt="Image">
                        <div class="card-body">
                            <small class="text-muted">
                                ${article.publishTime?string("d MMMM, HH:mm")}
                                <a href="/news?category=${article.category}">
                                    <span class="float-right">${article.category}</span>
                                </a>
                            </small>
                            <a href="article?title=${article.title}" class="text-decoration-none">
                                <h2 class="card-title h4" style="color: #000; transition: all .2s ease-out 0s;"
                                    onmouseover="this.style.color='#fc3f00';"
                                    onmouseout="this.style.color='#000';">${article.title}</h2>
                            </a>
                            <a href="article?title=${article.title}" class="text-decoration-none">
                                <p class="card-text">${article.summary}</p>
                            </a>
                            <a class="btn btn-primary" href="article?articleId=${article.articleId}">Read more</a>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </div>
</#macro>