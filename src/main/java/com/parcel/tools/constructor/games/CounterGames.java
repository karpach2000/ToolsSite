package com.parcel.tools.constructor.games;

import com.parcel.tools.constructor.bodies.ws.CounterMenuWs;

public class CounterGames {
    public CounterMenuWs counterMenu= new CounterMenuWs();
    public String descriptionText="Игры.";

    public CounterGames()
    {
        counterMenu.addItem("Шпион.","ropeSlack");
    }
}
