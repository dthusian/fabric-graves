package dev.wateralt.mc.tfa_graves.mixin;

import dev.wateralt.mc.tfa_graves.GraveManip;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
  @Inject(method = "setVelocity(Lnet/minecraft/util/math/Vec3d;)V", at = @At("HEAD"), cancellable = true)
  private void setVelocity(CallbackInfo ci) {
    Object that = this;
    if(that instanceof ChestMinecartEntity chestThat) {
      if(chestThat.getCommandTags().contains(GraveManip.GRAVE_ENTITY_TAG)) {
        ci.cancel();
      }
    }
  }
  
  @Inject(method = "isImmuneToExplosion", at = @At("HEAD"), cancellable = true)
  private void isImmuneToExplosion(Explosion explosion, CallbackInfoReturnable<Boolean> cir) {
    Object that = this;
    if(that instanceof ChestMinecartEntity chestThat) {
      if(chestThat.getCommandTags().contains(GraveManip.GRAVE_ENTITY_TAG)) {
        cir.setReturnValue(true);
      }
    }
  }
}
