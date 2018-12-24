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


    var ctxSlack = document.getElementById('slackGraf').getContext('2d');
    var ctxRopeForm = document.getElementById('ropeFormGraf').getContext('2d');
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
        var p = H/g0;
        var fmax = g0 * l*l /(8*H*cB);
        var Hs = (Tcp - g0*fmax)*cB
        var fmaxs = fmax*H/Hs


        var fArray = []//получаем массив с данными для провиса каната с точностью  до метра
        var xArray = []
        var ropeForm = []//форма веревки
        for(var x = 0; x<l; x++)
        {

            var k = 1 + cB*cB*((x*x -l*x +l*l/2)/p - 2*(l-2*x)*tB)/p;
            fArray[x] = g0 * x*(l - x)*k/(2*H*cB);
            ropeForm [x]=(h -x*tB- fArray[x]);
            xArray[x] = x
        }
        var ropeFormGraf = new Chart(ctxRopeForm, {
            // The type of chart we want to create
            type: 'line',

            // The data for our dataset
            data: {
                labels: xArray,
                datasets: [{
                    label: "Координаты точек веревки относительно верхней опоры [м].\n(форма веревки)",
                    backgroundColor: 'rgb(99, 255, 132)',
                    borderColor: 'rgb(255, 99, 132)',
                    data: ropeForm,
                }]
            },

            // Configuration options go here
            options: {}
        });
        document.getElementById("ropeSlackMax").textContent = fmaxs;
        var slackGraf = new Chart(ctxSlack, {
            // The type of chart we want to create
            type: 'line',

            // The data for our dataset
            data: {
                labels: xArray,
                datasets: [{
                    label: "Провис каната f[м].",
                    backgroundColor: 'rgb(255, 99, 132)',
                    borderColor: 'rgb(99, 255, 132)',
                    data: fArray,
                }]
            },

            // Configuration options go here
            options: {}
        });

    }
    catch (e) {
        document.getElementById("ropeSlackMax").textContent="Данные введены не верно.";
    }
}


