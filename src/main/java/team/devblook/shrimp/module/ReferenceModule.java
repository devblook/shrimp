package team.devblook.shrimp.module;

import team.devblook.shrimp.user.User;
import team.devblook.shrimp.user.UserHandler;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.key.TypeReference;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReferenceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeReference<Map<String, User>>(){})
        .toInstance(new ConcurrentHashMap<>());

        bind(UserHandler.class).singleton();
    }
}
