package com.ghostflyby.textbind.config

import com.ghostflyby.textbind.Tags
import com.ghostflyby.textbind.TextBind
import java.io.File
import net.minecraft.client.Minecraft

object Config {
  private val configDirPath =
      Minecraft.getMinecraft().mcDataDir.resolve("client").resolve(Tags.MODID).path

  /** typically .minecraft/client/textbind/ */
  private val configDir: File
    get() {
      val dir = File(configDirPath)
      if (!dir.exists())
          if (dir.mkdirs()) TextBind.logger.info("Created config directory: $configDirPath")
          else TextBind.logger.error("Failed to create config directory: $configDirPath")
      return dir
    }

  /**
   * @param fileName short file name with extension
   * @return Pair of file already exists and the file
   */
  operator fun get(fileName: String): Pair<Boolean, File> {
    val file = configDir.resolve(fileName)
    val exist = file.exists()
    if (!exist)
        if (file.createNewFile())
            TextBind.logger.info("Created config file: $fileName in $configDirPath")
        else TextBind.logger.error("Failed to create config file: $fileName in $configDirPath")
    return exist to file
  }
}
