package dev.wateralt.mc.tfa_graves.mixin;

import dev.wateralt.mc.tfa_graves.GraveManip;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestMinecartEntity.class)
public abstract class ChestMinecartMixin {
  @Inject(method = "onClose", at = @At("RETURN"))
  private void onClose(PlayerEntity player, CallbackInfo ci) {
    ChestMinecartEntity that = (ChestMinecartEntity) (Object) this;
    GraveManip.removeGraveIfEmpty(that);
  }
}
