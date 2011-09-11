package me.krotn.ServerWarp.utils;

import org.bukkit.permissions.Permissible;

public class PermissionManager {
    private static final String DEFAULT_BASE_NODE = "ServerWarp";
    private String baseNode;
    
    public PermissionManager(String baseNode){
        this.baseNode = baseNode+".";
    }
    
    public PermissionManager(){
        this(DEFAULT_BASE_NODE);
    }
    
    public boolean hasPermission(Permissible permissible, String permissionNode){
        return permissible.hasPermission(baseNode+permissionNode);
    }
}
