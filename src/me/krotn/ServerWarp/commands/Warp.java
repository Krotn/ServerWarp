package me.krotn.ServerWarp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.krotn.ServerWarp.utils.TeleportHandler;
import me.krotn.ServerWarp.utils.warp.WarpFileManager;

public class Warp implements CommandExecutor{
    private WarpFileManager warpMan;
    private TeleportHandler tpMan;
    private String warpNotExistMessage;
    
    public Warp(WarpFileManager warpMan,TeleportHandler tpMan,String warpNotExistMessage){
        this.warpMan = warpMan;
        this.warpNotExistMessage = warpNotExistMessage;
        this.tpMan = tpMan;
    }
    
    public boolean onCommand(CommandSender commandSender, Command command, String label,String[] args){
        if(args.length<1){
            return false;
        }
        if(commandSender instanceof Player){
            Player playerCommandSender = (Player) commandSender;
            String warpName = args[0];
            if(!warpMan.warpExists(warpName)){
                commandSender.sendMessage(ChatColor.RED+warpNotExistMessage);
                return true;
            }
            tpMan.doTeleport(playerCommandSender, warpMan.getWarpLocation(warpName));
            return true;
        }
        return false;
    }
}
