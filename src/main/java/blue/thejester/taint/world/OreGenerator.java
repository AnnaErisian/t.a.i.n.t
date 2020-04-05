package blue.thejester.taint.world;

import blue.thejester.taint.block.OreBlocks;
import com.legacy.aether.AetherConfig;
import com.legacy.aether.blocks.BlocksAether;
import com.shinoow.beneath.Beneath;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import thebetweenlands.common.block.structure.BlockSlabBetweenlands;
import thebetweenlands.common.config.BetweenlandsConfig;
import thebetweenlands.common.registries.BlockRegistry;
import twilightforest.TFConfig;
import twilightforest.biomes.TFBiomeFireSwamp;
import twilightforest.biomes.TFBiomes;

import java.util.Random;
import com.google.common.base.Predicate;
import twilightforest.block.TFBlocks;

public class OreGenerator implements IWorldGenerator {

    private static final int VEIN_SIZE_ARDORUM = 6;
    private static final int SPAWN_TRIES_ARDORUM = 10;
    private static final int MIN_HEIGHT_ARDORUM = 2;
    private static final int MAX_HEIGHT_ARDORUM = 26;

    private static final int VEIN_SIZE_TERMIUM = 6;
    private static final int SPAWN_TRIES_TERMIUM = 14;
    private static final int MIN_HEIGHT_TERMIUM = 2;
    private static final int MAX_HEIGHT_TERMIUM = 45;

    private static final int VEIN_SIZE_ADIPATUM = 6;
    private static final int SPAWN_TRIES_ADIPATUM = 3;
    private static final int MIN_HEIGHT_ADIPATUM = 2;
    private static final int MAX_HEIGHT_ADIPATUM = 30;

    private static final int VEIN_SIZE_CAERSIN = 3;
    private static final int SPAWN_TRIES_CAERSIN = 8;
    private static final int MIN_HEIGHT_CAERSIN = 10;
    private static final int MAX_HEIGHT_CAERSIN = 80;

    private static final int VEIN_SIZE_NEULITE = 7;
    private static final int SPAWN_TRIES_NEULITE = 5 ;
    private static final int MIN_HEIGHT_NEULITE = 10;
    private static final int MAX_HEIGHT_NEULITE = 35;

    private static final int VEIN_SIZE_ATERCAEUM = 6;
    private static final int SPAWN_TRIES_ATERCAEUM = 4;
    private static final int MIN_HEIGHT_ATERCAEUM = 10;
    private static final int MAX_HEIGHT_ATERCAEUM = 246;

    private static final int VEIN_SIZE_OSCURUM = 8;
    private static final int SPAWN_TRIES_OSCURUM = 2;
    private static final int MIN_HEIGHT_OSCURUM = 10;
    private static final int MAX_HEIGHT_OSCURUM = 80;

    private static final int VEIN_SIZE_INUROSE = 8;
    private static final int SPAWN_TRIES_INUROSE = 2;
    private static final int MIN_HEIGHT_INUROSE = 90;
    private static final int MAX_HEIGHT_INUROSE = 160;

    private static final int VEIN_SIZE_CIBARITE = 8;
    private static final int SPAWN_TRIES_CIBARITE = 2;
    private static final int MIN_HEIGHT_CIBARITE = 170;
    private static final int MAX_HEIGHT_CIBARITE = 246;

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int dimId = world.provider.getDimension();
        Biome biome = world.getBiome(new BlockPos(chunkX * 16, 0, chunkZ * 16));
        if(dimId == 0) {
            //Overworld
        } else if (dimId == -1) {
            //Nether
        } else if (dimId == 1) {
            //End
            runGenerator(OreBlocks.oreCaersin.getDefaultState(), VEIN_SIZE_CAERSIN, SPAWN_TRIES_CAERSIN, MIN_HEIGHT_CAERSIN, MAX_HEIGHT_CAERSIN, BlockMatcher.forBlock(Blocks.END_STONE), world, random, chunkX, chunkZ);
        } else if (dimId == BetweenlandsConfig.WORLD_AND_DIMENSION.dimensionId) {
            //Betweenlands
            runGenerator(OreBlocks.oreAdipatum.getDefaultState(), VEIN_SIZE_ADIPATUM, SPAWN_TRIES_ADIPATUM, MIN_HEIGHT_ADIPATUM, MAX_HEIGHT_ADIPATUM, BlockMatcher.forBlock(BlockRegistry.PITSTONE), world, random, chunkX, chunkZ);
        } else if (dimId == AetherConfig.dimension.aether_dimension_id) {
            //Aether
            runGenerator(OreBlocks.oreNeulite.getDefaultState(), VEIN_SIZE_NEULITE, SPAWN_TRIES_NEULITE, MIN_HEIGHT_NEULITE, MAX_HEIGHT_NEULITE, BlockMatcher.forBlock(BlocksAether.holystone), world, random, chunkX, chunkZ);
        } else if (dimId == TFConfig.dimension.dimensionID) {
            //Twilight Forest
            if(biome == TFBiomes.fireSwamp) {
                //Fire Swamp
                runGenerator(OreBlocks.oreArdorum.getDefaultState(), VEIN_SIZE_ARDORUM, SPAWN_TRIES_ARDORUM, MIN_HEIGHT_ARDORUM, MAX_HEIGHT_ARDORUM, BlockMatcher.forBlock(Blocks.STONE), world, random, chunkX, chunkZ);
            } else if(biome == TFBiomes.highlandsCenter) {
                //Final Plateau
                runGenerator(OreBlocks.oreTermium.getDefaultState(), VEIN_SIZE_TERMIUM, SPAWN_TRIES_TERMIUM, MIN_HEIGHT_TERMIUM, MAX_HEIGHT_TERMIUM, BlockMatcher.forBlock(TFBlocks.deadrock), world, random, chunkX, chunkZ);
            }
        } else if (dimId == Beneath.dim) {
            //Beneath
            runGenerator(OreBlocks.oreAtercaeum.getDefaultState(), VEIN_SIZE_ATERCAEUM, SPAWN_TRIES_ATERCAEUM, MIN_HEIGHT_ATERCAEUM, MAX_HEIGHT_ATERCAEUM, BlockMatcher.forBlock(Blocks.STONE), world, random, chunkX, chunkZ);
            runGenerator(OreBlocks.oreOscurum.getDefaultState(), VEIN_SIZE_OSCURUM, SPAWN_TRIES_OSCURUM, MIN_HEIGHT_OSCURUM, MAX_HEIGHT_OSCURUM, BlockMatcher.forBlock(Blocks.STONE), world, random, chunkX, chunkZ);
            runGenerator(OreBlocks.oreInurose.getDefaultState(), VEIN_SIZE_INUROSE, SPAWN_TRIES_INUROSE, MIN_HEIGHT_INUROSE, MAX_HEIGHT_INUROSE, BlockMatcher.forBlock(Blocks.STONE), world, random, chunkX, chunkZ);
            runGenerator(OreBlocks.oreCibarite.getDefaultState(), VEIN_SIZE_CIBARITE, SPAWN_TRIES_CIBARITE, MIN_HEIGHT_CIBARITE, MAX_HEIGHT_CIBARITE, BlockMatcher.forBlock(Blocks.STONE), world, random, chunkX, chunkZ);
        }
    }

    private void runGenerator(IBlockState blockToGen, int blockAmount, int chancesToSpawn, int minHeight, int maxHeight,
                              Predicate<IBlockState> blockToReplace, World world, Random rand, int chunk_X, int chunk_Z){
        if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
            throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

        WorldGenMinable generator = new WorldGenMinable(blockToGen, blockAmount, blockToReplace);
        int heightdiff = maxHeight - minHeight +1;
        for (int i=0; i<chancesToSpawn; i++){
            int x = chunk_X * 16 +rand.nextInt(16);
            int y = minHeight + rand.nextInt(heightdiff);
            int z = chunk_Z * 16 + rand.nextInt(16);

            generator.generate(world, rand, new BlockPos(x, y, z));
        }
    }
}
