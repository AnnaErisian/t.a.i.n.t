package blue.thejester.taint.core;

import blue.thejester.taint.Taint;
import blue.thejester.taint.tools.RecipeRechargeTinkerWandWithFlask;
import electroblob.wizardry.item.IManaStoringItem;
import electroblob.wizardry.item.ItemManaFlask;
import electroblob.wizardry.registry.WizardryItems;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This is basically verbatim from the EBWizardry codebase (electroblob.wizardry.registry.WizardryRecipes)
 */
@Mod.EventBusSubscriber
public class RecipeRegistry {


    private static final Queue<Item> chargingRecipeQueue = new LinkedList<>();

    /**
     * Adds the given item to the list of items that can be charged using mana flasks. Dynamic charging recipes
     * will be added for these items during {@code RegistryEvent.Register<IRecipe>}. The item must implement
     * {@link IManaStoringItem} for the recipes to work correctly. This method should be called from the item's
     * constructor.
     */
    public static void addToManaFlaskCharging(Item item) {
        chargingRecipeQueue.offer(item);
    }

    /**
     * Now only deals with the dynamic crafting recipes and the smelting recipes.
     */
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {

        IForgeRegistry<IRecipe> registry = event.getRegistry();

        Item chargeable;

        while (!chargingRecipeQueue.isEmpty()) {
            // Use remove() and not poll() because the queue shouldn't be empty in here
            chargeable = chargingRecipeQueue.remove();

            // Mana flask recipes
            registry.register(new RecipeRechargeTinkerWandWithFlask(chargeable, (ItemManaFlask) WizardryItems.small_mana_flask)
                    .setRegistryName(new ResourceLocation(Taint.MODID, "recipes/small_flask_" + chargeable.getRegistryName().getPath())));

            registry.register(new RecipeRechargeTinkerWandWithFlask(chargeable, (ItemManaFlask) WizardryItems.medium_mana_flask)
                    .setRegistryName(new ResourceLocation(Taint.MODID, "recipes/medium_flask_" + chargeable.getRegistryName().getPath())));

            registry.register(new RecipeRechargeTinkerWandWithFlask(chargeable, (ItemManaFlask) WizardryItems.large_mana_flask)
                    .setRegistryName(new ResourceLocation(Taint.MODID, "recipes/large_flask_" + chargeable.getRegistryName().getPath())));

        }
    }
}
