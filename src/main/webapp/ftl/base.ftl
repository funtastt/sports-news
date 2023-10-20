<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title><@title></@title></title>
    <script src="https://code.jquery.com/jquery-latest.min.js"></script>
    <link href="assets/css/style.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
            integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
            integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="assets/css/owl.carousel.min.css">
    <link rel="stylesheet" href="assets/css/ticker-style.css">
    <link rel="stylesheet" href="assets/css/flaticon.css">
    <link rel="stylesheet" href="assets/css/slicknav.css">
    <link rel="stylesheet" href="assets/css/animate.min.css">
    <link rel="stylesheet" href="assets/css/magnific-popup.css">
    <link rel="stylesheet" href="assets/css/fontawesome-all.min.css">
    <link rel="stylesheet" href="assets/css/themify-icons.css">
    <link rel="stylesheet" href="assets/css/slick.css">
    <link rel="stylesheet" href="assets/css/nice-select.css">

</head>

<body>
<header>
    <!-- Header Start -->
    <div class="header-area">
        <div class="main-header">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-xl-10 col-lg-10 col-md-12 header-flex">
                        <div class="main-menu d-none d-md-block">
                            <nav>
                                <ul id="navigation" class="nav nav-pills align-items-center">
                                    <li class="nav-item">
                                        <a href="/news">
                                            <img src="assets/img/logo/logo.png" alt="logo" width="105" height="45">
                                        </a>

                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="/news">News</a>
                                    </li>
                                    <#if isLoggedIn>
                                        <li class="nav-item">
                                            <a class="nav-link" href="/profile">Profile</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="/suggest">Suggest article</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="/find_friends">Find friends</a>
                                        </li>
                                    </#if>
                                    <li class="nav-item">
                                        <a class="nav-link" href="/about">About us</a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>

                    <div class="col-xl-2 col-lg-2 col-md-12 header-flex justify-content-between">
                        <div class="logout-button">
                            <#if isLoggedIn>
                                <a href="/logout">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30"
                                         viewBox="0 0 384.971 384.971">
                                        <path fill="#000000"
                                              d="M180.455,360.91H24.061V24.061h156.394c6.641,0,12.03-5.39,12.03-12.03s-5.39-12.03-12.03-12.03H12.03C5.39,0.001,0,5.39,0,12.031V372.94c0,6.641,5.39,12.03,12.03,12.03h168.424c6.641,0,12.03-5.39,12.03-12.03C192.485,366.299,187.095,360.91,180.455,360.91z"/>
                                        <path fill="#000000"
                                              d="M381.481,184.088l-83.009-84.2c-4.704-4.752-12.319-4.74-17.011,0c-4.704,4.74-4.704,12.439,0,17.179l62.558,63.46H96.279c-6.641,0-12.03,5.438-12.03,12.151c0,6.713,5.39,12.151,12.03,12.151h247.74l-62.558,63.46c-4.704,4.752-4.704,12.439,0,17.179c4.704,4.752,12.319,4.752,17.011,0l82.997-84.2C386.113,196.588,386.161,188.756,381.481,184.088z"/>
                                    </svg>
                                </a>
                            <#else>
                                <a href="/login">
                                    <svg fill="#000000" height="50" width="50" version="1.1" id="Capa_1"
                                         xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
                                         viewBox="0 0 273.5 273.5" xml:space="preserve"><g id="SVGRepo_bgCarrier"
                                                                                           stroke-width="0"></g>
                                        <g id="SVGRepo_tracerCarrier" stroke-linecap="round"
                                           stroke-linejoin="round"></g>
                                        <g id="SVGRepo_iconCarrier">
                                            <path d="M99.433,128.202v17.096c0,2.297-1.869,4.167-4.167,4.167h-5.253c-2.297,0-4.167-1.869-4.167-4.167v-17.096 c0-2.297,1.869-4.167,4.167-4.167h5.253C97.564,124.036,99.433,125.905,99.433,128.202z M273.5,109.125v55.25 c0,11.786-9.589,21.375-21.375,21.375H21.375C9.589,185.75,0,176.161,0,164.375v-55.25C0,97.339,9.589,87.75,21.375,87.75h230.75 C263.911,87.75,273.5,97.339,273.5,109.125z M68.161,156.965c0-4.142-3.358-7.5-7.5-7.5H42.433v-32.929c0-4.142-3.358-7.5-7.5-7.5 s-7.5,3.358-7.5,7.5v40.429c0,4.142,3.358,7.5,7.5,7.5h25.728C64.803,164.465,68.161,161.107,68.161,156.965z M114.433,128.202 c0-10.568-8.598-19.167-19.167-19.167h-5.253c-10.568,0-19.167,8.598-19.167,19.167v17.096c0,10.568,8.598,19.167,19.167,19.167 h5.253c10.569,0,19.167-8.598,19.167-19.167V128.202z M165.447,145.298c0-4.142-3.358-7.5-7.5-7.5h-6.822 c-4.142,0-7.5,3.358-7.5,7.5c0,1.542,0.465,2.975,1.263,4.167h-3.86c-2.297,0-4.167-1.869-4.167-4.167v-17.096 c0-2.297,1.869-4.167,4.167-4.167h5.253c1.303,0,2.217,0.565,2.754,1.04c3.106,2.742,7.845,2.446,10.586-0.659 c2.741-3.105,2.446-7.845-0.659-10.586c-3.502-3.092-8.006-4.794-12.681-4.794h-5.253c-10.568,0-19.167,8.598-19.167,19.167v17.096 c0,10.568,8.598,19.167,19.167,19.167h5.253C156.849,164.465,165.447,155.867,165.447,145.298z M187.68,116.536 c0-4.142-3.358-7.5-7.5-7.5s-7.5,3.358-7.5,7.5v40.429c0,4.142,3.358,7.5,7.5,7.5s7.5-3.358,7.5-7.5V116.536z M243.43,116.536 c0-4.142-3.358-7.5-7.5-7.5s-7.5,3.358-7.5,7.5v18.6l-18.084-23.209c-1.968-2.527-5.323-3.525-8.353-2.483 c-3.029,1.041-5.063,3.89-5.063,7.093v40.429c0,4.142,3.358,7.5,7.5,7.5s7.5-3.358,7.5-7.5v-18.6l18.084,23.209 c1.449,1.86,3.649,2.891,5.917,2.891c0.813,0,1.636-0.133,2.436-0.407c3.029-1.041,5.063-3.891,5.063-7.093V116.536z"></path>
                                        </g></svg>
                                </a>

                            </#if>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="mobile_menu d-block d-md-none"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Header End -->
</header>


<div class="container">
    <@content></@content>
</div>
</body>
</html>
