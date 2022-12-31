package team.devblook.shrimp.service;

public interface Service {
    void start();
    default void stop(){
    }
}
