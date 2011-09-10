package me.krotn.ServerWarp.utils.warp;

import java.io.File;
import me.krotn.ServerWarp.utils.LogManager;
import org.bukkit.Server;

public class PlayerWaypointFileManager extends WarpFileManager{
    private String playerName;
    
    public PlayerWaypointFileManager(String playerName,File warpFile,Server server,LogManager logMan){
        super(warpFile,server,logMan);
        this.playerName = toCorrectPlayerNameFormat(playerName);
    }
    
    protected String toCorrectPlayerNameFormat(String name){
        return name.toLowerCase();
    }
    
    public String getPlayerName(){
        return this.playerName;
    }
    
    public boolean isForPlayer(String playerToCheck){
        return playerToCheck.equalsIgnoreCase(getPlayerName());
    }
}
