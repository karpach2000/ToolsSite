package com.parcel.tools.constructor.content.ws;

import com.parcel.tools.constructor.content.PageContent;

public class CounterWs extends PageContent  {
    public CounterMenuWs counterMenu= new CounterMenuWs();
    public String descriptionText="Инструменты для расчета различных инженерных систем.";

    public CounterWs()
    {
        this.title = "Инженерные инструменты";

        this.additionalCss.add("web/css/unitsWs.css");
        this.additionalCss.add("//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.0/css/bootstrap-combined.min.css");

        this.additionalScripts.add("web/javascript/unitsWs.js");
        this.additionalScripts.add("https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.3/Chart.js");

        counterMenu.addItem("Что это?","main");
        counterMenu.addItem("Расчет провиса стального троса без нагрузки.","ropeSlack");
        counterMenu.addItem("Расчет координат стального троса под и без нагрузки.","ropeSlackCargo");
    }
}
