package dev.gamecrash.fallbackserver.configuration;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.tinylog.Logger;

import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigurationLoader {
    private static final String env_config = "FALLBACKSERVER_CONFIG";

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

    public static Configuration getConfiguration(@Nullable String pathParam) throws ConfigurateException {
        Logger.info("Loading configuration...");
        Path path;

        String envConfigPath = System.getProperty(env_config);
        if (envConfigPath != null) path = Path.of(envConfigPath);
        else if (pathParam != null) path = Path.of(pathParam);
        else path = Path.of(System.getProperty("user.dir"));

        if (Files.exists(path)) {
            if (Files.isDirectory(path)) throw new IllegalArgumentException("Given configuration path must not be a directory");
            return load(path);
        }

        CommentedConfigurationNode node = CommentedConfigurationNode.root();
        node.set(getDefault());

        HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
            .path(path)
            .build();
        loader.save(node);

        return getDefault();
    }
}
