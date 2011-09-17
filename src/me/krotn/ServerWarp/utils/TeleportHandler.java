package me.krotn.ServerWarp.utils;

import java.util.Hashtable;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import me.krotn.ServerWarp.utils.history.AllPlayerTeleportHistoryManager;

public class TeleportHandler {
    private Hashtable<Player,Location> internalTps = new Hashtable<Player,Location>();
    private AllPlayerTeleportHistoryManager histMan;
    private PermissionManager permMan;
    
    public TeleportHandler(AllPlayerTeleportHistoryManager histMan,PermissionManager permMan){
        this.histMan = histMan;
        this.permMan = permMan;
    }
    
    public void doIgnoredTeleport(Player player,Location newLocation){
        Location oldLocation = player.getLocation();
        if(permMan.hasPermission(player, "motion.depart")){
            doForcedIgnoredTeleport(player,newLocation);
            if(!permMan.hasPermission(player, "motion.arrive")){
                doForcedIgnoredTeleport(player,oldLocation);
            }
        }
    }
    
    protected void doForcedIgnoredTeleport(Player player,Location newLocation){
        internalTps.put(player, newLocation);
        player.teleport(newLocation);
    }
    
    protected void ensurePlayerHistManager(Player player){
        if(!histMan.playerHasHistoryManager(player)){
            histMan.addHistoryManager(player);
        }
    }
    
    public void reportTeleport(PlayerTeleportEvent tpEvt){
        ensurePlayerHistManager(tpEvt.getPlayer());
        if(internalTps.containsKey(tpEvt.getPlayer())){
            if(internalTps.get(tpEvt.getPlayer()).equals(tpEvt.getTo())){
                internalTps.remove(tpEvt.getPlayer());
                return;
            }
        }
        histMan.getPlayerHistoryManager(tpEvt.getPlayer()).addToHistory(tpEvt.getTo());
    }
    
    public void doTeleport(Player player,Location newLocation){
        Location oldLocation = player.getLocation();
        if(permMan.hasPermission(player, "motion.depart")){
            internalTps.put(player, newLocation);
            player.teleport(newLocation);
            if(!permMan.hasPermission(player, "motion.arrive")){
                this.doForcedIgnoredTeleport(player,oldLocation);
            }
            else{
                this.ensurePlayerHistManager(player);
                histMan.getPlayerHistoryManager(player).addToHistory(newLocation);
            }
        }
    }
    
    public AllPlayerTeleportHistoryManager getHistoryManager(){
        return histMan;
    }
}
