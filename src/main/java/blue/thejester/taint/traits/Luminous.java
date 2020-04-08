package blue.thejester.taint.traits;

import landmaster.plustic.api.Sounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.shared.TinkerCommons;

public class Luminous extends AbstractTrait {

    public static final Luminous luminous = new Luminous();
    private static final int COST = 1;

    public Luminous() {
        super("luminous", 0xffffaa);
        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void captureEntity(PlayerInteractEvent.RightClickBlock event) {
        NBTTagCompound nbt = TagUtil.getTagSafe(event.getItemStack());
        if (event.getWorld().isRemote
                || event.getEntityPlayer().isSneaking()
                || event.getItemStack() == null
                || !TinkerUtil.hasTrait(nbt, getIdentifier())
                || ToolHelper.getCurrentDurability(event.getItemStack()) < COST) {
            return;
        }
        BlockPos pos = event.getPos();
        TinkerCommons.blockGlow.addGlow(event.getWorld(), event.getPos().offset(event.getFace()), event.getFace());
        ToolHelper.damageTool(event.getItemStack(), COST, event.getEntityLiving());
        event.getEntityPlayer().swingArm(event.getHand());
        event.setCanceled(true);
        event.setCancellationResult(EnumActionResult.SUCCESS);
    }
}
