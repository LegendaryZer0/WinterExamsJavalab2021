<#ftl encoding="UTF-8"/>
<#import "mcrs/bodyScripts.ftl" as b>

<#import "mcrs/headScripts.ftl" as h>

<html>
<head>


    <title>$Title$</title>

    <@h.headScript></@h.headScript>



</head>
<body>

<div class="container">
    <div id="main">


        <div class="row" id="real-estates-detail">
            <div class="col-lg-4 col-md-4 col-xs-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <header class="panel-title">
                            <div class="text-center">
                                <strong>Пользователь сайта</strong>
                            </div>
                        </header>
                    </div>
                    <div class="panel-body">
                        <div class="text-center" id="author">
                            <img id="avatar" src="https://otvet.imgsmail.ru/download/249189998_8236d81a89da1a8719bf4d71eb60dd01_800.jpg" height="400" width="300">
                            <#if !user??>
                                <script>window.location.replace('selfProfile');</script>
                            </#if>
                            <#if user.nickname??>
                            <h3 id="viewNickname">
                                    ${user.nickname}

                            </h3>
                            <#else>
                            <h3 id="viewNickname">   ${user.email}</h3>
                            </#if>



                            <small class="label label-warning">Российская Федерация</small>
                            <p>.</p>
                            <p class="sosmed-author">
                                <a href="#"><i class="fa fa-facebook" title="Facebook"></i></a>
                                <a href="#"><i class="fa fa-twitter" title="Twitter"></i></a>
                                <a href="#"><i class="fa fa-google-plus" title="Google Plus"></i></a>
                                <a href="#"><i class="fa fa-linkedin" title="Linkedin"></i></a>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-8 col-md-8 col-xs-12">
                <div class="panel">
                    <div class="panel-body">
                        <ul id="myTab" class="nav nav-pills">
                            <li class="active"><a href="#detail" data-toggle="tab">О пользователе</a></li>
                            <li class=""><a href="#contact" data-toggle="tab">Отправить сообщение</a></li>
                            <li class=""><a href="#settings" data-toggle="tab">Настройки профиля</a></li>
                        </ul>
                        <a href = "${Request.reqObj.contextPath}/messenger" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Мессенджер</a>
                        <div id="myTabContent" class="tab-content">
                            <hr>
                            <div class="tab-pane fade active in" id="detail">
                                <h4>История профиля</h4>
                                <table class="table table-th-block">
                                    <tbody>
                                    <tr><td class="active">Зарегистрирован:</td><td>12-06-2016</td></tr>
                                    <tr><td class="active">Последняя активность:</td><td>12-06-2016 / 09:11</td></tr>
                                    <tr><td class="active">Страна:</td><td>Россия</td></tr>
                                    <tr><td class="active">Город:</td><td>Волгоград</td></tr>
                                    <tr><td class="active">Пол:</td><td>Мужской</td></tr>
                                    <tr><td class="active">Полных лет:</td><td>43</td></tr>
                                    <tr><td class="active">Семейное положение:</td><td>Женат</td></tr>
                                    <tr><td class="active">Рейтинг пользователя:</td><td><i class="fa fa-star" style="color:red"></i> <i class="fa fa-star" style="color:#ff0000"></i> <i class="fa fa-star" style="color:red"></i> <i class="fa fa-star" style="color:red"></i> 4/5</td></tr>
                                    <tr><td class="active">Плагин рейтинга:</td><td><a href="https://bootstraptema.ru/stuff/plugins_bootstrap/improvement/bootstrap_star_rating/12-1-0-73" target="_blank">http://goo.gl/bGGXuw</a></td></tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="tab-pane fade" id="contact">
                                <p></p>
                                <form role="form" method="post" action="sendMessage">
                                    <input id = 'csrf-token' type="hidden" name="_csrf_token" value="${_csrf_token}" />
                                    <div class="form-group">
                                        <input type="text" class="form-control rounded" placeholder="Укажите Ваше Имя" name="from-email" value="${user.email} " hidden>

                                    </div>

                                    <#if user.phone??>
                                    <div class="form-group">
                                        <input type="text" name="from-phone" class="form-control rounded" placeholder="(+7)(095)123456" value="${user.phone}" hidden>
                                    </div>
                                    </#if>
                                    <#if user.nickname??>
                                    <div class="form-group">
                                        <input type="text" name="from-nickname" class="form-control rounded" placeholder="(+7)(095)123456" value="${user.nickname}" hidden>
                                    </div>
                                    </#if>

                                    <div class="form-group">
                                        <label>Ник принимающего(опционально)</label>
                                        <input type="text" class="form-control rounded" placeholder="Укажите  Имя" name="to-nickname">
                                    </div>
                                    <div class="form-group">
                                        <label>email адрес адресата(обязательно)</label>
                                        <input type="email" class="form-control rounded" placeholder="Ваш Е-майл" name="to-email">
                                    </div>
                                    <div class="form-group">
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox"> Согласен с условиями
                                            </label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label>Текст Вашего сообщения</label>
                                        <textarea class="form-control rounded" style="height: 100px;" name="message"></textarea>
                                        <p class="help-block">Текст сообщения будет отправлен пользователю</p>
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-success" data-original-title="" title="">Отправить</button>
                                    </div>
                                </form>
                            </div>


                            <div class="tab-pane fade" id="settings">
                                <p></p>
                                <form role="form" method="post" action="usettings">
                                    <div class="form-group">
                                        <label>Псевдоним</label>
                                        <input type="text" name="nickname" class="form-control rounded" id="nickname" placeholder="Укажите Ваше Имя" value=<#if user.nickname??>"${user.nickname}" <#else>""</#if>>
                                    </div>
                                    <div class="form-group">
                                        <label>Пароль</label>
                                        <input type="text" name="password" class="form-control rounded" placeholder="Укажите Ваше Имя" value="${user.password}">
                                    </div>
                                    <#if user.phone??>
                                    <div class="form-group">
                                        <label>Ваш телефон</label>
                                        <input type="text" name="phone" class="form-control rounded" placeholder="(+7)(095)123456" value="${user.phone}">
                                    </div>
                                    </#if>
                                    <div class="form-group">
                                        <label>E-mail адрес</label>
                                        <input type="text" name="email" class="form-control rounded" placeholder="Ваш Е-майл" value="${user.email}">
                                    </div>

                                    <div class="form-group">
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox"> Согласен с условиями
                                            </label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label>О чём вы думаете?</label>
                                        <textarea class="form-control rounded" style="height: 100px;"></textarea>
                                        <p class="help-block">Все смогут это увидеть</p>
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-success" data-original-title="" title="" id="saveChanges">Сохранить</button>
                                    </div>
                                </form>
                            </div>
                            <div class="tab-pane fade" id="messenger">

                            </div>



                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div><!-- /.main -->
<!-- /.container -->

<@b.bodyScript></@b.bodyScript>

<script src="${reqObj.contextPath}/js/profile.js">
</script>


</body>
</html>
