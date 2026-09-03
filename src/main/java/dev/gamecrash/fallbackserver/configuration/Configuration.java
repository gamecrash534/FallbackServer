package dev.gamecrash.fallbackserver.configuration;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class Configuration {
    @Comment("Properties for where the server should listen")
    public Server server;


    @ConfigSerializable
    public static class Server {
        public String address = "0.0.0.0";
        public int port = 25565;
    }

    @ConfigSerializable
    public static class Properties {

    }

    @ConfigSerializable
    public static class Velocity {
        public boolean enabled = false;
        public @Nullable String authSecret;
    }

    @ConfigSerializable
    public static class ResourcePack {
        public boolean enabled = false;

    }
}


