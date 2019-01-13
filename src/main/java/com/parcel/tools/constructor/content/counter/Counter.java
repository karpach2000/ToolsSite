package com.parcel.tools.constructor.content.counter;

import com.parcel.tools.constructor.content.PageContent;

public class Counter extends PageContent {
    public CounterMenu counterMenu= new CounterMenu();
    public String descriptionText="Здесь предсатвленны различаные утилиты, полезные при разборе протоколов.";

    public Counter()
    {
        this.title = "Мелкие утилиты";

        this.additionalScripts.add("web/javascript/units.js");
        this.additionalCss.add("web/css/units.css");

        counterMenu.addItem("Что это?","main");
        counterMenu.addItem("Перевод между системами счисления.","notation");
        counterMenu.addItem("Расчет контрольных сумм.", "crc");
    }
}
