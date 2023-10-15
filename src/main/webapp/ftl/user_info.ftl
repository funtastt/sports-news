<#include "base.ftl">
<#macro title>Index</#macro>
<#macro content>
    <section class="section about-section" id="about">
        <div class="container">
            <div class="row align-items-center flex-row">
                <div class="col-lg-6">
                    <div class="about-avatar">
                        <img class="rounded-circle" src="${user.profilePicture}" title="${user.username}'s avatar"
                             alt="${user.username}'s avatar" width="450" height="450">
                    </div>
                </div>

                <div class="col-lg-6">
                    <div class="about-text go-to">
                        <h3 class="dark-color display-4">${user.username}</h3>
                        <p class="display-5">${user.bio}</p>
                        <div class="row about-list">
                            <div class="col-md-6">
                                <div class="media">
                                    <p class="display-6">First name: ${user.firstName}</p>
                                </div>
                                <div class="media">
                                    <p class="display-6">Last name: ${user.lastName}</p>
                                </div>
                                <div class="media">
                                    <label></label>
                                    <p class="display-6">Address: ${user.country}, ${user.city}</p>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="media">
                                    <p class="display-6">Age: ${user.age}</p>
                                </div>
                                <div class="media">
                                    <p class="display-6">Email: ${user.email}</p>
                                </div>
                                <div class="media">
                                    <p class="display-6">Gender: <#if user.isMale>Male<#else>Female</#if></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</#macro>
