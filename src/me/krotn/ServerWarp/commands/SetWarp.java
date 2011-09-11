package me.krotn.ServerWarp.commands;

import me.krotn.ServerWarp.utils.warp.WarpFileManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarp implements CommandExecutor{
    private WarpFileManager warpMan;
    private String warpSetString;
    private String alreadyExistsString;
    
    public SetWarp(WarpFileManager warpMan, String warpSetString, String alreadyExistsString){
        this.warpMan = warpMan;
        this.warpSetString = warpSetString;
        this.alreadyExistsString = alreadyExistsString;
    }
    
    public SetWarp(WarpFileManager warpMan){
        this(warpMan,"Warp set!","That warp already exists!");
    }
    
    public boolean onCommand(CommandSender commandSender, Command command, String label,String[] args){
        if(!(commandSender instanceof Player)){
            return false;
        }
        if(!(args.length>=1)){
            return false;
        }
        if(warpMan.warpExists(args[0])){
            commandSender.sendMessage(ChatColor.RED+alreadyExistsString);
        }
        else{
            Player commandPlayer = (Player) commandSender;
            warpMan.addWarp(args[0], commandPlayer.getLocation());
            commandSender.sendMessage(ChatColor.GREEN+warpSetString);
        }
        return true;
    }
}
