package me.krotn.ServerWarp.utils;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class LogManager {
    private Logger logger;
    private JavaPlugin plugin;
    
    public LogManager(Logger logger, JavaPlugin plugin){
        this.logger = logger;
        this.plugin = plugin;
    }
    
    protected String formatLog(String log){
        return "["+plugin.getDescription().getName()+"] "+log;
    }
    
    public void info(String message){
        logger.info(formatLog(message));
    }
    
    public void warning(String message){
        logger.warning(formatLog(message));
    }
    
    public void severe(String message){
        logger.warning(formatLog(message));
    }
}
