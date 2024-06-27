package dev.wateralt.mc.tfa_graves.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PlayerEntity.class)
public interface PlayerEntityInvoker {
  @Invoker("vanishCursedItems")
  void invokeVanishCursedItems();
}
