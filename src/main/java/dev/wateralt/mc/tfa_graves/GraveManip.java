package dev.wateralt.mc.tfa_graves;

import dev.wateralt.mc.tfa_graves.mixin.PlayerEntityInvoker;
import net.minecraft.client.util.ChatMessages;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GraveManip {
  public static final String GRAVE_ENTITY_TAG = "dev.wateralt.mc.tfa_graves.IsGrave";
  
  public static ChestMinecartEntity createGravePart(ServerWorld world, Vec3d pos, Text name, List<ItemStack> items) {
    ChestMinecartEntity entity = new ChestMinecartEntity(world, pos.getX(), pos.getY(), pos.getZ());
    entity.setCustomName(name);
    entity.setCustomNameVisible(true);
    entity.setInvulnerable(true);
    entity.setNoGravity(true);
    entity.setSilent(true);
    entity.addCommandTag(GRAVE_ENTITY_TAG);
    entity.resetInventory();
    List<ItemStack> inv = entity.getInventory();
    for(int i = 0; i < items.size(); i++) {
      inv.set(i, items.get(i));
    }
    world.spawnEntity(entity);
    return entity;
  }
  
  public static boolean spawnGraveForPlayer(ServerWorld world, PlayerEntity player) {
    PlayerInventory inv = player.getInventory();
    ((PlayerEntityInvoker) player).invokeVanishCursedItems();
    ArrayList<ItemStack> items1 = new ArrayList<>(27);
    ArrayList<ItemStack> items2 = new ArrayList<>(9 + 4 + 1);
    for(int i = 0; i < 27; i++) {
      items1.add(inv.removeStack(i + 9));
    }
    for(int i = 0; i < 9; i++) {
      items2.add(inv.removeStack(i));
    }
    for(int i = 0; i < 4; i++) {
      items2.add(inv.removeStack(36 + i));
    }
    items2.add(inv.removeStack(36 + 4));
    
    Text graveName = player.getDisplayName().copy();
    graveName.getSiblings().add(Text.literal("'s Grave"));
    
    double y = player.getPos().getY();
    if(y < player.getWorld().getBottomY() + 1) {
      y = player.getWorld().getBottomY() + 1;
    }
    Vec3d basePos = player.getPos().withAxis(Direction.Axis.Y, y);
    
    boolean ret = false;
    if(!items1.stream().allMatch(ItemStack::isEmpty)) {
      createGravePart(world, basePos, graveName, items1);
      ret = true;
    }
    if(!items2.stream().allMatch(ItemStack::isEmpty)) {
      createGravePart(world, basePos.add(0, 0.4, 0), graveName, items2);
      ret = true;
    }
    return ret;
  }
  
  public static void removeGraveIfEmpty(ChestMinecartEntity entity) {
    if(entity.getCommandTags().contains(GRAVE_ENTITY_TAG) && entity.getInventory().stream().allMatch(ItemStack::isEmpty)) {
      entity.remove(Entity.RemovalReason.DISCARDED);
    }
  }
  
  public static void logGraveCreation(PlayerEntity player) {
    String playerName = player.getName().toString();
    String dimensionName = player.getWorld().getRegistryKey().getValue().toString();
    String positionStr = String.format(Locale.ROOT, "(%.1f, %.1f, %.1f)", player.getX(), player.getY(), player.getZ());
      
    String logMsg = String.format("Grave for player %s spawned at %s dimension %s", playerName, positionStr, dimensionName);
    GravesMod.getInstance().getLogger().info(logMsg);
    
    Text chatText = Text.empty()
      .styled(style -> style.withColor(0xff8800));
    List<Text> siblings = chatText.getSiblings();
    siblings.add(Text.literal("Your grave is at "));
    siblings.add(Text.literal(positionStr).styled(style -> style.withColor(Formatting.YELLOW)));
    siblings.add(Text.literal(" in "));
    siblings.add(Text.literal(dimensionName).styled(style -> style.withColor(Formatting.YELLOW)));
    
    if(player instanceof ServerPlayerEntity player2) {
      player2.sendMessageToClient(chatText, false);
    }
  }
}
