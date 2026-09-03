package dev.gamecrash.fallbackserver;

import dev.gamecrash.fallbackserver.configuration.Configuration;
import dev.gamecrash.fallbackserver.configuration.ConfigurationLoader;
import dev.gamecrash.fallbackserver.server.Server;
import org.spongepowered.configurate.ConfigurateException;

public final class Main {
    static void main(String[] args) throws ConfigurateException {
        Configuration config = ConfigurationLoader.getConfiguration("./server.config");

        Server server = new Server(config);
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
    }


}
