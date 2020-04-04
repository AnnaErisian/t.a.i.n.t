package blue.thejester.taint.block;

import blue.thejester.taint.core.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockStorage extends Block {

    public BlockStorage(String name) {
        super(Material.IRON);
        this.setRegistryName(name);
        this.setTranslationKey(name);
        this.setSoundType(SoundType.METAL);
        this.setHardness(6.0F);
        this.setResistance(60f);
        this.setHarvestLevel("pickaxe", 2);
        this.setCreativeTab(CommonProxy.taintCreativeTab);
    }
}