package dev.wateralt.mc.tfa_graves.mixin;

import dev.wateralt.mc.tfa_graves.GraveManip;
import dev.wateralt.mc.tfa_graves.GravesMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestMinecartEntity.class)
public abstract class ChestMinecartMixin extends Entity {
  public ChestMinecartMixin(EntityType<?> type, World world) {
    super(type, world);
  }

  @Inject(method = "onClose", at = @At("RETURN"))
  private void onClose(PlayerEntity player, CallbackInfo ci) {
    ChestMinecartEntity that = (ChestMinecartEntity) (Object) this;
    GraveManip.removeGraveIfEmpty(that);
  }

  @Override
  public void setVelocity(Vec3d v) {
    ChestMinecartEntity that = (ChestMinecartEntity) (Object) this;
    if(!that.getCommandTags().contains(GraveManip.GRAVE_ENTITY_TAG)) {
      ((EntityAccessor) that).accessorSetVelocity(v);
    }
  }

  @Override
  public boolean isImmuneToExplosion(Explosion explosion) {
    ChestMinecartEntity that = (ChestMinecartEntity) (Object) this;
    if(!that.getCommandTags().contains(GraveManip.GRAVE_ENTITY_TAG)) {
      return false;
    } else {
      return true;
    }
  }
}
