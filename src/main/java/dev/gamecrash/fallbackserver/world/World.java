package dev.gamecrash.fallbackserver.world;

import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.anvil.AnvilLoader;
import net.minestom.server.world.DimensionType;

import java.nio.file.Path;

public class World {
    private final Instance instance;

    public World() {
        instance = MinecraftServer.getInstanceManager().createInstanceContainer();
        instance.setGenerator(new PlaneGenerator());
    }

    public World(Path worldPath) {
        instance = MinecraftServer.getInstanceManager().createInstanceContainer(
            new AnvilLoader(worldPath, DimensionType.OVERWORLD.key())
        );
    }
}
