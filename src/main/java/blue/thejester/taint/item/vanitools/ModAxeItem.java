package blue.thejester.taint.item.vanitools;

import blue.thejester.taint.core.CommonProxy;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;

public class ModAxeItem extends ItemAxe {
    public ModAxeItem(String name, ToolMaterial material, float damage, float speed) {
        super(material, damage, speed);
        setTranslationKey(name);
        setRegistryName(name);
        this.setCreativeTab(CommonProxy.taintCreativeTab);
    }
}
