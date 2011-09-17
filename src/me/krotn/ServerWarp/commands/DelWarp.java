package me.krotn.ServerWarp.commands;

import me.krotn.ServerWarp.utils.warp.WarpFileManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DelWarp implements CommandExecutor{
    private WarpFileManager warpMan;
    private String warpNotExistMessage;
    private String successMessage;
    
    public DelWarp(WarpFileManager warpMan,String successMessage,String warpNotExistMessage){
        this.warpMan = warpMan;
        this.warpNotExistMessage = warpNotExistMessage;
        this.successMessage = successMessage;
    }
    
    public boolean onCommand(CommandSender commandSender, Command command, String label,String[] args){
        if(args.length<=0){
            return false;
        }
        String warpName = args[0];
        if(warpMan.warpExists(warpName)){
            warpMan.removeWarp(warpName);
            commandSender.sendMessage(ChatColor.GREEN+successMessage);
            return true;
        }
        else{
            commandSender.sendMessage(ChatColor.RED+warpNotExistMessage);
            return true;
        }
    }
}
