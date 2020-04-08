package blue.thejester.taint.traits;

import landmaster.plustic.api.Toggle;
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

public class Smashing extends AbstractTrait {

    public static final Smashing smashing = new Smashing();
    private static final float MANGLE_COBBLE_CHANCE = 0.7f;
    private static final float MANGLE_DIRT_CHANCE = 0.4f;
    private static final float MANGLE_GRAVEL_CHANCE = 0.15f;
    private static final float MANGLE_SAND_CHANCE = 0.3f;

    public Smashing() {
        super("smashing", 0xffffff);
    }

    @Override
    public void blockHarvestDrops(ItemStack tool, BlockEvent.HarvestDropsEvent event) {
        ArrayList<ItemStack> newDrops = new ArrayList<>();
        for(ItemStack d : event.getDrops()) {
            if(d.getTranslationKey().equals("stone")) {
                newDrops.add(new ItemStack(Blocks.COBBLESTONE, d.getCount()));
            }
            else if(d.getTranslationKey().equals("cobblestone") && random.nextFloat() < MANGLE_COBBLE_CHANCE) {
                newDrops.add(new ItemStack(Blocks.GRAVEL, d.getCount()));
            }
            else if(d.getTranslationKey().equals("gravel") && random.nextFloat() < MANGLE_GRAVEL_CHANCE) {
                newDrops.add(new ItemStack(Blocks.SAND, d.getCount()));
            }
            else if((d.getTranslationKey().equals("dirt") || d.getTranslationKey().equals("grass")) && random.nextFloat() < MANGLE_DIRT_CHANCE) {
                newDrops.add(new ItemStack(Blocks.SAND, d.getCount()));
            }
            else if(d.getTranslationKey().equals("sand") && random.nextFloat() < MANGLE_SAND_CHANCE) {
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
