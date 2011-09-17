package me.krotn.ServerWarp;

import me.krotn.ServerWarp.utils.TeleportHandler;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class SWPlayerListener extends PlayerListener{
    private TeleportHandler tpMan;
    
    public SWPlayerListener(TeleportHandler tpMan){
        this.tpMan = tpMan;
    }
    
    public void onPlayerTeleport(PlayerTeleportEvent event){
        tpMan.reportTeleport(event);
    }
}
