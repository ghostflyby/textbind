package com.ghostflyby.textbind.actions

import com.ghostflyby.textbind.keyboard.Key.*
import com.ghostflyby.textbind.util.accessor
import net.minecraft.client.gui.GuiChat

enum class GuiChatActions(val action: Action<GuiChat>) {
  COMPLETE({
    it.func_146404_p_()
    true
  }),
  ESCAPE({
    it.mc.displayGuiScreen(null)
    true
  }),
  SEND({
    val s = it.accessor.inputField.text
    if (s.isNotBlank() && s.isNotEmpty()) it.func_146403_a(s)
    it.mc.displayGuiScreen(null)
    true
  }),
  PREVIOUS({
    it.getSentHistory(-1)
    true
  }),
  NEXT({
    it.getSentHistory(1)
    true
  }),
  PAGEUP({
    it.mc.ingameGUI.chatGUI.scroll(it.mc.ingameGUI.chatGUI.func_146232_i() - 1)
    true
  }),
  PAGEDOWN({
    it.mc.ingameGUI.chatGUI.scroll(-it.mc.ingameGUI.chatGUI.func_146232_i() + 1)
    true
  });
  companion object :
      ActionCompanion<GuiChatActions, GuiChat>(
          "Chat",
          mapOf(
              COMPLETE to keys(KEY_TAB()),
              ESCAPE to keys(KEY_ESCAPE()),
              SEND to keys(KEY_RETURN(), KEY_NUMPADENTER()),
              PREVIOUS to keys(KEY_UP(), KEY_P.control),
              NEXT to keys(KEY_DOWN(), KEY_N.control),
              PAGEUP to keys(KEY_PRIOR()),
              PAGEDOWN to keys(KEY_NEXT()))) {

    private val map = GuiChatActions.values().associateBy(GuiChatActions::name)
    override fun get(name: String): Action<GuiChat>? = map[name]?.action
  }
}
