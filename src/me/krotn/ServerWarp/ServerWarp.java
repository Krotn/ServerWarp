package me.krotn.ServerWarp;

import me.krotn.ServerWarp.utils.FileManager;
import me.krotn.ServerWarp.utils.LogManager;
import me.krotn.ServerWarp.utils.PermissionManager;
import me.krotn.ServerWarp.utils.warp.AllWarpManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import me.krotn.ServerWarp.commands.*;

public class ServerWarp extends JavaPlugin{
    private FileManager fileMan;
    private LogManager logMan;
    private AllWarpManager warpMan;
    private PermissionManager permMan;
    
    public void onEnable(){
        fileMan = new FileManager(this);
        logMan = new LogManager(this.getServer().getLogger(),this);
        warpMan = new AllWarpManager(fileMan,this.getServer(),logMan);
        permMan = new PermissionManager();
        this.getConfiguration().load();
        this.getConfiguration().save();
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
            logMan.info("Saved waypoints!");
        }catch(Exception e){
            logMan.severe("Error saving player waypoints! Player waypoints may not have been saved!");
            e.printStackTrace();
        }
        if(!this.getConfiguration().save()){
            logMan.severe("There was a problem saving the configuration to disk!");
        }
        logMan.info("ServerWarp disabled!");
    }
    
    public boolean onCommand(CommandSender sender, Command command, java.lang.String label, java.lang.String[] args){
        String commandString = command.getName();
        if(commandString.equalsIgnoreCase("listwarps")){
            if(permMan.hasPermission(sender, "warp.list")){
                return new ListWarps(warpMan.getPublicWarpsManager(),getConfiguration().getString("output.warpList.warpTitle","Warps")).onCommand(sender,command,label,args);
            }
        }
        if(commandString.equalsIgnoreCase("setwarp")){
            if(permMan.hasPermission(sender, "warp.set")){
                return new SetWarp(warpMan.getPublicWarpsManager(),getConfiguration().getString("output.warpSet.success","Warp set!"),
                                                            getConfiguration().getString("output.warpSet.failAlreadyExists","That warp already exists!"))
                                                            .onCommand(sender, command, label, args);
            }
        }
        return false;
    }
}
