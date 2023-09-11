package com.ghostflyby.textbind.keyboard

import org.lwjgl.input.Keyboard

class Modifier {
  companion object {
    fun isControlDown(): Boolean {
      return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)
    }
    fun isMenuDown(): Boolean {
      return Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU)
    }
    fun isMetaDown(): Boolean {
      return Keyboard.isKeyDown(Keyboard.KEY_LMETA) || Keyboard.isKeyDown(Keyboard.KEY_RMETA)
    }
  }
}
