package dev.gamecrash.fallbackserver.world;

import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.generator.GenerationUnit;
import net.minestom.server.instance.generator.Generator;

public class PlaneGenerator implements Generator {
    @Override
    public void generate(GenerationUnit unit) {
        unit.modifier().fillHeight(-1, 0, Block.BEDROCK);
        unit.modifier().fillHeight(0, 1, Block.GRASS_BLOCK);
    }
}
