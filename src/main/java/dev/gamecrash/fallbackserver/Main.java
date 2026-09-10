package dev.gamecrash.fallbackserver;

import dev.gamecrash.fallbackserver.configuration.Configuration;
import dev.gamecrash.fallbackserver.configuration.ConfigurationLoader;
import dev.gamecrash.fallbackserver.server.Server;
import dev.gamecrash.fallbackserver.util.DynamicLevelLoggingProvider;
import net.minestom.server.MinecraftServer;
import net.minestom.server.timer.TaskSchedule;
import org.spongepowered.configurate.ConfigurateException;
import org.tinylog.Level;
import org.tinylog.Logger;
import org.tinylog.provider.ProviderRegistry;

import java.time.temporal.TemporalUnit;
import java.util.Locale;

public final class Main {
    static void main(String[] args) {
        Logger.info("Starting...");

        try {
            Configuration config = ConfigurationLoader.getConfiguration("./server.config");
            DynamicLevelLoggingProvider.setActiveLevel(
                Level.valueOf(config.logger.level.toUpperCase(Locale.ROOT))
            );

            Server server = new Server(config);
            Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
            server.start();
            Logger.info("Minecraft Version: {} ({})", MinecraftServer.VERSION_NAME, MinecraftServer.PROTOCOL_VERSION);
        } catch (ConfigurateException e) {
            Logger.error("Error while trying to load configuration:", e);
            Logger.warn("exiting");
            System.exit(1);
        }
    }
}
