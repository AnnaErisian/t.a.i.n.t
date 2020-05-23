package blue.thejester.taint.item.vanitools;

import blue.thejester.taint.core.CommonProxy;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;

public class ModSwordItem extends ItemSword {
    public ModSwordItem(String name, ToolMaterial material) {
        super(material);
        setTranslationKey(name);
        setRegistryName(name);
        this.setCreativeTab(CommonProxy.taintCreativeTab);
    }
}
