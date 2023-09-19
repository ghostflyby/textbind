package com.ghostflyby.textbind.mixins

import codechicken.nei.SearchField
import codechicken.nei.TextField
import com.ghostflyby.textbind.actions.SearchFieldActions
import com.ghostflyby.textbind.keyboard.Key
import com.ghostflyby.textbind.keyboard.KeyCombination
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(SearchField::class, remap = false)
@Suppress("NonJavaMixin")
abstract class MixinSearchField(ident: String?) : TextField(ident) {

  @Inject(method = ["lastKeyTyped"], at = [At("HEAD")], cancellable = true)
  private fun lastKeyTyped(keyId: Int, char: Char, cir: CallbackInfo) {
    @Suppress("CAST_NEVER_SUCCEEDS") val original = this as SearchField
    if (!original.isVisible) cir.cancel()

    val keyCombination = KeyCombination.current().withKey(Key[keyId] ?: return)
    if (SearchFieldActions[keyCombination]?.invoke(this) == true) cir.cancel()
  }
}
