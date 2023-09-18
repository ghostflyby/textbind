package com.ghostflyby.textbind.mixins

import codechicken.nei.SearchField
import codechicken.nei.util.TextHistory
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Invoker

@Mixin(SearchField::class, remap = false)
@Suppress("NonJavaMixin")
interface IMixinSearchFieldAccessor {

  @Invoker fun callHandleNavigateHistory(direction: TextHistory.Direction): Boolean
}
