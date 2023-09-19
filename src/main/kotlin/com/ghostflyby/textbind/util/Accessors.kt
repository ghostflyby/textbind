package com.ghostflyby.textbind.util

import codechicken.nei.SearchField
import com.ghostflyby.textbind.mixins.IGuiChat
import com.ghostflyby.textbind.mixins.ISearchField
import net.minecraft.client.gui.GuiChat

val GuiChat.accessor: IGuiChat
  get() = this as IGuiChat

val SearchField.accessor: ISearchField
  get() = this as ISearchField
