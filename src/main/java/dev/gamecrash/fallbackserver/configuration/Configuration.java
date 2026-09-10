package dev.gamecrash.fallbackserver.configuration;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.Locale;
import java.util.Set;

@ConfigSerializable
public class Configuration {
    @Comment("Properties for where the server should listen")
    public Server server = new Server();

    @Comment("World properties")
    public World world = new World();

    @Comment("Different properties for the server")
    public Properties properties = new Properties();

    @Comment("Configuration regarding the use of a velocity proxy")
    public Proxy proxy = new Proxy();

    @Comment("Messages that may be sent to the player")
    public Messages messages = new Messages();

    @Comment("Logger properties")
    public Logger logger = new Logger();

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
        public String brandName = "Fallback";

        @Comment("The MOTD that will be displayed in the server list. Supports MiniMessage")
        public String motd = "<rainbow>Fallback Server";

    }

    @ConfigSerializable
    public static class World {
        @Comment("The default game mode to use")
        public String defaultGamemode = "ADVENTURE";

        @Comment("The location in a world where players will spawn")
        public Position spawnPoint = new Position(0.5, 2, 0.5);
    }

    @ConfigSerializable
    public static class Proxy {
        @Comment("""
            The proxy type this server should use for authentication.
            Following values are accepted:
                'none'       : No Proxy (default)
                'velocity'   : Use the Velocity proxy. Will require 'auth_secret' to be set.
                'bungeecord' : Use Bungeecord as a proxy. 'auth_secret' can either be left empty or contain a key when using BungeeGuard.
                               Note: currently only supports a single key."""
        )
        public String type = "none";
        public String authSecret = "";
    }

    @ConfigSerializable
    public static class Messages {
        public String shutdownMessage = "<red>Server stopped";
    }

    @ConfigSerializable
    public static class Logger {
        public String level = "info";
    }

    @ConfigSerializable
    public static class Position {
        public Position() {
            x = y = z = 0;
        }

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
        return properties.online && proxy.type.toLowerCase(Locale.ROOT).equals("none");
    }

    public boolean isVelocity() {
        return proxy.type.toLowerCase(Locale.ROOT).equals("velocity");
    }

    public boolean isBungee() {
        return proxy.type.toLowerCase(Locale.ROOT).equals("bungeecord");
    }
}


