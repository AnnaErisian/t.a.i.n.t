package blue.thejester.taint.item.vanitools;

import blue.thejester.taint.core.CommonProxy;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSpade;

public class ModShovelItem extends ItemSpade {
    public ModShovelItem(String name, ToolMaterial material) {
        super(material);
        setTranslationKey(name);
        setRegistryName(name);
        this.setCreativeTab(CommonProxy.taintCreativeTab);
    }
}
