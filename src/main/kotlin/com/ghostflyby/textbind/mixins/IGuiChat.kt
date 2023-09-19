package com.ghostflyby.textbind.mixins

import net.minecraft.client.gui.GuiChat
import net.minecraft.client.gui.GuiTextField
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Accessor

@Mixin(GuiChat::class)
interface IGuiChat {
  @get:Accessor val inputField: GuiTextField
}
