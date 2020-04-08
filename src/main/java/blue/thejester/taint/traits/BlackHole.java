package blue.thejester.taint.traits;

import landmaster.plustic.api.Toggle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.shared.TinkerCommons;

public class BlackHole extends AbstractTrait {

    public static final BlackHole blackhole = new BlackHole();

    public BlackHole() {
        super("blackhole", 0xffffff);
        Toggle.addToggleable(identifier);
    }

    @Override
    public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
        if(damage == 1 && Toggle.getToggleState(tool, identifier)) return 0;
        else return super.onToolDamage(tool, damage, newDamage, entity);
    }

    @Override
    public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
        if (!Toggle.getToggleState(tool, identifier)) return;
        super.afterBlockBreak(tool, world, state, pos, player, wasEffective);
        if(wasEffective) ToolHelper.healTool(tool, 1, player);
    }

    @Override
    public void blockHarvestDrops(ItemStack tool, BlockEvent.HarvestDropsEvent event) {
        if (!Toggle.getToggleState(tool, identifier)) return;
        event.getDrops().clear();
        super.blockHarvestDrops(tool, event);
    }

}
