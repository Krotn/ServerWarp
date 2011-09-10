package me.krotn.ServerWarp;

import me.krotn.ServerWarp.utils.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerWarp extends JavaPlugin{
    private FileManager fileMan;
    
    public void onEnable(){
        fileMan = new FileManager(this);
    }
    
    public void onDisable(){
        
    }
}
