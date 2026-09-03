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
        world.instance().getPlayers().forEach(plr -> {
            plr.kick(MiniMessage.miniMessage().deserialize(config.messages.shutdownMessage));
        });
        MinecraftServer.stopCleanly();
    }

    private Auth getAuth() {
        if (config.isOnline()) return new Auth.Online();
        else if (config.isVelocity()) {
            if (config.velocity.authSecret == null)
                throw new IllegalArgumentException("Velocity auth secret must not be when Velocity is enabled!");

            return new Auth.Velocity(config.velocity.authSecret);
        }
        return new Auth.Offline();
    }

    private void registerListeners() {
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        Listener listener = new Listener(world, config);

        handler.addListener(AsyncPlayerConfigurationEvent.class, listener::onPlayerAsyncConfiguration);
        handler.addListener(PlayerSpawnEvent.class, listener::onPlayerSpawn);
        handler.addListener(ServerListPingEvent.class, listener::onServerListPing);
    }
}
