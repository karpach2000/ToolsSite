package com.parcel.tools.constructor.bodies.ws;

public class CounterWs {
    public CounterMenuWs counterMenu= new CounterMenuWs();
    public String descriptionText="Инструменты для расчета различных инженерных систем.";

    public CounterWs()
    {
        counterMenu.addItem("Что это?","main");
        counterMenu.addItem("Расчет провиса стального троса без нагрузки.","ropeSlack");
        counterMenu.addItem("Расчет координат стального троса под и без нагрузки.","ropeSlackCargo");
    }
}
