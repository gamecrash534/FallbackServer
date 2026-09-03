package dev.gamecrash.fallbackserver.server;

import dev.gamecrash.fallbackserver.configuration.Configuration;
import dev.gamecrash.fallbackserver.world.World;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.ping.Status;

import java.util.Locale;

public class Listener {
    private final World world;
    private final Configuration config;
    private final Component motd;

    public Listener(World w, Configuration c) {
        world = w;
        config = c;
        motd = MiniMessage.miniMessage().deserialize(c.properties.motd);
    }

    public void onPlayerSpawn(PlayerSpawnEvent e) {
        Configuration.Position pos = config.world.spawnPoint;
        e.getPlayer().teleport(new Vec(pos.x, pos.y, pos.z).asPos());
        e.getPlayer().setGameMode(GameMode.valueOf(config.world.defaultGamemode.toUpperCase(Locale.ROOT)));
    }

    public void onPlayerAsyncConfiguration(AsyncPlayerConfigurationEvent e) {
        e.setSpawningInstance(world.instance());
    }

    public void onServerListPing(ServerListPingEvent e) {
        Status newStatus = Status.builder(e.getStatus())
            .description(motd)
            .build();
        e.setStatus(newStatus);
    }
}
