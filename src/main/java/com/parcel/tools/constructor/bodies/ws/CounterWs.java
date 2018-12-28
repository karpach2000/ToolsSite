package com.parcel.tools.constructor.bodies.ws;

public class CounterWs {
    public CounterMenuWs counterMenu= new CounterMenuWs();
    public String descriptionText="Алгоритмы расчета различных альпинистских систем";

    public CounterWs()
    {
        counterMenu.addItem("Что это?","main");
        counterMenu.addItem("Расчет провиса стального троса без нагрузки.","ropeSlack");
        counterMenu.addItem("Расчет провиса стального троса под нагрузкой.","ropeSlackCargo");
    }
}
