package blue.thejester.taint.tools;

import electroblob.wizardry.Wizardry;
import electroblob.wizardry.item.IManaStoringItem;
import electroblob.wizardry.item.ItemManaFlask;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * This entire class is a copy of RecipeRechargeWithFlash from EBWizardry
 * EXCEPT we copy the NBT before repairing the wand's mana (line 67/68).
 * This lets the wand actually know how much it's max mana is when restoring it
 */
@Mod.EventBusSubscriber
public class RecipeRechargeTinkerWandWithFlask extends ShapelessOreRecipe {

    private final IManaStoringItem chargeable;
    private final ItemManaFlask flask;

    /**
     * Creates a new charging recipe for the given chargeable item using the given flask item.
     * @param chargeable The type of item to be charged
     * @param flask The mana flask used to charge the item
     */
    public RecipeRechargeTinkerWandWithFlask(Item chargeable, ItemManaFlask flask){
        super(null, new ItemStack(chargeable, 1, OreDictionary.WILDCARD_VALUE),
                new ItemStack(chargeable, 1, OreDictionary.WILDCARD_VALUE), flask);
        if(!(chargeable instanceof IManaStoringItem)) throw new IllegalArgumentException("Item to be charged must be an instance of IManaStoringItem");
        this.chargeable = (IManaStoringItem)chargeable;
        this.flask = flask;
    }

    // Commented out for now because JEI spams the log with errors about the recipe having no output
//	@Nonnull
//	@Override
//	public ItemStack getRecipeOutput(){
//		return ItemStack.EMPTY; // According to the javadoc, dynamic recipes are supposed to return an empty stack here
//	}

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv){
        ItemStack result = super.getCraftingResult(inv);
        rechargeItemAndCopyNBT(result, inv);
        return result;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world){
        ItemStack stack = findItemToCharge(inv);
        if(!stack.isEmpty() && chargeable.isManaFull(stack)) return false;
        return super.matches(inv, world);
    }

    private void rechargeItemAndCopyNBT(ItemStack toCharge, InventoryCrafting inv){
        if(toCharge.getItem() == chargeable){
            ItemStack stack = findItemToCharge(inv);
            if(!stack.isEmpty()) chargeable.setMana(toCharge, chargeable.getMana(stack));
            toCharge.setTagCompound(stack.getTagCompound()); // Copy NBT to new stack
            chargeable.rechargeMana(toCharge, flask.size.capacity);
        }else{
            Wizardry.logger.warn("Tried to recharge item {} with mana flask, but it did not match the recipe result {}!", toCharge.getItem(), chargeable);
        }
    }

    private ItemStack findItemToCharge(InventoryCrafting inv){
        for(int i=0; i<inv.getSizeInventory(); i++){
            ItemStack ingredient = inv.getStackInSlot(i);
            if(ingredient.getItem() == chargeable) return ingredient;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isDynamic(){
        return true; // Stops it appearing in the recipe book
    }

    @SubscribeEvent
    public static void onItemCraftedEvent(PlayerEvent.ItemCraftedEvent event){
        // getCraftingResult seems to only work for the result that's displayed, not once it is actually taken
        // This means that although I no longer have to replace the result every tick, I still need to do it here
        // ... I thought the whole point of the new recipe system was so that I DIDN'T have to do this?!
        if(event.craftMatrix instanceof InventoryCrafting){
            for(IRecipe recipe : CraftingManager.REGISTRY){
                if(recipe instanceof RecipeRechargeTinkerWandWithFlask
                        && recipe.matches((InventoryCrafting)event.craftMatrix, event.player.world)){
                    // Have to modify the itemstack in the actual event, it cannot be replaced
                    ((RecipeRechargeTinkerWandWithFlask)recipe).rechargeItemAndCopyNBT(event.crafting, (InventoryCrafting)event.craftMatrix);
                }
            }
        }
    }
}
