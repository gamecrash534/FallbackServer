package dev.gamecrash.fallbackserver.world;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.anvil.AnvilLoader;
import net.minestom.server.world.DimensionType;
import org.tinylog.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class World {
    @Getter @Accessors(fluent = true)
    private Instance instance;

    public World() {
        Logger.debug("Generating new World");
        flatworld();
    }

    public World(Path worldPath) {
        try {
            if (!Files.exists(worldPath)) {
                Logger.warn("The specified world path does not exist. Defaulting to a flat world.");
                flatworld();
                return;
            }

            Logger.info("Loading World...");
            if (Files.list(worldPath).toList().isEmpty()) {
                Logger.warn("The specified world path seems to contain no world. Defaulting to a flat world.");
                flatworld();
                return;
            }

            instance = MinecraftServer.getInstanceManager().createInstanceContainer(
                new AnvilLoader(worldPath, DimensionType.OVERWORLD.key())
            );
        } catch (Exception e) {
            Logger.error("Could not load world. Defaulting to a flat world.");
            Logger.debug(e);
            flatworld();
        }
    }

    private void flatworld() {
        instance = MinecraftServer.getInstanceManager().createInstanceContainer();
        instance.setGenerator(PlaneGenerator::new);
        instance.setChunkSupplier(LightingChunk::new);
    }
}
