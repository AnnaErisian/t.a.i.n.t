package blue.thejester.taint.traits.wands;

import blue.thejester.taint.tools.ToolWand;
import landmaster.plustic.api.Toggle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Nourishing extends AbstractTrait implements IWandTrait {

    public static final Nourishing nourishing = new Nourishing();

    public Nourishing() {
        super("wand_nourishing", 0x54E5FF);
        Toggle.addToggleable(identifier);
    }

    @Override
    public void onCondenserRecharge(ToolWand toolWand, ItemStack stack, World world, Entity entity, int condenserAmount) {
        if(entity instanceof EntityPlayer) {
            if(world.rand.nextFloat() < 0.05 * condenserAmount) {
                int foodLevel = ((EntityPlayer) entity).getFoodStats().getFoodLevel();
                float satLevel = ((EntityPlayer) entity).getFoodStats().getSaturationLevel();
                if(foodLevel < 14) {
                    ((EntityPlayer) entity).getFoodStats().setFoodLevel(foodLevel+1);
                }
                if(satLevel < 14) {
                    ((EntityPlayer) entity).getFoodStats().setFoodSaturationLevel(satLevel+1);
                }
            }
        }
    }
}
