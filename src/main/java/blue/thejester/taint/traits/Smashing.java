package blue.thejester.taint.traits;

import landmaster.plustic.api.Toggle;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Smashing extends AbstractTrait {

    public static final Smashing smashing = new Smashing();
    private static final float MANGLE_COBBLE_CHANCE = 0.7f;
    private static final float MANGLE_DIRT_CHANCE = 0.4f;
    private static final float MANGLE_GRAVEL_CHANCE = 0.15f;
    private static final float MANGLE_SAND_CHANCE = 0.3f;

    //TODO: these should be maps so we can appropriately handle mod dimension rocks, decorative stone, et cetera
    private static final Map<String, Block> smootheStones = new HashMap<>();
    private static final Map<String, Block> cobbleStones = new HashMap<>();
    private static final Map<String, Block> gravels = new HashMap<>();
    private static final Map<String, Block> dirts = new HashMap<>();
    private static final List<String> sands = new ArrayList();
    static {
        smootheStones.put("minecraft:stone", Blocks.COBBLESTONE);
        cobbleStones.put("minecraft:cobblestone", Blocks.GRAVEL);
        gravels.put("minecraft:gravel", Blocks.SAND);
        dirts.put("minecraft:dirt", Blocks.SAND);
        dirts.put("minecraft:grass", Blocks.SAND);
        sands.add("minecraft:sand");
    }

    public Smashing() {
        super("smashing", 0xffffff);
    }

    @Override
    public void blockHarvestDrops(ItemStack tool, BlockEvent.HarvestDropsEvent event) {
        ArrayList<ItemStack> newDrops = new ArrayList<>();
        for(ItemStack d : event.getDrops()) {
            String name = d.getItem().getRegistryName().toString();
            if(smootheStones.containsKey(name)) {
                newDrops.add(new ItemStack(smootheStones.get(name), d.getCount()));
            }
            else if(random.nextFloat() < MANGLE_COBBLE_CHANCE && cobbleStones.containsKey(name)) {
                newDrops.add(new ItemStack(cobbleStones.get(name), d.getCount()));
            }
            else if(random.nextFloat() < MANGLE_GRAVEL_CHANCE && gravels.containsKey(name)) {
                newDrops.add(new ItemStack(gravels.get(name), d.getCount()));
            }
            else if(random.nextFloat() < MANGLE_DIRT_CHANCE && dirts.containsKey(name)) {
                newDrops.add(new ItemStack(dirts.get(name), d.getCount()));
            }
            else if(random.nextFloat() < MANGLE_SAND_CHANCE && sands.contains(name)) {
                //nothing - destroy the sand
            } else {
                newDrops.add(d);
            }
        }
        event.getDrops().clear();
        event.getDrops().addAll(newDrops);
        super.blockHarvestDrops(tool, event);
    }

}
