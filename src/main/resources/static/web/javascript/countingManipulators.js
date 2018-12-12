//количество ужедобавленных сочленений
var unitCount=0;

//добавить сочленение
function addUnit() {
    var lx = document.getElementById("lx").value;
    var ly = document.getElementById("ly").value;
    var lz = document.getElementById("lz").value;

    var ox = document.getElementById("ox").value;
    var oy = document.getElementById("oy").value;
    var oz = document.getElementById("oz").value;
    countUnits();
    document.getElementById("manipulatorFormul").value=document.getElementById("manipulatorFormul").value
        +"us"+unitCount+"lx"+lx+"ly"+ly+"lz"+lz+"ox"+ox+"oy"+oy+"oz"+oz;

}

//получить ориентацию конечного звена
function getFinalLinkOrientation() {
    var xhr = new XMLHttpRequest();

    var params =
        'manipulatorFormul=' + encodeURIComponent(document.getElementById("manipulatorFormul").value);
    xhr.open("GET", '/getFinalLinkOrientation?' + params, false);
    xhr.send();
   if(xhr.status!=200)
   {
       document.getElementById("finalLinkOrientation").textContent="Ошибка сервера! Код ошибки "+xhr.status;
       //document.getElementById("finalLinkOrientation").textContent = xhr.responseText;
   }
   else {
       document.getElementById("finalLinkOrientation").textContent = xhr.responseText;
   }


}

//посчитать количество сочленений из формуллы сочленеий
function  countUnits() {
    var formul=document.getElementById("manipulatorFormul").value;
    var splits=formul.split("us");
    unitCount=splits.length;
}