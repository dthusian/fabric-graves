package dev.wateralt.mc.tfa_graves.mixin;

import dev.wateralt.mc.tfa_graves.GraveManip;
import dev.wateralt.mc.tfa_graves.GravesMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
  @Inject(method = "dropInventory", at = @At("HEAD"))
  private void dropInventory(CallbackInfo ci) {
    PlayerEntity that = (PlayerEntity) (Object) this;
    World world = that.getWorld();
    if(!world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
      if(world instanceof ServerWorld serverWorld) {
        boolean spawned = GraveManip.spawnGraveForPlayer(serverWorld, that);
        if(spawned) GraveManip.logGraveCreation(that);
      }
    }
  }
}
