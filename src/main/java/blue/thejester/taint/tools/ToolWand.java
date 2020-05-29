package blue.thejester.taint.tools;

import blue.thejester.taint.item.ModItems;
import blue.thejester.taint.modules.Tools;
import com.google.common.collect.Multimap;
import electroblob.wizardry.Wizardry;
import electroblob.wizardry.constants.Constants;
import electroblob.wizardry.constants.Tier;
import electroblob.wizardry.data.SpellGlyphData;
import electroblob.wizardry.data.WizardData;
import electroblob.wizardry.entity.living.ISummonedCreature;
import electroblob.wizardry.event.SpellCastEvent;
import electroblob.wizardry.item.IManaStoringItem;
import electroblob.wizardry.item.ISpellCastingItem;
import electroblob.wizardry.item.IWorkbenchItem;
import electroblob.wizardry.item.ItemWand;
import electroblob.wizardry.packet.PacketCastSpell;
import electroblob.wizardry.packet.WizardryPacketHandler;
import electroblob.wizardry.registry.*;
import electroblob.wizardry.spell.Spell;
import electroblob.wizardry.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.TinkerToolCore;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.TinkerTools;

import java.util.List;
import java.util.Random;

/**
 * Since Java does not allow multiple-inheritance, I basically copied and
 *   modified all of the code in ItemWand from EBWizardry
 */
@Mod.EventBusSubscriber
public class ToolWand extends TinkerToolCore implements IWorkbenchItem, ISpellCastingItem, IManaStoringItem {

    public ToolWand() {
        super(  PartMaterialType.head(TinkerTools.arrowHead),
                PartMaterialType.extra(TinkerTools.binding),
                new PartMaterialType(Tools.wandCore, "wand_core", MaterialTypes.HANDLE));

        this.addCategory(Category.WEAPON);

        setTranslationKey("magicwand").setRegistryName("magicwand");

        WizardryRecipes.addToManaFlaskCharging(this);
    }

    @Override
    public int[] getRepairParts() {
        return new int[] {0};
    }

    @Override
    public float damagePotential() {
        return 0.3f;
    }

    @Override
    public float damageCutoff() {
        return 6f;
    }

    @Override
    public double attackSpeed() {
        return 1.2f;
    }

    @Override
    public float knockback() {
        return 0.5f;
    }

    @Override
    public ToolNBT buildTagData(List<Material> materials) {
        WandNBT data = new WandNBT();

        HeadMaterialStats head = materials.get(0).getStatsOrUnknown(MaterialTypes.HEAD);
        ExtraMaterialStats extra = materials.get(1).getStatsOrUnknown(MaterialTypes.EXTRA);
        HandleMaterialStats handle = materials.get(2).getStatsOrUnknown(MaterialTypes.HANDLE);
        WandCoreMaterialStats wandcore = materials.get(2).getStatsOrUnknown(WandCoreMaterialStats.TYPE);
        data.head(head);

        data.extra(extra);

        // calculate handle impact
        data.handle(handle);

        //wand core effects
        data.wandCore(wandcore);

        // 3 free modifiers
        data.modifiers = DEFAULT_MODIFIERS;

        return data;
    }

    private Tier getWizardryTier(ItemStack stack) {
        switch (this.getCastingLevel(stack)) {
            case 0:
            case 1: return Tier.NOVICE;
            case 2: return Tier.APPRENTICE;
            case 3: return Tier.ADVANCED;
            default: return Tier.MASTER;
        }
    }

    private int getCastingLevel(ItemStack stack) {
        return ToolHelper.getHarvestLevelStat(stack);
    }

    private int getUpgradeLimit(ItemStack stack) {
        return 1 + 2 * Math.max(8, getCastingLevel(stack));
    }

    private boolean canBoUpgraded(ItemStack stack, Item specialUpgrade) {
        if(WandHelper.getTotalUpgrades(stack) < getUpgradeLimit(stack)) {
            if(specialUpgrade == WizardryItems.attunement_upgrade) {
                return WandHelper.getUpgradeLevel(stack, specialUpgrade) < Constants.UPGRADE_STACK_LIMIT;
            } else {
                return WandHelper.getUpgradeLevel(stack, specialUpgrade) < getEnhancedUpgradeStackLimit(stack);
            }
        } else {
            return false;
        }
    }

    private int getEnhancedUpgradeStackLimit(ItemStack stack) {
        return Math.min(Constants.UPGRADE_STACK_LIMIT, (getCastingLevel(stack) + 1) / 2);
    }

    private float getPotencyIncrease(ItemStack stack, Spell spell) {
        WandNBT nbt = new WandNBT(TagUtil.getToolTag(stack));
        switch (spell.getElement()) {
            case FIRE: return nbt.fire;
            case ICE: return nbt.ice;
            case LIGHTNING: return nbt.lightning;
            case NECROMANCY: return nbt.necro;
            case EARTH: return nbt.earth;
            case SORCERY: return nbt.sorc;
            case HEALING: return nbt.healing;
        }
        return 0;
    }

    /*==================================================================================================================
     * =================================================================================================================
       ===============================================================================================================*/


    /** The number of spell slots a wand has with no attunement upgrades applied. */
    public static final int BASE_SPELL_SLOTS = 5;

    /** The number of ticks between each time a continuous spell is added to the player's recently-cast spells. */
    private static final int CONTINUOUS_TRACKING_INTERVAL = 20;


    @Override
    public Spell getCurrentSpell(ItemStack stack){
        return WandHelper.getCurrentSpell(stack);
    }

    @Override
    public Spell[] getSpells(ItemStack stack){
        return WandHelper.getSpells(stack);
    }

    @Override
    public void selectNextSpell(ItemStack stack){
        WandHelper.selectNextSpell(stack);
    }

    @Override
    public void selectPreviousSpell(ItemStack stack){
        WandHelper.selectPreviousSpell(stack);
    }

    @Override
    public boolean showSpellHUD(EntityPlayer player, ItemStack stack){
        return true;
    }

    @Override
    public boolean showTooltip(ItemStack stack){
        return true;
    }

    /** Does nothing, use {@link ToolWand#setMana(ItemStack, int)} to modify wand mana. */
    @Override
    public void setDamage(ItemStack stack, int damage){
        // Overridden to do nothing to stop repair things from 'repairing' the mana in a wand
    }

    @Override
    public void setMana(ItemStack stack, int mana){
        // Using super (which can only be done from in here) bypasses the above override
        super.setDamage(stack, getManaCapacity(stack) - mana);
    }

    @Override
    public int getMana(ItemStack stack){
        return getManaCapacity(stack) - getDamage(stack);
    }

    @Override
    public int getManaCapacity(ItemStack stack){
        return this.getMaxDamage(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D(){
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public net.minecraft.client.gui.FontRenderer getFontRenderer(ItemStack stack){
        return Wizardry.proxy.getFontRenderer(stack);
    }

    @Override
    public boolean isEnchantable(ItemStack stack){
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book){
        return false;
    }

//    @Override
//    public boolean hasEffect(ItemStack stack){
//        return !Wizardry.settings.legacyWandLevelling && getWizardryTier(stack).level < Tier.MASTER.level
//                && WandHelper.getProgression(stack) >= Tier.values()[getWizardryTier(stack).ordinal() + 1].progression;
//    }

    // Max damage is modifiable with upgrades.
    @Override
    public int getMaxDamage(ItemStack stack){
        // + 0.5f corrects small float errors rounding down
        return (int)(super.getMaxDamage(stack) * (1.0f + Constants.STORAGE_INCREASE_PER_LEVEL
                * WandHelper.getUpgradeLevel(stack, WizardryItems.storage_upgrade)) + 0.5f);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn){
        setMana(stack, 0); // Wands are empty when first crafted
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isHeld){

        WandHelper.decrementCooldowns(stack);

        // Decrements wand damage (increases mana) every 1.5 seconds if it has a condenser upgrade
        if(!world.isRemote && !this.isManaFull(stack) && world.getTotalWorldTime() % Constants.CONDENSER_TICK_INTERVAL == 0){
            // If the upgrade level is 0, this does nothing anyway.
            this.rechargeMana(stack, WandHelper.getUpgradeLevel(stack, WizardryItems.condenser_upgrade));
        }
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack){

        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);

        if(slot == EntityEquipmentSlot.MAINHAND){
            int level = WandHelper.getUpgradeLevel(stack, WizardryItems.melee_upgrade);
            // This check doesn't affect the damage output, but it does stop a blank line from appearing in the tooltip.
            if(level > 0 && !this.isManaEmpty(stack)){
                multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                        new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Melee upgrade modifier", 2 * level, 0));
                multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Melee upgrade modifier", -2.4000000953674316D, 0));
            }
        }

        return multimap;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase wielder){

        int level = WandHelper.getUpgradeLevel(stack, WizardryItems.melee_upgrade);
        int mana = this.getMana(stack);

        if(level > 0 && mana > 0) this.consumeMana(stack, level * 4, wielder);

        return true;
    }

    @Override
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player){
        return WandHelper.getUpgradeLevel(stack, WizardryItems.melee_upgrade) == 0;
    }

    // A proper hook was introduced for this in Forge build 14.23.5.2805 - Hallelujah, finally!
    // The discussion about this was quite interesting, see the following:
    // https://github.com/TeamTwilight/twilightforest/blob/1.12.x/src/main/java/twilightforest/item/ItemTFScepterLifeDrain.java
    // https://github.com/MinecraftForge/MinecraftForge/pull/4834
    // Among the things mentioned were that it can be 'fixed' by doing the exact same hacks that I did, and that
    // returning a result of PASS rather than SUCCESS from onItemRightClick also solves the problem (not sure why
    // though, and again it's not a perfect solution)
    // Edit: It seems that the hacky fix in previous versions actually introduced a wand duplication bug... oops

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack){
        // Ignore durability changes
        if(ItemStack.areItemsEqualIgnoreDurability(oldStack, newStack)) return true;
        return super.canContinueUsing(oldStack, newStack);
    }

    @Override
    public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack){
        // Ignore durability changes
        if(ItemStack.areItemsEqualIgnoreDurability(oldStack, newStack)) return false;
        return super.shouldCauseBlockBreakReset(oldStack, newStack);
    }

    @Override
    // Only called client-side
    // This method is always called on the item in oldStack, meaning that oldStack.getItem() == this
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged){

        // This method does some VERY strange things! Despite its name, it also seems to affect the updating of NBT...

        if(!oldStack.isEmpty() || !newStack.isEmpty()){
            // We only care about the situation where we specifically want the animation NOT to play.
            if(oldStack.getItem() == newStack.getItem() && !slotChanged && oldStack.getItem() instanceof ItemWand
                    && newStack.getItem() instanceof ItemWand
                    && WandHelper.getCurrentSpell(oldStack) == WandHelper.getCurrentSpell(newStack))
                return false;
        }

        return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack){
        return WandHelper.getCurrentSpell(itemstack).action;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack){
        return 72000;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> text, net.minecraft.client.util.ITooltipFlag advanced){

        EntityPlayer player = net.minecraft.client.Minecraft.getMinecraft().player;
        if (player == null) { return; }
        // +0.5f is necessary due to the error in the way floats are calculated.
//        if(element != null) text.add("\u00A78" + net.minecraft.client.resources.I18n.format("item." + Wizardry.MODID + ":wand.buff",
//                (int)((tier.level + 1) * Constants.POTENCY_INCREASE_PER_TIER * 100 + 0.5f) + "%",
//                element.getDisplayName()));

        Spell spell = WandHelper.getCurrentSpell(stack);

        boolean discovered = true;
        if(Wizardry.settings.discoveryMode && !player.isCreative() && WizardData.get(player) != null
                && !WizardData.get(player).hasSpellBeenDiscovered(spell)){
            discovered = false;
        }

        text.add("\u00A77" + net.minecraft.client.resources.I18n.format("item." + Wizardry.MODID + ":wand.spell",
                discovered ? "\u00A77" + spell.getDisplayNameWithFormatting()
                        : "#\u00A79" + SpellGlyphData.getGlyphName(spell, player.world)));

        if(advanced.isAdvanced()){
            // Advanced tooltips for debugging
            text.add("\u00A79" + net.minecraft.client.resources.I18n.format("item." + Wizardry.MODID + ":wand.mana",
                    this.getMana(stack), this.getManaCapacity(stack)));

//		}else{
//
//			ChargeStatus status = ChargeStatus.getChargeStatus(stack);
//			text.add(status.getFormattingCode() + status.getDisplayName());
        }
    }

    // Continuous spells use the onUsingItemTick method instead of this one.
    /* An important thing to note about this method: it is only called on the server and the client of the player
     * holding the item (I call this client-inconsistency). This means if you spawn particles here they will not show up
     * on other players' screens. Instead, this must be done via packets. */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand){

        ItemStack stack = player.getHeldItem(hand);

        // Alternate right-click function; overrides spell casting.
        if(this.selectMinionTarget(player, world)) return new ActionResult<>(EnumActionResult.SUCCESS, stack);

        Spell spell = WandHelper.getCurrentSpell(stack);
        SpellModifiers modifiers = this.calculateModifiers(stack, player, spell);

        if(canCast(stack, spell, player, hand, 0, modifiers)){
            // Now we can cast continuous spells with scrolls!
            if(spell.isContinuous){
                if(!player.isHandActive()){
                    player.setActiveHand(hand);
                    // Store the modifiers for use each tick
                    if(WizardData.get(player) != null) WizardData.get(player).itemCastingModifiers = modifiers;
                    // Return the player's held item so spells can change it if they wish (e.g. possession)
                    return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
                }
            }else{
                if(cast(stack, spell, player, hand, 0, modifiers)){
                    return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
                }
            }
        }

        return new ActionResult<>(EnumActionResult.FAIL, stack);
    }

    // For continuous spells. The count argument actually decrements by 1 each tick.
    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase user, int count){

        if(user instanceof EntityPlayer){

            EntityPlayer player = (EntityPlayer)user;

            Spell spell = WandHelper.getCurrentSpell(stack);

            SpellModifiers modifiers;

            if(WizardData.get(player) != null){
                modifiers = WizardData.get(player).itemCastingModifiers;
            }else{
                modifiers = this.calculateModifiers(stack, (EntityPlayer)user, spell); // Fallback to the old way, should never be used
            }

            int castingTick = stack.getMaxItemUseDuration() - count;

            // Continuous spells (these must check if they can be cast each tick since the mana changes)
            // Don't call canCast when castingTick == 0 because we already did it in onItemRightClick
            if(spell.isContinuous && (castingTick == 0 || canCast(stack, spell, player, player.getActiveHand(), castingTick, modifiers))){
                cast(stack, spell, player, player.getActiveHand(), castingTick, modifiers);
            }else{
                // Stops the casting if it was interrupted, either by events or because the wand ran out of mana
                player.stopActiveHand();
            }
        }
    }

    @Override
    public boolean canCast(ItemStack stack, Spell spell, EntityPlayer caster, EnumHand hand, int castingTick, SpellModifiers modifiers){

        // Spells can only be cast if the casting events aren't cancelled...
        if(castingTick == 0){
            if(MinecraftForge.EVENT_BUS.post(new SpellCastEvent.Pre(SpellCastEvent.Source.WAND, spell, caster, modifiers))) return false;
        }else{
            if(MinecraftForge.EVENT_BUS.post(new SpellCastEvent.Tick(SpellCastEvent.Source.WAND, spell, caster, modifiers, castingTick))) return false;
        }

        int cost = (int)(spell.getCost() * modifiers.get(SpellModifiers.COST) + 0.1f); // Weird floaty rounding

        // As of wizardry 4.2 mana cost is only divided over two intervals each second
        if(spell.isContinuous) cost = getDistributedCost(cost, castingTick);

        // ...and the wand has enough mana to cast the spell...
        return cost <= this.getMana(stack) // This comes first because it changes over time
                // ...and the wand is the same tier as the spell or higher...
                && spell.getTier().level <= getWizardryTier(stack).level
                // ...and either the spell is not in cooldown or the player is in creative mode
                && (WandHelper.getCurrentCooldown(stack) == 0 || caster.isCreative());
    }

    @Override
    public boolean cast(ItemStack stack, Spell spell, EntityPlayer caster, EnumHand hand, int castingTick, SpellModifiers modifiers){

        World world = caster.world;

        if(world.isRemote && !spell.isContinuous && spell.requiresPacket()) return false;

        if(spell.cast(world, caster, hand, castingTick, modifiers)){

            if(castingTick == 0) MinecraftForge.EVENT_BUS.post(new SpellCastEvent.Post(SpellCastEvent.Source.WAND, spell, caster, modifiers));

            if(!world.isRemote){

                // Continuous spells never require packets so don't rely on the requiresPacket method to specify it
                if(!spell.isContinuous && spell.requiresPacket()){
                    // Sends a packet to all players in dimension to tell them to spawn particles.
                    IMessage msg = new PacketCastSpell.Message(caster.getEntityId(), hand, spell, modifiers);
                    WizardryPacketHandler.net.sendToDimension(msg, world.provider.getDimension());
                }

                caster.setActiveHand(hand);

                // Mana cost
                int cost = (int)(spell.getCost() * modifiers.get(SpellModifiers.COST) + 0.1f); // Weird floaty rounding
                // As of wizardry 4.2 mana cost is only divided over two intervals each second
                if(spell.isContinuous) cost = getDistributedCost(cost, castingTick);

                if(cost > 0) this.consumeMana(stack, cost, caster);

            }

            // Cooldown
            if(!spell.isContinuous && !caster.isCreative()){ // Spells only have a cooldown in survival
                WandHelper.setCurrentCooldown(stack, (int)(spell.getCooldown() * modifiers.get(WizardryItems.cooldown_upgrade)));
            }

            return true;
        }

        return false;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase user, int timeLeft){

        if(user instanceof EntityPlayer){

            EntityPlayer player = (EntityPlayer)user;

            Spell spell = WandHelper.getCurrentSpell(stack);

            SpellModifiers modifiers;

            if(WizardData.get(player) != null){
                modifiers = WizardData.get(player).itemCastingModifiers;
            }else{
                modifiers = this.calculateModifiers(stack, (EntityPlayer)user, spell); // Fallback to the old way, should never be used
            }

            int castingTick = stack.getMaxItemUseDuration() - timeLeft; // Might as well include this

            int cost = getDistributedCost((int)(spell.getCost() * modifiers.get(SpellModifiers.COST) + 0.1f), castingTick);

            // Still need to check there's enough mana or the spell will finish twice, since running out of mana is
            // handled separately.
            if(spell.isContinuous && spell.getTier().level <= getWizardryTier(stack).level && cost <= this.getMana(stack)){

                MinecraftForge.EVENT_BUS.post(new SpellCastEvent.Finish(SpellCastEvent.Source.WAND, spell, player, modifiers, castingTick));
                spell.finishCasting(world, player, Double.NaN, Double.NaN, Double.NaN, null, castingTick, modifiers);

                if(!player.isCreative()){ // Spells only have a cooldown in survival
                    WandHelper.setCurrentCooldown(stack, (int)(spell.getCooldown() * modifiers.get(WizardryItems.cooldown_upgrade)));
                }
            }
        }
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity, EnumHand hand){

        if(player.isSneaking() && entity instanceof EntityPlayer && WizardData.get(player) != null){
            String string = WizardData.get(player).toggleAlly((EntityPlayer)entity) ? "item." + Wizardry.MODID + ":wand.addally"
                    : "item." + Wizardry.MODID + ":wand.removeally";
            if(!player.world.isRemote) player.sendMessage(new TextComponentTranslation(string, entity.getName()));
            return true;
        }

        return false;
    }

    /** Distributes the given cost (which should be the per-second cost of a continuous spell) over a second and
     * returns the appropriate cost to be applied for the given tick. Currently the cost is distributed over 2
     * intervals per second, meaning the returned value is 0 unless {@code castingTick} is a multiple of 10.*/
    protected static int getDistributedCost(int cost, int castingTick){

        int partialCost;

        if(castingTick % 20 == 0){ // Whole number of seconds has elapsed
            partialCost = cost / 2 + cost % 2; // Make sure cost adds up to the correct value by adding the remainder here
        }else if(castingTick % 10 == 0){ // Something-and-a-half seconds has elapsed
            partialCost = cost/2;
        }else{ // Some other number of ticks has elapsed
            partialCost = 0; // Wands aren't damaged within half-seconds
        }

        return partialCost;
    }

    /** Returns a SpellModifiers object with the appropriate modifiers applied for the given ItemStack and Spell. */
    // This is now public because artefacts use it
    public SpellModifiers calculateModifiers(ItemStack stack, EntityPlayer player, Spell spell){

        SpellModifiers modifiers = new SpellModifiers();

        // Now we only need to add multipliers if they are not 1.
        int level = WandHelper.getUpgradeLevel(stack, WizardryItems.range_upgrade);
        if(level > 0)
            modifiers.set(WizardryItems.range_upgrade, 1.0f + level * Constants.RANGE_INCREASE_PER_LEVEL, true);

        level = WandHelper.getUpgradeLevel(stack, WizardryItems.duration_upgrade);
        if(level > 0)
            modifiers.set(WizardryItems.duration_upgrade, 1.0f + level * Constants.DURATION_INCREASE_PER_LEVEL, false);

        level = WandHelper.getUpgradeLevel(stack, WizardryItems.blast_upgrade);
        if(level > 0)
            modifiers.set(WizardryItems.blast_upgrade, 1.0f + level * Constants.BLAST_RADIUS_INCREASE_PER_LEVEL, true);

        level = WandHelper.getUpgradeLevel(stack, WizardryItems.cooldown_upgrade);
        if(level > 0)
            modifiers.set(WizardryItems.cooldown_upgrade, 1.0f - level * Constants.COOLDOWN_REDUCTION_PER_LEVEL, true);

        modifiers.set(SpellModifiers.POTENCY, 1.0f + getPotencyIncrease(stack, spell), true);

        return modifiers;
    }

    private boolean selectMinionTarget(EntityPlayer player, World world){

        RayTraceResult rayTrace = RayTracer.standardEntityRayTrace(world, player, 16, false);

        if(rayTrace != null && WizardryUtilities.isLiving(rayTrace.entityHit)){

            EntityLivingBase entity = (EntityLivingBase)rayTrace.entityHit;

            // Sets the selected minion's target to the right-clicked entity
            if(player.isSneaking() && WizardData.get(player) != null && WizardData.get(player).selectedMinion != null){

                ISummonedCreature minion = WizardData.get(player).selectedMinion.get();

                if(minion instanceof EntityLiving && minion != entity){
                    // There is now only the new AI! (which greatly improves things)
                    ((EntityLiving)minion).setAttackTarget(entity);
                    // Deselects the selected minion
                    WizardData.get(player).selectedMinion = null;
                    return true;
                }
            }
        }

        return false;
    }

    // Workbench stuff

    @Override
    public int getSpellSlotCount(ItemStack stack){
        return BASE_SPELL_SLOTS + WandHelper.getUpgradeLevel(stack, WizardryItems.attunement_upgrade);
    }

    @Override
    public boolean onApplyButtonPressed(EntityPlayer player, Slot centre, Slot crystals, Slot upgrade, Slot[] spellBooks){

        boolean changed = false;

        // Upgrades wand if necessary. Damage is copied, preserving remaining durability,
        // and also the entire NBT tag compound.
        if(WandHelper.isWandUpgrade(upgrade.getStack().getItem())){

            // Special upgrades
            Item specialUpgrade = upgrade.getStack().getItem();

            if(canBoUpgraded(centre.getStack(), specialUpgrade)){

                // Used to preserve existing mana when upgrading storage rather than creating free mana.
                int prevMana = this.getMana(centre.getStack());

                WandHelper.applyUpgrade(centre.getStack(), specialUpgrade);

                // Special behaviours for specific upgrades
                if(specialUpgrade == WizardryItems.storage_upgrade){

                    this.setMana(centre.getStack(), prevMana);

                }else if(specialUpgrade == WizardryItems.attunement_upgrade){

                    int newSlotCount = BASE_SPELL_SLOTS + WandHelper.getUpgradeLevel(centre.getStack(),
                            WizardryItems.attunement_upgrade);

                    Spell[] spells = WandHelper.getSpells(centre.getStack());
                    Spell[] newSpells = new Spell[newSlotCount];

                    for(int i = 0; i < newSpells.length; i++){
                        newSpells[i] = i < spells.length && spells[i] != null ? spells[i] : Spells.none;
                    }

                    WandHelper.setSpells(centre.getStack(), newSpells);

                    int[] cooldowns = WandHelper.getCooldowns(centre.getStack());
                    int[] newCooldowns = new int[newSlotCount];

                    if(cooldowns.length > 0){
                        System.arraycopy(cooldowns, 0, newCooldowns, 0, cooldowns.length);
                    }

                    WandHelper.setCooldowns(centre.getStack(), newCooldowns);
                }

                upgrade.decrStackSize(1);
                WizardryAdvancementTriggers.special_upgrade.triggerFor(player);

                if(WandHelper.getTotalUpgrades(centre.getStack()) == getUpgradeLimit(centre.getStack())){
                    WizardryAdvancementTriggers.max_out_wand.triggerFor(player);
                }

                changed = true;
            }
        }

        // Reads NBT spell metadata array to variable, edits this, then writes it back to NBT.
        // Original spells are preserved; if a slot is left empty the existing spell binding will remain.
        // Accounts for spells which cannot be applied because they are above the wand's tier; these spells
        // will not bind but the existing spell in that slot will remain and other applicable spells will
        // be bound as normal, along with any upgrades and crystals.
        Spell[] spells = WandHelper.getSpells(centre.getStack());

        if(spells.length <= 0){
            // Base value here because if the spell array doesn't exist, the wand can't possibly have attunement upgrades
            spells = new Spell[BASE_SPELL_SLOTS];
        }

        for(int i = 0; i < spells.length; i++){
            if(spellBooks[i].getStack() != ItemStack.EMPTY){

                Spell spell = Spell.byMetadata(spellBooks[i].getStack().getItemDamage());
                // If the wand is powerful enough for the spell, it's not already bound to that slot and it's enabled for wands
                if(!(spell.getTier().level > getWizardryTier(centre.getStack()).level) && spells[i] != spell && spell.isEnabled(SpellProperties.Context.WANDS)){
                    spells[i] = spell;
                    changed = true;
                }
            }
        }

        WandHelper.setSpells(centre.getStack(), spells);

        // Charges wand by appropriate amount
        if(crystals.getStack() != ItemStack.EMPTY && !this.isManaFull(centre.getStack())){

            int chargeDepleted = this.getManaCapacity(centre.getStack()) - this.getMana(centre.getStack());

            int manaPerItem = Constants.MANA_PER_CRYSTAL;
            if(crystals.getStack().getItem() == WizardryItems.crystal_shard) manaPerItem = Constants.MANA_PER_SHARD;
            if(crystals.getStack().getItem() == WizardryItems.grand_crystal) manaPerItem = Constants.GRAND_CRYSTAL_MANA;

            if(crystals.getStack().getCount() * manaPerItem < chargeDepleted){
                // If there aren't enough crystals to fully charge the wand
                this.rechargeMana(centre.getStack(), crystals.getStack().getCount() * manaPerItem);
                crystals.decrStackSize(crystals.getStack().getCount());

            }else{
                // If there are excess crystals (or just enough)
                this.setMana(centre.getStack(), this.getManaCapacity(centre.getStack()));
                crystals.decrStackSize((int)Math.ceil(((double)chargeDepleted) / manaPerItem));
            }

            changed = true;
        }

        return changed;
    }

    // hitEntity is only called server-side, so we'll have to use events
    @SubscribeEvent
    public static void onAttackEntityEvent(AttackEntityEvent event){

        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = player.getHeldItemMainhand(); // Can't melee with offhand items

        if(stack.getItem() instanceof IManaStoringItem){

            // Nobody said it had to be a wand, as long as it's got a melee upgrade it counts
            int level = WandHelper.getUpgradeLevel(stack, WizardryItems.melee_upgrade);
            int mana = ((IManaStoringItem)stack.getItem()).getMana(stack);

            if(level > 0 && mana > 0){

                Random random = player.world.rand;

                player.world.playSound(player.posX, player.posY, player.posZ, WizardrySounds.ITEM_WAND_MELEE, SoundCategory.PLAYERS, 0.75f, 1, false);

                if(player.world.isRemote){

                    Vec3d origin = new Vec3d(player.posX, player.getEntityBoundingBox().minY + player.getEyeHeight(), player.posZ);
                    Vec3d hit = origin.add(player.getLookVec().scale(player.getDistance(event.getTarget())));
                    // Generate two perpendicular vectors in the plane perpendicular to the look vec
                    Vec3d vec1 = player.getLookVec().rotatePitch(90);
                    Vec3d vec2 = player.getLookVec().crossProduct(vec1);

                    for(int i = 0; i < 15; i++){
                        ParticleBuilder.create(ParticleBuilder.Type.SPARKLE).pos(hit)
                                .vel(vec1.scale(random.nextFloat() * 0.3f - 0.15f).add(vec2.scale(random.nextFloat() * 0.3f - 0.15f)))
                                .clr(1f, 1f, 1f).fade(0.3f, 0.5f, 1)
                                .time(8 + random.nextInt(4)).spawn(player.world);
                    }
                }
            }
        }
    }

}
