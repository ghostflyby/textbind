package com.ghostflyby.textbind

import com.ghostflyby.textbind.actions.ActionCompanion
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(
    modid = Tags.MODID,
    version = Tags.VERSION,
    name = Tags.MODNAME,
    acceptedMinecraftVersions = "[1.7.10]")
class TextBind {
  companion object {
    val logger: Logger = LogManager.getLogger(Tags.MODID)
  }

  @Suppress("unused")
  @Mod.EventHandler
  @SideOnly(Side.CLIENT)
  fun preInit(event: FMLPreInitializationEvent) {
    logger.info("PreInit started")
    ActionCompanion::class.sealedSubclasses.map { it.objectInstance }.forEach { it?.loadConfig() }
  }
}
