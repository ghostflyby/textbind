package com.ghostflyby.textbind.mixins

import com.ghostflyby.textbind.TextBind
import com.ghostflyby.textbind.actions.GuiChatActions
import com.ghostflyby.textbind.keyboard.Key
import com.ghostflyby.textbind.keyboard.KeyCombination
import net.minecraft.client.gui.GuiChat
import org.lwjgl.input.Keyboard
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(GuiChat::class)
@Suppress("NonJavaMixin")
abstract class MixinGuiChat {

  @Shadow @Suppress("PrivatePropertyName") private var field_146414_r: Boolean = false
  @Shadow @Suppress("PrivatePropertyName") private var field_146417_i: Boolean = false

  @Inject(method = ["keyTyped"], at = [At("HEAD")], cancellable = true)
  private fun keyTyped(typedChar: Char, keyCode: Int, callbackInfo: CallbackInfo) {
    @Suppress("CAST_NEVER_SUCCEEDS") val guiChat = this as GuiChat
    TextBind.logger.debug("[GuiChat] Char: ${typedChar}, KeyCode: ${Keyboard.getKeyName(keyCode)}")
    field_146414_r = false

    if (keyCode != Keyboard.KEY_TAB) field_146417_i = false

    val keyCombination = KeyCombination.current().withKey(Key[keyCode] ?: return)
    TextBind.logger.debug("[GuiChat] KeyCombination: {}", keyCombination)
    if (GuiChatActions[keyCombination]?.invoke(guiChat) == true) callbackInfo.cancel()
  }
}
