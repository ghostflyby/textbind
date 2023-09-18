package com.ghostflyby.textbind.actions

import com.ghostflyby.textbind.keyboard.Key.*
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.GuiTextField

enum class GuiTextFieldActions(val action: Action<GuiTextField>) {
  ESC({
    it.isFocused = false
    true
  }),
  LEFT({
    it.cursorPosition--
    true
  }),
  RIGHT({
    it.cursorPosition++
    true
  }),
  WORD_LEFT({
    it.cursorPosition = it.getNthWordFromCursor(-1)
    true
  }),
  WORD_RIGHT({
    it.cursorPosition = it.getNthWordFromCursor(1)
    true
  }),
  HOME({
    it.setCursorPositionZero()
    true
  }),
  END({
    it.setCursorPositionEnd()
    true
  }),
  COPY({
    GuiScreen.setClipboardString(it.selectedText.ifEmpty { it.text })
    true
  }),
  PASTE({
    it.writeText(GuiScreen.getClipboardString())
    true
  }),
  CUT({
    GuiScreen.setClipboardString(
        it.selectedText.ifEmpty {
          com.ghostflyby.textbind.actions.GuiTextFieldActions.SELECT_ALL.action.invoke(it)
          it.text
        })
    it.text = ""
    true
  }),
  SELECT_ALL({
    it.setCursorPositionEnd()
    it.setSelectionPos(0)
    true
  }),
  DELETE({
    it.deleteFromCursor(1)
    true
  }),
  BACKSPACE({
    it.deleteFromCursor(-1)
    true
  }),
  WORD_DELETE({
    it.deleteWords(1)
    true
  }),
  WORD_BACKSPACE({
    it.deleteWords(-1)
    true
  }),
  LINE_DELETE({
    it.deleteFromCursor(it.text.length - it.cursorPosition)
    true
  }),
  LINE_BACKSPACE({
    it.deleteFromCursor(-it.cursorPosition)
    true
  }),
  SELECT_LEFT({
    it.setSelectionPos(it.selectionEnd - 1)
    true
  }),
  SELECT_RIGHT({
    it.setSelectionPos(it.selectionEnd + 1)
    true
  }),
  SELECT_WORD_LEFT({
    it.apply { setSelectionPos(getNthWordFromPos(-1, selectionEnd)) }
    true
  }),
  SELECT_WORD_RIGHT({
    it.apply { setSelectionPos(getNthWordFromPos(1, selectionEnd)) }
    true
  }),
  SELECT_HOME({
    it.setSelectionPos(0)
    true
  }),
  SELECT_END({
    it.setSelectionPos(it.text.length)
    true
  });

  companion object :
      ActionCompanion<GuiTextFieldActions, GuiTextField>(
          "GuiTextField",
          mapOf(
              ESC to keys(KEY_ESCAPE()),
              LEFT to keys(KEY_LEFT(), KEY_B.c.mac),
              RIGHT to keys(KEY_RIGHT(), KEY_F.c.mac),
              WORD_LEFT to keys(KEY_LEFT.c.nomac, KEY_B.c.o, KEY_LEFT.option, KEY_B.option),
              WORD_RIGHT to keys(KEY_RIGHT.c.nomac, KEY_F.c.o, KEY_RIGHT.option, KEY_F.option),
              HOME to keys(KEY_HOME(), KEY_LEFT.cmd, KEY_A.c.mac),
              END to keys(KEY_END(), KEY_RIGHT.cmd, KEY_E.c.mac),
              COPY to keys(KEY_C.u),
              PASTE to keys(KEY_V.u),
              CUT to keys(KEY_X.u),
              SELECT_ALL to keys(KEY_A.u),
              DELETE to keys(KEY_DELETE(), KEY_D.c.mac),
              BACKSPACE to keys(KEY_BACK()),
              WORD_DELETE to keys(KEY_DELETE.c.nomac, KEY_D.c.o, KEY_DELETE.option, KEY_D.option),
              WORD_BACKSPACE to keys(KEY_BACK.c.nomac, KEY_BACK.option, KEY_W.c.mac),
              LINE_DELETE to keys(KEY_DELETE.cmd, KEY_K.c.mac),
              LINE_BACKSPACE to keys(KEY_BACK.cmd, KEY_U.c.mac),
              SELECT_LEFT to keys(KEY_LEFT.shift, KEY_B.s.c.mac),
              SELECT_RIGHT to keys(KEY_RIGHT.shift, KEY_F.s.c.mac),
              SELECT_WORD_LEFT to keys(KEY_LEFT.c.s.nomac, KEY_B.s.c.o, KEY_LEFT.s.option),
              SELECT_WORD_RIGHT to keys(KEY_RIGHT.c.s.nomac, KEY_F.c.s.o, KEY_RIGHT.s.option),
              SELECT_HOME to keys(KEY_HOME.shift, KEY_LEFT.shift.cmd, KEY_A.s.c.mac),
              SELECT_END to keys(KEY_END.shift, KEY_RIGHT.shift.cmd, KEY_E.s.c.mac),
          )) {
    private val map = values().associateBy(GuiTextFieldActions::name)
    override fun get(name: String): Action<GuiTextField>? = map[name]?.action
  }
}
