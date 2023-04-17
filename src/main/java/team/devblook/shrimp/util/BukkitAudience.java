package team.devblook.shrimp.util;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.jetbrains.annotations.NotNull;
import team.devblook.shrimp.Shrimp;

public class BukkitAudience {
    private BukkitAudiences audience;

    private final Shrimp plugin;

    public BukkitAudience(final Shrimp plugin) {
        this.plugin = plugin;
    }

    public @NotNull BukkitAudiences adventure() {
        return this.audience = BukkitAudiences.create(this.plugin);
    }
}
