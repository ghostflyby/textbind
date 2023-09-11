package com.ghostflyby.textbind.actions

import com.ghostflyby.textbind.keyboard.Key.*
import com.ghostflyby.textbind.keyboard.KeyCombination as KC
import com.ghostflyby.textbind.keyboard.KeyCombination
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.GuiTextField

fun keys(vararg keys: KC?) = keys.filterNotNull().toTypedArray()

enum class GuiTextFieldActions(
    val defaultBindings: Array<KC>,
    val action: (GuiTextField) -> Boolean
) {
  ESC(
      keys(KEY_ESCAPE()),
      {
        it.isFocused = false
        true
      }),
  LEFT(
      keys(KEY_LEFT(), KEY_B.c.mac),
      {
        it.cursorPosition--
        true
      }),
  RIGHT(
      keys(KEY_RIGHT(), KEY_F.c.mac),
      {
        it.cursorPosition++
        true
      }),
  WORD_LEFT(
      keys(KEY_LEFT.c.nomac, KEY_B.c.o, KEY_LEFT.option, KEY_B.option),
      {
        it.cursorPosition = it.getNthWordFromCursor(-1)
        true
      }),
  WORD_RIGHT(
      keys(KEY_RIGHT.c.nomac, KEY_F.c.o, KEY_RIGHT.option, KEY_F.option),
      {
        it.cursorPosition = it.getNthWordFromCursor(1)
        true
      }),
  HOME(
      keys(KEY_HOME(), KEY_LEFT.cmd, KEY_A.c.mac),
      {
        it.setCursorPositionZero()
        true
      }),
  END(
      keys(KEY_END(), KEY_RIGHT.cmd, KEY_E.c.mac),
      {
        it.setCursorPositionEnd()
        true
      }),
  COPY(
      keys(KEY_C.u),
      {
        GuiScreen.setClipboardString(it.selectedText.ifEmpty { it.text })
        true
      }),
  PASTE(
      keys(KEY_V.u),
      {
        it.writeText(GuiScreen.getClipboardString())
        true
      }),
  CUT(
      keys(KEY_X.u),
      {
        GuiScreen.setClipboardString(it.selectedText.ifEmpty { it.text })
        it.writeText("")
        true
      }),
  SELECT_ALL(
      keys(KEY_A.u),
      {
        it.setCursorPositionEnd()
        it.setSelectionPos(0)
        true
      }),
  DELETE(
      keys(KEY_DELETE(), KEY_D.c.mac),
      {
        it.deleteFromCursor(1)
        true
      }),
  BACKSPACE(
      keys(KEY_BACK()),
      {
        it.deleteFromCursor(-1)
        true
      }),
  WORD_DELETE(
      keys(KEY_DELETE.c.nomac, KEY_D.c.o, KEY_DELETE.option, KEY_D.option),
      {
        it.deleteWords(1)
        true
      }),
  WORD_BACKSPACE(
      keys(KEY_BACK.c.nomac, KEY_BACK.option, KEY_W.c.mac),
      {
        it.deleteWords(-1)
        true
      }),
  LINE_DELETE(
      keys(KEY_DELETE.cmd, KEY_K.c.mac),
      {
        it.deleteFromCursor(it.text.length - it.cursorPosition)
        true
      }),
  LINE_BACKSPACE(
      keys(KEY_BACK.cmd, KEY_U.c.mac),
      {
        it.deleteFromCursor(-it.cursorPosition)
        true
      }),
  SELECT_LEFT(
      keys(KEY_LEFT.shift, KEY_B.s.c.mac),
      {
        it.setSelectionPos(it.selectionEnd - 1)
        true
      }),
  SELECT_RIGHT(
      keys(KEY_RIGHT.shift, KEY_F.s.c.mac),
      {
        it.setSelectionPos(it.selectionEnd + 1)
        true
      }),
  SELECT_WORD_LEFT(
      keys(KEY_LEFT.c.s.nomac, KEY_B.s.c.o, KEY_LEFT.s.option),
      {
        it.apply { setSelectionPos(getNthWordFromPos(-1, selectionEnd)) }
        true
      }),
  SELECT_WORD_RIGHT(
      keys(KEY_RIGHT.c.s.nomac, KEY_F.c.s.o, KEY_RIGHT.s.option),
      {
        it.apply { setSelectionPos(getNthWordFromPos(1, selectionEnd)) }
        true
      }),
  SELECT_HOME(
      keys(KEY_HOME.shift, KEY_LEFT.shift.cmd, KEY_A.s.c.mac),
      {
        it.setSelectionPos(0)
        true
      }),
  SELECT_END(
      keys(KEY_END.shift, KEY_RIGHT.shift.cmd, KEY_E.s.c.mac),
      {
        it.setSelectionPos(it.text.length)
        true
      });

  companion object {
    private val map = values().flatMap { v -> v.defaultBindings.map { it to v.action } }.toMap()
    operator fun get(keyCombination: KeyCombination) = map[keyCombination]
  }
}
