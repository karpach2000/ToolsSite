function addUser() {
    var xmlHttp = new XMLHttpRequest();
    var userName = document.getElementById("userName").value
    var sessionId = document.getElementById("sessionId").value
    var sessionPas = document.getElementById("sessionPas").value

    if(userName == "")
    {
        alert("Заполните поле \"Имя пользователя\"")
        return
    }
    else if(sessionId == "")
    {
        alert("Заполните поле \"ID сессии\"")
        return
    }
    else if(sessionPas == "")
    {
        alert("Заполните поле \"Пароль сессии\"")
        return
    }

    //create session
    xmlHttp.open("GET", "/games/spy_add_session?userName="+userName+"&sessionId="+sessionId+
        "&sessionPas="+sessionPas, false); // false for synchronous request
    xmlHttp.send(null);
    if(xmlHttp.responseText=="true") {
        alert("Игра создана.")
    }
    else if(xmlHttp.responseText=="false")
    {
        //alert("Ничего не делаем.")
    }
    else
    {
        xmlHttp.responseText
    }


    //Add user
    xmlHttp.open("GET", "/games/spy_addUser?userName="+userName+"&sessionId="+sessionId+
        "&sessionPas="+sessionPas, false); // false for synchronous request
    xmlHttp.send(null);
    if(xmlHttp.responseText=="true")
    {
        document.getElementById("user").hidden = true
        document.getElementById("beforGame").hidden = false
        //alert("Пользователь добавлен.")
    }
    else
    {
        alert(xmlHttp.responseText)
    }
}



function startGame() {
    var xmlHttp = new XMLHttpRequest();
    var userName = document.getElementById("userName").value
    var sessionId = document.getElementById("sessionId").value
    var sessionPas = document.getElementById("sessionPas").value

    xmlHttp.open("GET", "/games/spy_start_game?userName="+userName+"&sessionId="+sessionId+
        "&sessionPas="+sessionPas, false); // false for synchronous request
    xmlHttp.send(null);
    document.getElementById("game").hidden = false
    document.getElementById("beforGame").hidden = true
    document.getElementById("gamerInformation").textContent = xmlHttp.responseText
    //alert(xmlHttp.responseText)
}
function stopGame() {
    var xmlHttp = new XMLHttpRequest();
    var userName = document.getElementById("userName").value
    var sessionId = document.getElementById("sessionId").value
    var sessionPas = document.getElementById("sessionPas").value
    xmlHttp.open("GET", "/games/spy_stop_game?userName="+userName+"&sessionId="+sessionId+
        "&sessionPas="+sessionPas, false); // false for synchronous request
    xmlHttp.send(null);
    document.getElementById("user").hidden = false
    document.getElementById("game").hidden = true
    //alert(xmlHttp.responseText)

}

function showSpy() {
    var xmlHttp = new XMLHttpRequest();
    var userName = document.getElementById("userName").value
    var sessionId = document.getElementById("sessionId").value
    var sessionPas = document.getElementById("sessionPas").value
    xmlHttp.open("GET", "/games/spy_get_spy?userName="+userName+"&sessionId="+sessionId+
        "&sessionPas="+sessionPas, false); // false for synchronous request
    xmlHttp.send(null);
    alert(xmlHttp.responseText)
}

function isSpyShowen() {
    var xmlHttp = new XMLHttpRequest();
    var userName = document.getElementById("userName").value
    var sessionId = document.getElementById("sessionId").value
    var sessionPas = document.getElementById("sessionPas").value
    xmlHttp.open("GET", "/games/spy_is_spy_showen?userName="+userName+"&sessionId="+sessionId+
        "&sessionPas="+sessionPas, false); // false for synchronous request
    xmlHttp.send(null);
    alert(xmlHttp.responseText)
}