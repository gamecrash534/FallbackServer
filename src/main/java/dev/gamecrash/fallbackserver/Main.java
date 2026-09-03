package dev.gamecrash.fallbackserver;

import dev.gamecrash.fallbackserver.configuration.Configuration;
import dev.gamecrash.fallbackserver.configuration.ConfigurationLoader;
import dev.gamecrash.fallbackserver.server.Listener;
import dev.gamecrash.fallbackserver.world.World;
import net.minestom.server.Auth;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.event.server.ServerListPingEvent;

import java.util.Locale;

public final class Main {
    static void main(String[] args) {
        Configuration config = ConfigurationLoader.getDefault();

        Auth auth = config.velocity.enabled ? new Auth.Velocity(config.velocity.authSecret) : new Auth.Online();

        MinecraftServer server = MinecraftServer.init(auth);

        MinecraftServer.setBrandName(config.properties.brandName);
        server.start(config.server.address, config.server.port);
        World world = new World();

        Listener listener = new Listener(world, config);

        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        handler.addListener(AsyncPlayerConfigurationEvent.class, listener::onPlayerAsyncConfiguration);
        handler.addListener(PlayerSpawnEvent.class, listener::onPlayerSpawn);
        handler.addListener(ServerListPingEvent.class, listener::onServerListPing);
    }
}
