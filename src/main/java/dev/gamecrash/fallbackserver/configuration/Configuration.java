package dev.gamecrash.fallbackserver.configuration;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.awt.*;

@ConfigSerializable
public class Configuration {
    @Comment("Properties for where the server should listen")
    public Server server = new Server();

    public Properties properties = new Properties();

    @Comment("Configuration regarding the use of a velocity proxy")
    public Velocity velocity = new Velocity();


    @ConfigSerializable
    public static class Server {
        public String address = "0.0.0.0";
        public int port = 25565;
    }

    @ConfigSerializable
    public static class Properties {
        public String brandName = "§cFallback§f";
        public String motd = "<rainbow>Fallback Server";
        public String defaultGamemode = "ADVENTURE";
        public Position spawnPoint = new Position(0.5, 2, 0.5);
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

    @ConfigSerializable
    public static class Position {
        public Position(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public double x = 0;
        public double y = 0;
        public double z = 0;
    }
}


