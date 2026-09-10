package dev.gamecrash.fallbackserver.world;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.anvil.AnvilLoader;
import net.minestom.server.world.DimensionType;
import org.tinylog.Logger;

import java.nio.file.Path;

public class World {
    @Getter @Accessors(fluent = true)
    private final Instance instance;

    public World() {
        Logger.debug("Generating new World");
        instance = MinecraftServer.getInstanceManager().createInstanceContainer();
        instance.setGenerator(new PlaneGenerator());
        instance.setChunkSupplier(LightingChunk::new);
    }

    public World(Path worldPath) {
        Logger.info("Loading World...");
        instance = MinecraftServer.getInstanceManager().createInstanceContainer(
            new AnvilLoader(worldPath, DimensionType.OVERWORLD.key())
        );
    }
}
