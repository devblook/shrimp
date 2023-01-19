package team.devblook.shrimp.service;

import org.bukkit.entity.Player;

public interface Service {
    void start();
    default void stop(){
    }
}
