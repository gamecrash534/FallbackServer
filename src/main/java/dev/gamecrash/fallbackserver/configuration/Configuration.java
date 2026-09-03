package dev.gamecrash.fallbackserver.configuration;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minestom.server.Auth;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.awt.*;

@ConfigSerializable
public class Configuration {
    @Comment("Properties for where the server should listen")
    public Server server = new Server();

    @Comment("Different properties for the server")
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
        @Comment("Whether the server should operate in online mode. Will be ignored when Velocity is enabled")
        public boolean online = true;

        @Comment("The string of the server type displayed in the client F3 Menu")
        public String brandName = "§cFallback§f";

        @Comment("The MOTD that will be displayed in the server list. Supports MiniMessage")
        public String motd = "<rainbow>Fallback Server";

        @Comment("The default game mode to use")
        public String defaultGamemode = "ADVENTURE";

        @Comment("The location in a world where players will spawn")
        public Position spawnPoint = new Position(0.5, 2, 0.5);
    }

    @ConfigSerializable
    public static class Velocity {
        public boolean enabled = false;
        public @Nullable String authSecret;
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

    public boolean isOnline() {
        return properties.online && !velocity.enabled;
    }

    public boolean isVelocity() {
        return velocity.enabled;
    }
}


