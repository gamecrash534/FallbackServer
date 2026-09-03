package dev.gamecrash.fallbackserver.configuration;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.nio.file.Path;

public class ConfigurationLoader {
    private ConfigurationLoader() { }

    public static Configuration load(Path path) throws ConfigurateException {
        HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
            .path(path)
            .build();

        CommentedConfigurationNode node = loader.load();
        return node.get(Configuration.class);
    }

    public static Configuration getDefault() {
        return new Configuration();
    }
}
