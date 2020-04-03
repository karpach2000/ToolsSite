function addUser() {
    var xmlHttp = new XMLHttpRequest();
    var userName = document.getElementById("userName").value
    xmlHttp.open("GET", "/spy_addUser?userName="+userName, false); // false for synchronous request
    xmlHttp.send(null);
    if(xmlHttp.responseText=="true")
    {
        document.getElementById("user").hidden = true
        document.getElementById("beforGame").hidden = false
        //alert("Пользователь добавлен.")
    }
    else
    {
        alert("Введеное имя пользователя слишком короткое или пользователь с данным именем уже существует")
    }
}

function startGame() {
    var xmlHttp = new XMLHttpRequest();
    var userName = document.getElementById("userName").value
    xmlHttp.open("GET", "/spy_start_game?userName="+userName, false); // false for synchronous request
    xmlHttp.send(null);
    document.getElementById("game").hidden = false
    document.getElementById("beforGame").hidden = true
    document.getElementById("gamerInformation").textContent = xmlHttp.responseText
    //alert(xmlHttp.responseText)
}
function stopGame() {
    var xmlHttp = new XMLHttpRequest();
    var userName = document.getElementById("userName").value
    xmlHttp.open("GET", "/spy_stop_game?userName="+userName, false); // false for synchronous request
    xmlHttp.send(null);
    document.getElementById("user").hidden = false
    document.getElementById("game").hidden = true
    //alert(xmlHttp.responseText)

}

function showSpy() {
    var xmlHttp = new XMLHttpRequest();
    var userName = document.getElementById("userName").value
    xmlHttp.open("GET", "/spy_get_spy?userName="+userName, false); // false for synchronous request
    xmlHttp.send(null);
    alert(xmlHttp.responseText)
}

function isSpyShowen() {
    var xmlHttp = new XMLHttpRequest();
    var userName = document.getElementById("userName").value
    xmlHttp.open("GET", "/spy_is_spy_showen?userName="+userName, false); // false for synchronous request
    xmlHttp.send(null);
    alert(xmlHttp.responseText)
}