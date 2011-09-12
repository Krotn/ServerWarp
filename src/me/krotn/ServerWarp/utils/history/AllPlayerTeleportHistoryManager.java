package me.krotn.ServerWarp.utils.history;

import java.util.ArrayList;
import org.bukkit.entity.Player;

public class AllPlayerTeleportHistoryManager {
    
    private ArrayList<PlayerTeleportHistoryManager> histories = new ArrayList<PlayerTeleportHistoryManager>();
    private int histSize;
    
    public AllPlayerTeleportHistoryManager(int histSize){
        this.histSize = histSize;
    }
    
    public PlayerTeleportHistoryManager getPlayerHistoryManager(Player player){
        for(PlayerTeleportHistoryManager histMan:histories){
            if(histMan.getPlayer().getName().equalsIgnoreCase(player.getName())){
                return histMan;
            }
        }
        return null;
    }
    
    public boolean playerHasHistoryManager(Player player){
        return !(getPlayerHistoryManager(player) == null);
    }
    
    public void addHistoryManager(Player player,int historySize){
        if(playerHasHistoryManager(player)){
            return;
        }
        else{
            histories.add(new PlayerTeleportHistoryManager(player,historySize));
        }
    }
    
    public void addHistoryManager(Player player){
        addHistoryManager(player,histSize);
    }
    
    public void clearPlayerHistoryManager(Player player){
        if(!playerHasHistoryManager(player)){
            return;
        }
        else{
            getPlayerHistoryManager(player).clear();
        }
    }
}
