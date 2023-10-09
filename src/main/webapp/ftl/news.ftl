<#include "base.ftl">
<#macro title>Index</#macro>
<#macro content>
    <div class="container">
        <div class="row">
            <!-- Featured blog post (large card) -->
            <div class="col-lg-12 mb-4">
                <div class="card">
                    <a href="#!"><img class="card-img-top" src="https://dummyimage.com/1200x500/dee2e6/6c757d.jpg"
                                      alt="..."/></a>
                    <div class="card-body">
                        <div class="small text-muted">January 1, 2023</div>
                        <h2 class="card-title">Featured Post Title</h2>
                        <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reiciendis
                            aliquid
                            atque, nulla? Quos cum ex quis soluta, a laboriosam. Dicta expedita corporis animi vero
                            voluptate voluptatibus possimus, veniam magni quis!</p>
                        <a class="btn btn-primary" href="#!">Read more →</a>
                    </div>
                </div>
            </div>

            <#list all_news as article>
                <div class="col-lg-6 mb-4">
                    <div class="card">
                        <img class="card-img-top" src="${article.imageUrl}" alt="..."/>
                        <div class="card-body">
                            <div class="small text-muted">
                                ${article.publishTime?string("d MMMM, HH:mm")}
                                <span class="float-right">${article.category}</span>
                            </div>
                            <a href="article?title=${article.title}" class="text-decoration-none">
                                <h2 class="card-title h4">${article.title}</h2>
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