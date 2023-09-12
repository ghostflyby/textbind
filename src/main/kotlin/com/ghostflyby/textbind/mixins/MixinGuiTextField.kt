package com.ghostflyby.textbind.mixins

import com.ghostflyby.textbind.TextBind
import com.ghostflyby.textbind.actions.GuiTextFieldActions
import com.ghostflyby.textbind.keyboard.Key
import com.ghostflyby.textbind.keyboard.KeyCombination
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiTextField
import net.minecraft.util.ChatAllowedCharacters
import org.lwjgl.input.Keyboard
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Overwrite
import org.spongepowered.asm.mixin.Shadow

@Mixin(GuiTextField::class)
@SuppressWarnings("UnresolvedMixinReference")
abstract class MixinGuiTextField(@Shadow private var isFocused: Boolean) : Gui() {

  @Overwrite
  /**
   * @author ghostflyby
   * @reason Changing and rebinding the shortcuts in GuiTextField TODO: Add support for configurable
   * shortcuts
   */
  fun textboxKeyTyped(typedChar: Char, keyCode: Int): Boolean {
    @Suppress("CAST_NEVER_SUCCEEDS") val textField = this as GuiTextField
    TextBind.logger.debug(
        "[TextField] Char: ${typedChar}, KeyCode: ${Keyboard.getKeyName(keyCode)}")

    if (!isFocused) {
      return false
    }

    val keyCombination = KeyCombination.current().withKey(Key[keyCode] ?: return false)
    TextBind.logger.debug("[TextField] KeyCombination: {}", keyCombination)

    if (GuiTextFieldActions[keyCombination]?.invoke(textField) == true) return true

    return if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
      textField.writeText(typedChar.toString())
      true
    } else {
      false
    }
  }
}
