package com.ghostflyby.textbind.keyboard

import net.minecraft.client.Minecraft
import org.lwjgl.input.Keyboard

data class KeyCombination(
    var controlDown: Boolean,
    var shiftDown: Boolean,
    var menuDown: Boolean,
    var metaDown: Boolean,
    var key: Key?
) {
  override fun toString(): String {
    val control = if (controlDown) "control" else ""
    val shift = if (shiftDown) "shift" else ""
    val menu = if (menuDown) "menu" else ""
    val meta = if (metaDown) "meta" else ""
    return "$control $shift $menu $meta ${key?.name}"
  }

  companion object {
    fun current(): KeyCombination {
      return KeyCombination(
          controlDown =
              Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) ||
                  Keyboard.isKeyDown(Keyboard.KEY_LCONTROL),
          shiftDown =
              Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT),
          menuDown =
              Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_LMENU),
          metaDown =
              Keyboard.isKeyDown(Keyboard.KEY_LMETA) || Keyboard.isKeyDown(Keyboard.KEY_LMETA),
          key = Key[Keyboard.getEventKey()])
    }

    val none
      get(): KeyCombination {
        return KeyCombination()
      }
  }

  constructor() : this(false, false, false, false, Key.KEY_0)
  constructor(key: Key) : this() {
    this.key = key
  }

  fun withKey(key: Key): KeyCombination {
    this.key = key
    return this
  }

  val control
    get(): KeyCombination {
      controlDown = true
      return this
    }

  val ctrl
    get() = control
  val c
    get() = control

  val shift
    get(): KeyCombination {
      shiftDown = true
      return this
    }

  val s
    get() = shift

  val menu
    get(): KeyCombination {
      menuDown = true
      return this
    }

  val alt
    get() = menu
  val option
    get() = menu.mac
  val o
    get() = menu.mac

  val meta
    get(): KeyCombination {
      metaDown = true
      return this
    }

  val cmd
    get() = meta.mac
  val m
    get() = meta

  val usual
    get(): KeyCombination {
      return if (Minecraft.isRunningOnMac) {
        meta
      } else {
        control
      }
    }

  val u
    get() = usual

  val mac
    get(): KeyCombination? {
      return if (Minecraft.isRunningOnMac) {
        this
      } else {
        null
      }
    }

  val nomac
    get(): KeyCombination? {
      return if (Minecraft.isRunningOnMac) {
        null
      } else {
        this
      }
    }
}
