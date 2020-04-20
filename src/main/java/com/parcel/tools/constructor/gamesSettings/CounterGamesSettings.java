package com.parcel.tools.constructor.gamesSettings;

import com.parcel.tools.constructor.games.CounterMenuGames;
import com.parcel.tools.spy.SpySessionManager;

import java.util.ArrayList;

public class CounterGamesSettings {
    public CounterMenuGamesSettings counterMenu= new CounterMenuGamesSettings();
    public String descriptionText="Настройка игр.";
    public ArrayList<String> locations = new ArrayList<String>();
    public CounterGamesSettings()
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
