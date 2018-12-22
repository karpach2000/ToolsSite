var currentMenuCountingItem="main";
function menuClick(id) {
    showMenuContent(id);
}

function showMenuContent(id) {

    document.getElementById(currentMenuCountingItem).hidden=true;
    document.getElementById(id).hidden=false;
    currentMenuCountingItem=id;

}


function ropeSlack() {
    try {
        var l = document.getElementById("l").value;
        var h = document.getElementById("h").value;
        var g0 = document.getElementById("g0").value;
        var Ta = document.getElementById("Ta").value/10;
        var Tb = Ta - g0*h;
        var Tcp = (Ta + Tb)/2;

        var tB = h/l;
        var L = Math.sqrt(h*h + l*l);
        var cB = l/L;

        var H = Tcp*cB;
        var fmax = g0 * l*l /(8*H*cB);
        var Hs = (Tcp - g0*fmax)*cB
        var fmaxs = fmax*H/Hs

        document.getElementById("notationResult").textContent = fmaxs;
    }
    catch (e) {
        document.getElementById("notationResult").textContent="Данные введены не верно."
    }
}


