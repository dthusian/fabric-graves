package dev.wateralt.mc.tfa_graves.mixinutil;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.explosion.Explosion;

public interface EntityOverride {
  default void setVelocity(Vec3d v) { }
  default boolean isImmuneToExplosion(Explosion explosion) { return false; }
}
