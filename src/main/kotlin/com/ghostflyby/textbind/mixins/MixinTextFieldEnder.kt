package com.ghostflyby.textbind.mixins

import com.enderio.core.client.gui.widget.TextFieldEnder
import com.ghostflyby.textbind.TextBind
import com.ghostflyby.textbind.actions.GuiTextFieldActions
import com.ghostflyby.textbind.keyboard.Key
import com.ghostflyby.textbind.keyboard.KeyCombination
import net.minecraft.client.gui.GuiTextField
import org.lwjgl.input.Keyboard
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Overwrite
import org.spongepowered.asm.mixin.Shadow

@Mixin(TextFieldEnder::class, remap = false)
@Suppress("NonJavaMixin")
abstract class MixinTextFieldEnder {

  @Shadow private val filter: TextFieldEnder.ICharFilter? = null

  /**
   * @author ghostflyby
   * @reason Changing and rebinding the shortcuts in TextFieldEnder
   */
  @Overwrite
  fun textboxKeyTyped(typedChar: Char, keyCode: Int): Boolean {

    @Suppress("CAST_NEVER_SUCCEEDS") val textField = this as GuiTextField
    @Suppress("CAST_NEVER_SUCCEEDS") val textFieldEnder = this as TextFieldEnder
    TextBind.logger.debug(
        "[TextFieldEnder] Char: ${typedChar}, KeyCode: ${Keyboard.getKeyName(keyCode)}")

    if (!isFocused) {
      return false
    }

    val keyCombination = KeyCombination.current().withKey(Key[keyCode] ?: return false)
    TextBind.logger.debug("[TextFieldEnder] KeyCombination: {}", keyCombination)

    GuiTextFieldActions[keyCombination]?.let {
      //      if (!isEnabled) return false
      if (it.invoke(this)) return true
    }

    return if (filter != null && filter.passesFilter(textFieldEnder, typedChar)) {
      textField.writeText(typedChar.toString())
      true
    } else {
      false
    }
  }
}
