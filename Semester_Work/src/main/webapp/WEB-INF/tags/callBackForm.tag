<%@ tag pageEncoding="UTF-8" %>
<form action="${pageContext.request.contextPath}/assets/comingSoonPage/index.html">

    <div class="form-group">
        <label for="exampleFormControlSelect2">Выберите тип проблемы</label>
        <select multiple class="form-control" id="exampleFormControlSelect2">
            <option>Не открывается страница(404)</option>
            <option>Сервер перенаправляет на другую страницу</option>
            <option>Не проходит регистрация</option>
            <option>Элементы сайта не отвечают</option>
            <option>другое</option>
        </select>
    </div>
    <div class="form-group">
        <label for="exampleFormControlTextarea1">Опишите проблему</label>
        <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
    </div>
    <div  class="col-auto">
        <button  type="submit" class="btn btn-primary mb-2">отправить</button>
    </div>
</form>