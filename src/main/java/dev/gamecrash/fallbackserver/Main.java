package dev.gamecrash.fallbackserver;

import dev.gamecrash.fallbackserver.configuration.Configuration;
import dev.gamecrash.fallbackserver.configuration.ConfigurationLoader;
import net.minestom.server.MinecraftServer;

public final class Main {
    static void main(String[] args) {
        Configuration config = ConfigurationLoader.getDefault();

        MinecraftServer server = MinecraftServer.init();

        server.start(config.server.address, config.server.port);
    }
}
