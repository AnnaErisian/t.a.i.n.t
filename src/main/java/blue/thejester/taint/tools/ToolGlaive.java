package blue.thejester.taint.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import shnupbups.tinkersaether.traits.Reach;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.SwordCore;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.tools.TinkerTools;

import javax.annotation.Nonnull;
import java.util.List;

public class ToolGlaive extends SwordCore {


    private static final float DURABILITY_MODIFIER = 1.8f;

    public ToolGlaive() {
        super(PartMaterialType.handle(TinkerTools.toolRod),
                PartMaterialType.handle(TinkerTools.toolRod),
                PartMaterialType.head(TinkerTools.swordBlade),
                PartMaterialType.extra(TinkerTools.binding));

        this.addCategory(Category.WEAPON);

        setTranslationKey("glaive").setRegistryName("glaive");
    }

    // no offhand for you
    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ItemStack itemStackIn = playerIn.getHeldItem(hand);
        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    public int[] getRepairParts() {
        return new int[] {2};
    }

    @Override
    public float damagePotential() {
        return 1.1f;
    }

    @Override
    public double attackSpeed() {
        return 1.2;
    }

    @Override
    public float damageCutoff() {
        return 22f;
    }

    @Override
    public float getRepairModifierForPart(int index) {
        return DURABILITY_MODIFIER;
    }

    @Override
    public ToolNBT buildTagData(List<Material> materials) {
        ToolNBT data = new ToolNBT();

        HandleMaterialStats rod1 = materials.get(0).getStatsOrUnknown(MaterialTypes.HANDLE);
        HandleMaterialStats rod2 = materials.get(1).getStatsOrUnknown(MaterialTypes.HANDLE);
        HeadMaterialStats head = materials.get(2).getStatsOrUnknown(MaterialTypes.HEAD);
        ExtraMaterialStats binding = materials.get(3).getStatsOrUnknown(MaterialTypes.EXTRA);

        data.head(head);
        data.extra(binding);
        data.handle(rod1, rod2);

        data.modifiers = DEFAULT_MODIFIERS;
        data.attack *= 1.15f;
        data.attack += 2f;

        return data;
    }

    @Override
    public void addMaterialTraits(NBTTagCompound root, List<Material> materials) {
        super.addMaterialTraits(root, materials);

        //Thank you Tinker's Aether
        Reach.reach.apply(root);
    }
}
