<#include "base.ftl">

<#macro title>Suggest an Article</#macro>

<#macro content>
    <section class="section suggest-article-section" id="suggest-article">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <h2 class="section-title">Suggest an Article</h2>
                    <form action="/suggest" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="articleTitle">Title:</label>
                            <input type="text" id="articleTitle" name="articleTitle" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="articleContent">Article Content:</label>
                            <textarea id="articleContent" name="articleContent" class="form-control" rows="5" required></textarea>
                        </div>
                        <div class="form-group">
                            <label for="articleSummary">Summary:</label>
                            <input type="text" id="articleSummary" name="articleSummary" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label for="articleCategory">Category:</label>
                            <select id="articleCategory" name="articleCategory" class="form-control" required>
                                <option value="Football">Football</option>
                                <option value="Basketball">Basketball</option>
                                <option value="Tennis">Tennis</option>
                                <option value="Cyber-sport">Cyber Sport</option>
                                <option value="Hockey">Hockey</option>
                                <option value="Volleyball">Volleyball</option>
                                <option value="Other">Other</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="articleImage">Article Image:</label>
                            <input type="file" id="articleImage" name="articleImage" accept="image/*" class="form-control-file" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    </section>
</#macro>
