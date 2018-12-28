var currentMenuCountingItem="main";





function menuClick(id) {
    showMenuContent(id);
}

function showMenuContent(id) {

    document.getElementById(currentMenuCountingItem).hidden=true;
    document.getElementById(id).hidden=false;
    currentMenuCountingItem=id;

}

function run() {
    ropeSlack();
    ropeSlackCargo();

}

/**
 * Без груза.
 */
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
        document.getElementById("ropeSlackMax").textContent = fmaxs;
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

/**
 * С грузом.
 */
function ropeSlackCargo() {


    var ctxSlack = document.getElementById('slackGrafCargo').getContext('2d');
    var ctxRopeForm = document.getElementById('ropeFormGrafCargo').getContext('2d');
    try {
        var l = document.getElementById("lCargo").value;
        var h = document.getElementById("hCargo").value;
        var g0 = document.getElementById("g0Cargo").value;
        var Ta = document.getElementById("TaCargo").value/10;
        var Q = document.getElementById("mCargo").value*9.81;
        var Tb = Ta - g0*h - Q;
        var TbFree = Ta - g0*h;
        var Tcp = (Ta + Tb)/2;
        var TcpFree = (Ta + TbFree)/2;

        var tB = h/l;
        var L = Math.sqrt(h*h + l*l);
        var cB = l/L;

        var H = Tcp*cB;
        var HFree = TcpFree*cB;
        var p = H/g0;
        var pFree = HFree/g0;



        var fArrayFree = []
        var fArraySum = []
        var xArray = []
        var ropeForm = []//форма веревки
        var ropeFormFree = []//форма веревки
        for(var x = 0; x<l; x++)
        {

            var k = 1 + cB*cB*((x*x -l*x +l*l/2)/p - 2*(l-2*x)*tB)/p;
            var kFree = 1 + cB*cB*((x*x -l*x +l*l/2)/p - 2*(l-2*x)*tB)/pFree;
            var f = g0 * x*(l - x)*k/(2*H*cB);
            var f_ =  x*(l - x)*Q/(H*l);
            fArraySum[x] = f + f_;
            fArrayFree[x] = g0 * x*(l - x)*kFree/(2*HFree*cB)
            ropeForm [x]=(h -x*tB- fArraySum[x]);
            ropeFormFree[x]=(h -x*tB- fArrayFree[x]);
            xArray[x] = x
        }
        document.getElementById("ropeHighMin").textContent = Math.min.apply(null, ropeFormFree).toString();
        document.getElementById("cargoHighMin").textContent = Math.min.apply(null, ropeForm).toString();
        var ropeFormGraf = new Chart(ctxRopeForm, {
            // The type of chart we want to create
            type: 'line',

            // The data for our dataset
            data: {
                labels: xArray,
                datasets: [{
                    label: "Координаты точек положения груза относительно верхней опоры [м].\n(траектория разника)",
                    //backgroundColor: ,
                    borderColor: 'rgb(255, 99, 132)',
                    data: ropeForm,
                }
                    ,

                {
                    label: "Координаты точек положения каната относительно верхней опоры без нагрузки[м].\n",
                    //backgroundColor: 'rgb(132, 255, 99)',
                    borderColor: 'rgb(99, 255, 132)',
                    data: ropeFormFree,
                 }
                ]
            },

            // Configuration options go here
            options: {}
        });

        var slackGraf = new Chart(ctxSlack, {
            // The type of chart we want to create
            type: 'line',

            // The data for our dataset
            data: {
                labels: xArray,
                datasets: [{
                    label: "Провис каната под грузом f[м].",
                    backgroundColor: 'rgb(255, 99, 132)',
                    borderColor: 'rgb(99, 255, 132)',
                    data: fArraySum,
                },


                ]
            },

            // Configuration options go here
            options: {}
        });

    }
    catch (e) {
        document.getElementById("ropeSlackMax").textContent="Данные введены не верно.";
    }
}
