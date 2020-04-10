package blue.thejester.taint.item;

import blue.thejester.taint.block.BlockStorage;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static cofh.core.util.helpers.RecipeHelper.addTwoWayStorageRecipe;

//import static cofh.core.util.helpers.RecipeHelper.addTwoWayStorageRecipe;

public enum MetalMaterial {

//    sageslime,
//    chaoite,
//    tritanium,
//    white_steel,
//    alubronze,
//    caelite,
//    atmium,
//    pelagium,
//    sonium,
//    caeputescite,
//    yellow_cobalt,
//    aureclase,
//    cobalt_thorium_g,
//    florium,
//    gourmium,
//    alcite,
//    aggrite,
//    cloudiron,
//    astralium,
//    bavarium,
//    assarion,
//    dyrnwynite,
//    tetramentium,
//    cyrium,
//    pyrium,
//    tinardite,
//    daemotium,
    escalite,
    slipsteel,
    faerite,
    black_rosite,
    piridium,
    luginite,
    peractium,
    sestertium,
    infernium,
    littium,
    lordslime,
    ascalite,
    aerium,
    cannium,
    lunite,
    betweensteel,
    unseelium,
    tempestite,
    rallium,
    fierkite,
    orinase,
    superlumium,
    terium,
    soldrite,
    accelerase,
    antenium,
    orichalcum,
    urielium,
    dupondium,
    adamantite,
    starsteel,
    ptemnium,
    solairite,


    ardorum,
    termium,
    adipatum,
    caersin,
    neulite,
    atercaeum,
    oscurum,
    inurose,
    cibarite;

    public Item ingot = null;
    public ItemStack ingotStack = null;
    public Item nugget = null;
    public ItemStack nuggetStack = null;
    public Block block = null;
    public Item blockItem = null;
    public ItemStack blockItemStack = null;
    public Fluid fluid = null;

    public String getOreName() {
        List<String> list = new ArrayList<>();
        for (String i : this.name().split("_")) {
            String s = i.substring(0, 1).toUpperCase() + i.substring(1);
            list.add(s);
        }
        return String.join("", list);
    }


    public static boolean makeItems() {
        for (MetalMaterial mat : MetalMaterial.values()) {
            mat.nugget = new ItemMaterial("nugget_" + mat.name());
            mat.nuggetStack = new ItemStack(mat.nugget);
            mat.ingot = new ItemMaterial("ingot_" + mat.name());
            mat.ingotStack = new ItemStack(mat.ingot);
            mat.block = new BlockStorage("block_" + mat.name());
            mat.blockItem = new ItemBlock(mat.block).setRegistryName(mat.block.getRegistryName());
            mat.blockItemStack = new ItemStack(mat.blockItem);
        }
        return true;
    }

    public static boolean makeRecipes() {
        for (MetalMaterial mat : MetalMaterial.values()) {
            addTwoWayStorageRecipe(mat.ingotStack, mat.nuggetStack);
            addTwoWayStorageRecipe(mat.blockItemStack, mat.ingotStack);
        }
        return true;
    }


    public static boolean registerOreDict() {
        for (MetalMaterial mat : MetalMaterial.values()) {
            OreDictionary.registerOre("nugget" + mat.getOreName(), mat.nuggetStack);
            OreDictionary.registerOre("ingot" + mat.getOreName(), mat.ingotStack);
            OreDictionary.registerOre("block" + mat.getOreName(), mat.blockItemStack);
        }
        return true;
    }

    public static boolean sendSmelteryIMC() {
        for (MetalMaterial mat : MetalMaterial.values()) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("fluid", mat.name());
            tag.setString("ore", mat.getOreName().substring(0, 1).toUpperCase(Locale.ROOT) + mat.getOreName().substring(1));


            tag.setBoolean("toolforge", true);
            FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tag);
        }
        return true;
    }
}
