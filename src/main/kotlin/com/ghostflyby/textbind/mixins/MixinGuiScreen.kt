package com.ghostflyby.textbind.mixins

import com.ghostflyby.textbind.keyboard.Modifier
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiScreen
import org.lwjgl.input.Keyboard
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(GuiScreen::class)
abstract class MixinGuiScreen(@Shadow private var mc: Minecraft) : Gui() {

  @Shadow abstract fun keyTyped(typedChar: Char, keyCode: Int)

  @Inject(method = ["handleKeyboardInput()V"], at = [At("HEAD")], cancellable = true)
  fun handleKeyboardInput(callbackInfo: CallbackInfo) {
    val typedChar = Keyboard.getEventCharacter()
    val keyCode = Keyboard.getEventKey()
    if (Modifier.isControlDown() && Modifier.isMenuDown() || Modifier.isMetaDown()) {
      keyTyped(typedChar, keyCode)
      mc.func_152348_aa()
      callbackInfo.cancel()
    }
  }
}