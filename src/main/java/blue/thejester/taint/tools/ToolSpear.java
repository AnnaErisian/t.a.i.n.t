package blue.thejester.taint.tools;

import net.minecraft.nbt.NBTTagCompound;
import shnupbups.tinkersaether.traits.Reach;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.SwordCore;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.modifiers.ModBeheading;

import java.util.List;

//Spear: long range tinker weapon, damage/speed as rapier but 0.7 damage potential. Knifeblade, Binding, 2 Rod.
public class ToolSpear extends SwordCore {


    private static final float DURABILITY_MODIFIER = 1.1f;

    public ToolSpear() {
        super(PartMaterialType.handle(TinkerTools.toolRod),
                PartMaterialType.handle(TinkerTools.toolRod),
                PartMaterialType.head(TinkerTools.knifeBlade),
                PartMaterialType.extra(TinkerTools.binding));

        this.addCategory(Category.WEAPON);

        setTranslationKey("spear").setRegistryName("spear");
    }

    @Override
    public int[] getRepairParts() {
        return new int[] {2};
    }

    @Override
    public float damagePotential() {
        return 0.8f;
    }

    @Override
    public double attackSpeed() {
        return 2.0f;
    }

    @Override
    public float damageCutoff() {
        return 16f;
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

        return data;
    }

    @Override
    public void addMaterialTraits(NBTTagCompound root, List<Material> materials) {
        super.addMaterialTraits(root, materials);

        //Thank you Tinker's Aether
        Reach.reach.apply(root);
    }
}
