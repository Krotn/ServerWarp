package me.krotn.ServerWarp.utils.warp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Hashtable;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;

public class WarpFileManager {
    private File warpFile;
    private Server server;
    private Hashtable<String,Location> warps = new Hashtable<String,Location>();
    
    public WarpFileManager(File warpFile,Server server){
        this.warpFile = warpFile;
        this.server = server;
        if(!this.warpFile.exists()){
            try{
                this.warpFile.createNewFile();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        load();
    }
    
    public void load(){
        warps.clear();
        try{
            BufferedReader in = new BufferedReader(new FileReader(warpFile));
            String line = null;
            while((line = in.readLine()) != null){
                String[] lineVals = line.split(":");
                String name = toCorrectNameFormat(lineVals[0]);
                double x = new Double(lineVals[1]);
                double y = new Double(lineVals[2]);
                double z = new Double(lineVals[3]);
                float yaw = new Float(lineVals[4]);
                float pitch = new Float(lineVals[5]);
                World world = null;
                for(World w:this.server.getWorlds()){
                    String searchName = lineVals[6];
                    if(w.getName().equalsIgnoreCase(searchName)){
                        world = w;
                        break;
                    }
                }
                if(world==null){
                    continue; //INVALID FORMAT!
                }
                Location warpLoc = new Location(world,x,y,z,yaw,pitch);
                warps.put(name, warpLoc);
            }
            in.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void save(){
        boolean result = false;
        try{
            result = warpFile.delete();
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            warpFile.createNewFile();
            if(result){
                PrintWriter out = new PrintWriter(new FileWriter(warpFile));
                for(String name:warps.keySet()){
                    Location loc = warps.get(name);
                    out.println(toCorrectNameFormat(name)+":"+loc.getX()+":"+loc.getY()+":"+
                                loc.getZ()+":"+loc.getYaw()+":"+loc.getPitch()+":"+
                                loc.getWorld().getName());
                }
                out.close();
            }   
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    protected String toCorrectNameFormat(String name){
        return name.toLowerCase();
    }
    
    public void addWarp(String name,Location location){
        warps.put(toCorrectNameFormat(name), location);
    }
    
    public void removeWarp(String name){
        if(warpExists(name)){
            String workingName = toCorrectNameFormat(name);
            warps.remove(workingName);
        }
    }
    
    public Location getWarpLocation(String name){
        String workingName = toCorrectNameFormat(name);
        if(!warpExists(workingName)){
            return null;
        }
        else{
            return warps.get(workingName);
        }
    }
    
    public String[] getWarps(){
        return (String[]) warps.keySet().toArray();
    }
    
    public boolean warpExists(String name){
        String workingName = toCorrectNameFormat(name);
        return warps.containsKey(workingName);
    }
}
