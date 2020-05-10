package io.github.yey007.asynctp;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import io.papermc.lib.PaperLib;

import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public final class AsyncTp extends JavaPlugin {

    public void onEnable()
    {
        PaperLib.suggestPaper(this);
    }

    public void onDisable()
    {
        
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if(cmd.getName().equalsIgnoreCase("tp-async"))
        {
            if(sender instanceof Player)
            {
                if(args.length < 2 || args.length > 4)
                {
                    sender.sendMessage("Too many arguments!");
                    return false;
                }
                else {
                    //get which player to teleport
                    List<Entity> selected = Bukkit.selectEntities(sender, args[0]);
                    Player player = (Player) sender;
                    if(args.length > 2)
                    {
                        Location location = null;
                        try {
                            location = new Location(player.getWorld(), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
                        } catch (Exception e) {
                            e.printStackTrace();
                            return false;
                        }
                        for (Entity entity : selected) {
                            PaperLib.teleportAsync(entity, location);
                        }
                        return true;
                    }
                    else
                    {
                        List<Entity> selectedOther = Bukkit.selectEntities(sender, args[1]);
                        if(selectedOther.size() > 1)
                        {
                            sender.sendMessage("That's not allowed!");
                            return false;
                        }
                        else {
                            Location location = selectedOther.get(0).getLocation();
                            for (Entity entity : selected) {
                                PaperLib.teleportAsync(entity, location);
                            }
                            return true;
                        }
                    }
                }
            }
            else
            {
                getLogger().info("You must be a player!");
                return false;
            }
        }
        return false;
    }
    
}
