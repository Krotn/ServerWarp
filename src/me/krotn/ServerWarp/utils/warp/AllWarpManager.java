package me.krotn.ServerWarp.utils.warp;

import java.io.File;
import java.util.ArrayList;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import me.krotn.ServerWarp.utils.FileManager;
import me.krotn.ServerWarp.utils.LogManager;

public class AllWarpManager {
    private FileManager fileMan;
    private Server server;
    private final String WAYPOINTS_DIR = "waypoints";
    private final String WARP_FILE = "warps.txt";
    private ArrayList<PlayerWaypointFileManager> playerWaypoints = new ArrayList<PlayerWaypointFileManager>();
    private WarpFileManager publicWarps;
    private LogManager logMan;
    
    public AllWarpManager(FileManager fileMan,Server server,LogManager logMan){
        this.fileMan = fileMan;
        this.logMan = logMan;
        this.server = server;
        fileMan.makeSubdirectory(WAYPOINTS_DIR);
        loadPlayerWaypoints();
        loadPublicWarps();
    }
    
    public void loadPublicWarps(){
        publicWarps = new WarpFileManager(fileMan.getPluginFile(new File(WARP_FILE)),server,logMan);
    }
    
    public void savePublicWarps(){
        publicWarps.save();
    }
    
    public WarpFileManager getPublicWarpsManager(){
        return publicWarps;
    }
    
    public void loadPlayerWaypoints(){
        playerWaypoints.clear();
        File directory = fileMan.getPluginFile(new File(WAYPOINTS_DIR));
        ArrayList<File> playerFiles = new ArrayList<File>();
        for(File f:directory.listFiles()){
            if(f.isFile()){
                playerFiles.add(f);
            }
        }
        for(File f:playerFiles){
            playerWaypoints.add(new PlayerWaypointFileManager(f.getName().split(".")[0],f,server,logMan));
        }
    }
    
    public void savePlayerWaypoints(){
        for(PlayerWaypointFileManager pwfm:playerWaypoints){
            pwfm.save();
        }
    }
    
    public PlayerWaypointFileManager getPlayerWaypointManager(Player player){
        return getPlayerWaypointManager(player.getName());
    }
    
    public PlayerWaypointFileManager getPlayerWaypointManager(String cleanPlayerName){
        for(PlayerWaypointFileManager pwfm:playerWaypoints){
            if(pwfm.isForPlayer(cleanPlayerName)){
                return pwfm;
            }
        }
        return null;
    }
    
    public boolean playerHasWaypointManager(String cleanPlayerName){
        return !(getPlayerWaypointManager(cleanPlayerName)==null);
    }
    
    public void addPlayerWaypointManager(String cleanPlayerName){
        if(playerHasWaypointManager(cleanPlayerName)){
            return;
        }
        String fileName = cleanPlayerName.toLowerCase()+".txt";
        try{
            File newWaypointFile = new File(fileMan.getPluginFile(new File(WAYPOINTS_DIR)).getAbsolutePath()+File.separator+fileName);
            newWaypointFile.createNewFile();
            playerWaypoints.add(new PlayerWaypointFileManager(cleanPlayerName,newWaypointFile,server,logMan));
        }catch(Exception e){
            logMan.severe("Error creating waypoints file for: "+cleanPlayerName+"!");
            e.printStackTrace();
        }
        
    }
}
