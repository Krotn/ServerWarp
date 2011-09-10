package me.krotn.ServerWarp;

import me.krotn.ServerWarp.utils.FileManager;
import me.krotn.ServerWarp.utils.LogManager;
import me.krotn.ServerWarp.utils.warp.AllWarpManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerWarp extends JavaPlugin{
    private FileManager fileMan;
    private LogManager logMan;
    private AllWarpManager warpMan;
    
    public void onEnable(){
        fileMan = new FileManager(this);
        logMan = new LogManager(this.getServer().getLogger(),this);
        warpMan = new AllWarpManager(fileMan,this.getServer(),logMan);
        logMan.info("ServerWarp enabled!");
    }
    
    public void onDisable(){
        try{
            warpMan.savePublicWarps();
            logMan.info("Saved warps!");
        }catch(Exception e){
            logMan.severe("Error saving warps! Warps may not have been saved!");
            e.printStackTrace();
        }
        try{
            warpMan.savePlayerWaypoints();
        }catch(Exception e){
            logMan.severe("Error saving player waypoints! Player waypoints may not have been saved!");
            e.printStackTrace();
        }
        logMan.info("ServerWarp disabled!");
    }
}
