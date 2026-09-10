package dev.gamecrash.fallbackserver.server;

import dev.gamecrash.fallbackserver.configuration.Configuration;
import dev.gamecrash.fallbackserver.world.World;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minestom.server.Auth;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.network.player.PlayerSocketConnection;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.Collections;

public class Server {
    private final Configuration config;
    private final MinecraftServer server;
    private final World world;

    public Server(Configuration configuration) {
        config = configuration;

        server = MinecraftServer.init(getAuth());

        world = new World();
        MinecraftServer.setBrandName(config.properties.brandName);
        registerListeners();
    }

    public void start() {
        server.start(config.server.address, config.server.port);
    }

    public void stop() {
        MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(player -> {
            player.kick(MiniMessage.miniMessage().deserialize(config.messages.shutdownMessage));
            try {
                ((PlayerSocketConnection) player.getPlayerConnection()).flushSync();
            } catch (IOException ignored) { }
        });
        MinecraftServer.stopCleanly();
    }

    private Auth getAuth() {
        Logger.debug("Determining Auth to use");
        String secret = config.proxy.authSecret;
        switch (config.proxy.type) {
            case "velocity": {
                if (secret.isBlank())
                    throw new IllegalArgumentException("Velocity auth secret must not null be when Velocity is enabled!");

                return new Auth.Velocity(secret);
            }
            case "bungeecord": {
                return new Auth.Bungee(secret.isBlank() ? null : Collections.singleton(secret));
            }
            default: {
                return config.properties.online
                    ? new Auth.Online()
                    : new Auth.Offline();
            }
        }
    }

    private void registerListeners() {
        Logger.debug("Registering Listeners");
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        Listener listener = new Listener(world, config);

        handler.addListener(AsyncPlayerConfigurationEvent.class, listener::onPlayerAsyncConfiguration);
        handler.addListener(PlayerSpawnEvent.class, listener::onPlayerSpawn);
        handler.addListener(ServerListPingEvent.class, listener::onServerListPing);
    }
}
