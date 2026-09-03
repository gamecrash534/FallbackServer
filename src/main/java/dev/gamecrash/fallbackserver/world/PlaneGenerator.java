package dev.gamecrash.fallbackserver.world;

import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.generator.GenerationUnit;
import net.minestom.server.instance.generator.Generator;

public class PlaneGenerator implements Generator {
    @Override
    public void generate(GenerationUnit unit) {
        Point start = unit.absoluteStart();
        Point end = unit.absoluteEnd();

        for (int x = start.blockX(); x <= end.blockX(); x++) {
            for (int z = start.blockZ(); x <= end.blockZ(); z++) {
                unit.modifier().setBlock(new BlockVec(x, 0, z), Block.BEDROCK);
                unit.modifier().setBlock(new BlockVec(x, 1, z), Block.GRASS_BLOCK);
            }
        }
    }
}
