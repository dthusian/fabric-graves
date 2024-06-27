package dev.wateralt.mc.tfa_graves.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor {
  @Accessor("velocity")
  void accessorSetVelocity(Vec3d v);
}
