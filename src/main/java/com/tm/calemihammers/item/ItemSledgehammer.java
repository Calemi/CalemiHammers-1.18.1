package com.tm.calemihammers.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.tm.calemicore.util.BlockScanner;
import com.tm.calemicore.util.Location;
import com.tm.calemicore.util.helper.LogHelper;
import com.tm.calemicore.util.helper.LoreHelper;
import com.tm.calemicore.util.helper.RayTraceHelper;
import com.tm.calemicore.util.helper.WorldEditHelper;
import com.tm.calemicore.util.helper.shape.ShapeFlatCube;
import com.tm.calemihammers.config.CHConfig;
import com.tm.calemihammers.init.InitEnchantments;
import com.tm.calemihammers.init.InitItems;
import com.tm.calemihammers.main.CHReference;
import com.tm.calemihammers.main.CalemiHammers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemSledgehammer extends DiggerItem {

    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public int baseChargeTime;
    public int chargeTime;

    public ItemSledgehammer(SledgehammerTiers tier) {
        super(0, 0, tier, BlockTags.MINEABLE_WITH_PICKAXE, new Item.Properties().tab(CalemiHammers.TAB).stacksTo(1));

        this.chargeTime = baseChargeTime;
        this.baseChargeTime = tier.baseChargeTime;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", tier.getAttackDamageBonus() - 1, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", tier.attackSpeed - 4, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipList, TooltipFlag advanced) {
        LoreHelper.addInformationLoreFirst(tooltipList, new TranslatableComponent("ch.lore.sledgehammer.1"));
        LoreHelper.addInformationLore(tooltipList, new TranslatableComponent("ch.lore.sledgehammer.2"));
        LoreHelper.addControlsLoreFirst(tooltipList,  new TranslatableComponent("ch.lore.sledgehammer.use"), LoreHelper.ControlType.USE);
        LoreHelper.addControlsLore(tooltipList,  new TranslatableComponent("ch.lore.sledgehammer.sneak-use"), LoreHelper.ControlType.SNEAK_USE);
        LoreHelper.addControlsLore(tooltipList,  new TranslatableComponent("ch.lore.sledgehammer.release-use"), LoreHelper.ControlType.RELEASE_USE);

        if (stack.isEnchanted()) {
            LoreHelper.addBlankLine(tooltipList);
        }
    }

    /**
     * Handles charging.
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        //If the off hand has an item and the Player is not crouching, prevent charging.
        if (hand == InteractionHand.MAIN_HAND && !player.getOffhandItem().isEmpty() && !player.isCrouching()) {
            return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
        }

        chargeTime = Math.max(1, baseChargeTime - EnchantmentHelper.getBlockEfficiency(player) * 3);
        player.startUsingItem(hand);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    /**
     * Handles vein minding and excavation.
     */
    @Override
    public void releaseUsing(ItemStack heldStack, Level level, LivingEntity entity, int timeLeft) {

        Player player = (Player) entity;

        InteractionHand hand = InteractionHand.OFF_HAND;

        //If the Sledgehammer is in the main hand, set the current hand to main.
        if (ItemStack.isSame(player.getMainHandItem(), heldStack)) {
            hand = InteractionHand.MAIN_HAND;
        }

        //Checks if fully charged.
        if (getUseDuration(heldStack) - timeLeft >= chargeTime) {

            player.swing(hand);

            RayTraceHelper.BlockTrace blockTrace = RayTraceHelper.rayTraceBlock(level, player, 5);

            //Checks if the ray trace hit a Block.
            if (blockTrace != null) {

                Location hit = blockTrace.getHit();

                LogHelper.logCommon(CHReference.MOD_NAME, level, hit);

                //If the Block hit was an ore, vein mine.
                if (CHTagItemLists.isLog(hit.getBlock())) {
                    veinMine(heldStack, player, hit);
                    return;
                }

                //If the Block hit was a log, vein mine.
                if (CHTagItemLists.isOre(hit.getBlock())) {
                    veinMine(heldStack, player, hit);
                    return;
                }

                //Else, excavate.
                excavateBlocks(level, heldStack, player, hit, blockTrace.getHitSide());
            }
        }
    }

    /**
     * Handles vein mining. (for trees & ores)
     */
    private void veinMine (ItemStack heldStack, Player player, Location startLocation) {

        //Checks if the starting Location can be mined.
        if (canBreakBlock(player, startLocation)) {

            //Start a scan of blocks that equal the starting Location's Block.
            BlockScanner scan = new BlockScanner(startLocation, startLocation.getBlock().defaultBlockState(), CHConfig.server.maxVeinMineSize.get(), true);
            scan.startRadiusScan();

            int damage = getDamage(heldStack);

            //Iterate through the scanned Locations.
            for (Location nextLocation : scan.buffer) {

                int maxDamage = getMaxDamage(heldStack);

                //If the Sledgehammer is broken, stop the iteration.
                if (damage > maxDamage && maxDamage > 0) {
                    return;
                }

                nextLocation.breakBlock(player);
                damage++;
            }
        }
    }

    /**
     * Handles the 3x3 mining of Blocks.
     * Size can increase based on Crushing enchant.
     */
    private void excavateBlocks (Level worldIn, ItemStack heldStack, Player player, Location location, Direction face) {

        int radius = EnchantmentHelper.getEnchantmentLevel(InitEnchantments.CRUSHING.get(), player) + 1;

        ArrayList<Location> locations = WorldEditHelper.selectShape(new ShapeFlatCube(location, face, radius));

        int damage = getDamage(heldStack);

        //Iterate through the Locations from the World Edit shape.
        for (Location nextLocation : locations) {

            int maxDamage = getMaxDamage(heldStack);

            //If the Sledgehammer is broken, stop the iteration.
            if (damage > maxDamage && maxDamage > 0) {
                return;
            }

            //Checks if the next Location can be mined.
            if (canBreakBlock(player, nextLocation)) {
                nextLocation.breakBlock(player);
                damage++;
            }
        }
    }

    /**
     * Checks if the Block at the given Location can be mined by the Sledgehammer.
     */
    private boolean canBreakBlock (Player player, Location location) {
        float hardness = location.getBlockState().getDestroySpeed(location.level, location.getBlockPos());
        return hardness >= 0 && hardness <= 50 && ForgeHooks.isCorrectToolForDrops(location.getBlockState(), player);
    }

    /**
     * Handles damaging when the Sledgehammer breaks a Block.
     */
    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity livingEntity) {

        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
            damageHammer(stack, livingEntity);
        }

        return true;
    }

    /**
     * Handles damaging when the Sledgehammer hits an Entity.
     */
    public boolean hurtEnemy(ItemStack stack, LivingEntity player, LivingEntity target) {
        damageHammer(stack, player);
        return true;
    }

    /**
     * Used to damage the Sledgehammer if its not a Starlight one.
     */
    private void damageHammer(ItemStack stack, LivingEntity livingEntity) {
        if (stack.getItem() != InitItems.SLEDGEHAMMER_STARLIGHT.get()) stack.hurtAndBreak(1, livingEntity, (i) -> i.broadcastBreakEvent(EquipmentSlot.MAINHAND));
    }

    /**
     * Handles setting attack damage & speed.
     */
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot, stack);
    }

    @Override
    public float getDestroySpeed (ItemStack stack, BlockState blockState) {
        return this.speed;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration (ItemStack stack) {
        return 72000;
    }

    @Override
    public boolean isFireResistant() {
        return this == InitItems.SLEDGEHAMMER_NETHERITE.get() || this == InitItems.SLEDGEHAMMER_STARLIGHT.get();
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return this == InitItems.SLEDGEHAMMER_STARLIGHT.get() || stack.isEnchanted();
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        if (stack.getItem() == InitItems.SLEDGEHAMMER_STARLIGHT.get()) return Rarity.RARE;
        return Rarity.COMMON;
    }
}
