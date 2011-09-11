package me.krotn.ServerWarp.commands;

import java.util.Arrays;

import me.krotn.ServerWarp.utils.warp.WarpFileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

public class ListWarps implements CommandExecutor{
    WarpFileManager warpMan;
    
    public ListWarps(WarpFileManager warpMan){
        this.warpMan = warpMan;
    }
    
    private String makeWarpString(int page){
        int maxCharsPerLine = 50;
        int maxLines = 2;
        String defaultWarpString = ChatColor.GRAY+"Warps:"+ChatColor.WHITE;
        String warpString = defaultWarpString;
        String[] warps = warpMan.getWarps();
        Arrays.sort(warps);
        int pageCount = 0;
        int lineCount = 0;
        String workingString = "";
        for(String s:warps){
            workingString = workingString+" "+s;
            if(workingString.length()>=maxCharsPerLine){
                warpString = warpString+workingString;
                workingString = "";
                lineCount++;
            }
            if(lineCount>maxLines){
                pageCount++;
                warpString = warpString+workingString;
                if(pageCount == page){
                    return warpString;
                }
                else{
                    warpString = defaultWarpString;
                }
            }
        }
        return warpString+workingString;
    }
    
    public boolean onCommand(CommandSender commandSender, Command command, String label,String[] args) {
        int page = 0;
        if(args.length == 0){
            page = 0;
        }
        else{
            page = new Integer(args[0]);
        }
        String warpString = makeWarpString(page);
        for(String s:warpString.split("\n")){
            commandSender.sendMessage(s);
        }
        return true;
    }
    
}
