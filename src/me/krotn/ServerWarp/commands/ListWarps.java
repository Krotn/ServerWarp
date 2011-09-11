package me.krotn.ServerWarp.commands;

import java.util.Arrays;

import me.krotn.ServerWarp.utils.warp.WarpFileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

public class ListWarps implements CommandExecutor{
    WarpFileManager warpMan;
    private final int WARPS_PER_LINE = 4;
    private final int LINES_PER_PAGE = 3;
    private String warpWord;
    
    public ListWarps(WarpFileManager warpMan,String warpWord){
        this.warpMan = warpMan;
        this.warpWord = warpWord;
    }
    
    public ListWarps(WarpFileManager warpMan){
        this(warpMan,"Warps");
    }
    
    private String makeWarpString(int page){
        String[] allWarps = warpMan.getWarps();
        Arrays.sort(allWarps);
        String[] subWarps = new String[WARPS_PER_LINE*LINES_PER_PAGE];
        int lowindex = page*(WARPS_PER_LINE*LINES_PER_PAGE);
        int highindex = (page+1)*(WARPS_PER_LINE*LINES_PER_PAGE);
        if(lowindex>allWarps.length){
            return ChatColor.RED+"Invalid page!";
        }
        int workingIndex = lowindex;
        int subIndex = 0;
        while(workingIndex<highindex&&workingIndex<allWarps.length&&subIndex<subWarps.length){
            subWarps[subIndex] = allWarps[workingIndex];
            subIndex++;
            workingIndex++;
        }
        String warpString = ChatColor.GRAY+warpWord+": "+ChatColor.WHITE;
        int linePosCounter = 0;
        int lineCount = 0;
        int index = 0;
        while(lineCount<LINES_PER_PAGE){
            while(linePosCounter<WARPS_PER_LINE){
                if(subWarps[index]!=null){
                    warpString = warpString+subWarps[index]+" ";
                }
                linePosCounter++;
                index++;
            }
            warpString = warpString+"\n";
            linePosCounter=0;
            lineCount++;
        }
        return warpString;
    }
    
    private int countWarps(){
        return warpMan.getWarps().length;
    }
    
    private int getNumPages(){
        return (countWarps()/(WARPS_PER_LINE*LINES_PER_PAGE))+1;
    }
    
    public boolean onCommand(CommandSender commandSender, Command command, String label,String[] args) {
        int page = 0;
        if(args.length == 0){
            page = 0;
        }
        else{
            page = (new Integer(args[0]).intValue())-1;
        }
        String warpString = makeWarpString(page);
        for(String s:warpString.split("\n")){
            commandSender.sendMessage(s);
        }
        commandSender.sendMessage(ChatColor.GRAY+"["+ChatColor.GREEN+(page+1)+ChatColor.GRAY+"/"+ChatColor.GREEN+getNumPages()+ChatColor.GRAY+"]");
        return true;
    }
    
}
