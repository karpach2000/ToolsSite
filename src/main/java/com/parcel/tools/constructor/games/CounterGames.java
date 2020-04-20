package com.parcel.tools.constructor.games;

import com.parcel.tools.constructor.bodies.ws.CounterMenuWs;
import com.parcel.tools.spy.SpySessionManager;

import java.util.ArrayList;

public class CounterGames {
    public CounterMenuGames counterMenu= new CounterMenuGames();
    public String descriptionText="Игры.";
    public ArrayList<String> locations = new ArrayList<String>();
    public CounterGames()
    {
        counterMenu.addItem("Шпион.","ropeSlack");
        try {
            locations = SpySessionManager.INSTANCE.getLocationList();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
