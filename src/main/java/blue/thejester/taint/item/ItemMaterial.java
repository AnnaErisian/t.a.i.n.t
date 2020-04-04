package blue.thejester.taint.item;

import blue.thejester.taint.core.CommonProxy;
import net.minecraft.item.Item;

public class ItemMaterial extends Item{

    public ItemMaterial(String name) {
        setTranslationKey(name);
        setRegistryName(name);
        this.setCreativeTab(CommonProxy.taintCreativeTab);
    }

}
