package blue.thejester.taint.item.vanitools;

import blue.thejester.taint.core.CommonProxy;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSpade;

public class ModHoeItem extends ItemHoe {
    public ModHoeItem(String name, ToolMaterial material) {
        super(material);
        setTranslationKey(name);
        setRegistryName(name);
        this.setCreativeTab(CommonProxy.taintCreativeTab);
    }
}
