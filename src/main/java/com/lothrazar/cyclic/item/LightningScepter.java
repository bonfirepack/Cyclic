package com.lothrazar.cyclic.item;

import com.lothrazar.cyclic.base.ItemBase;
import com.lothrazar.cyclic.util.UtilItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class LightningScepter extends ItemBase {

  public LightningScepter(Properties properties) {
    super(properties);
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn) {
    ItemStack stack = player.getHeldItem(handIn);
    if (player.getCooldownTracker().hasCooldown(this)) {
      return super.onItemRightClick(worldIn, player, handIn);
    }
    LightningEntity ent = new LightningEntity(player, worldIn);
    ent.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
    worldIn.addEntity(ent);
    player.getCooldownTracker().setCooldown(stack.getItem(), 10);
    UtilItemStack.damageItem(player, stack);
    return super.onItemRightClick(worldIn, player, handIn);
  }
}