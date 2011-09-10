package me.krotn.ServerWarp.utils;

import java.io.File;

import org.bukkit.plugin.Plugin;

public class FileManager {
    private Plugin plugin;
    
    public FileManager(Plugin plugin){
        this.plugin = plugin;
        this.plugin.getDataFolder().mkdirs();
    }
    
    public File getPluginFile(File basicFile){
        return new File(this.plugin.getDataFolder().getAbsolutePath()+
                        File.separator+basicFile.getPath());
    }
    
    public void makeSubdirectory(String directoryName){
        File dirFile = new File(directoryName);
        this.getPluginFile(dirFile).mkdirs();
    }
}
