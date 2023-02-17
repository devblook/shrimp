package team.devblook.shrimp.service;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class CommandExample implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player p)){
            return false;
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.isOp()){
                if (player.getInventory().getHelmet()!=null){
                    if (!player.getInventory().getHelmet().getType().equals(org.bukkit.Material.CARVED_PUMPKIN)){
                        player.setHealth(0);
                        player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), 25);
                    }
                }

            }
        }
    return true;
    }
}
