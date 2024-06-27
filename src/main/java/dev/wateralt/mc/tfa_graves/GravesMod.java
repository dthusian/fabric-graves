package dev.wateralt.mc.tfa_graves;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GravesMod implements ModInitializer {
  private static GravesMod INSTANCE = null;
  private Logger logger;
  
  @Override
  public void onInitialize() {
    if(INSTANCE != null) throw new IllegalStateException();
    INSTANCE = this;
    logger = LoggerFactory.getLogger("GravesMod");
  }
  
  public static GravesMod getInstance() {
    if(INSTANCE == null) throw new IllegalStateException();
    return INSTANCE;
  }
  
  public Logger getLogger() {
    return logger;
  }
}
