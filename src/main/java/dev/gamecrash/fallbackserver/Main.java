package dev.gamecrash.fallbackserver;

import dev.gamecrash.fallbackserver.configuration.Configuration;
import dev.gamecrash.fallbackserver.configuration.ConfigurationLoader;
import dev.gamecrash.fallbackserver.server.Server;
import dev.gamecrash.fallbackserver.util.DynamicLevelLoggingProvider;
import lombok.SneakyThrows;
import net.minestom.server.MinecraftServer;
import net.minestom.server.timer.TaskSchedule;
import org.spongepowered.configurate.ConfigurateException;
import org.tinylog.Level;
import org.tinylog.Logger;
import org.tinylog.core.ConfigurationParser;
import org.tinylog.provider.LoggingProvider;
import org.tinylog.provider.ProviderRegistry;
import org.tinylog.writers.Writer;

import java.time.temporal.TemporalUnit;
import java.util.Locale;
import java.util.Properties;

public final class Main {
    private static Configuration config;
    private static Server server;

    static void main(String[] args) {
        Logger.info("Starting...");
        System.setProperty("minestom.shutdown-on-signal", "false");

        try {
            config = ConfigurationLoader.getConfiguration("./server.config");
            DynamicLevelLoggingProvider.setActiveLevel(
                Level.valueOf(config.logger.level.toUpperCase(Locale.ROOT))
            );

            server = new Server(config);
            Runtime.getRuntime().addShutdownHook(new Thread(Main::shutdown));

            server.start();
            Logger.info("Minecraft Version: {} ({})", MinecraftServer.VERSION_NAME, MinecraftServer.PROTOCOL_VERSION);
        } catch (ConfigurateException e) {
            Logger.error("Error while trying to load configuration:", e);
            Logger.warn("exiting");
            System.exit(1);
        }
    }

    static void shutdown() {
        Logger.info("Shutting down");
        try {
            server.stop();
            ProviderRegistry.getLoggingProvider().shutdown();
        } catch (InterruptedException ignored) { }
    }
}
