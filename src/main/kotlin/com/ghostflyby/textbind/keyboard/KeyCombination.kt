package com.ghostflyby.textbind.keyboard

import net.minecraft.client.Minecraft
import org.lwjgl.input.Keyboard

/**
 * A class to represent a key combination.
 * @see org.lwjgl.input.Keyboard
 */
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
    /**
     * The currently pressed key combination.
     * @see Keyboard.getEventKey
     * @see Keyboard.isKeyDown
     */
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

    /** A key combination with no modifiers and no key. */
    val none
      get(): KeyCombination {
        return KeyCombination()
      }
  }

  constructor() : this(false, false, false, false, Key.KEY_0)

  /** A key combination with no modifiers and the given key. */
  constructor(key: Key) : this() {
    this.key = key
  }

  /**
   * @param key The key to change to.
   * @return The key combination with the given key.
   */
  fun withKey(key: Key): KeyCombination {
    this.key = key
    return this
  }

  /** @return The key combination with the control modifier. */
  val control
    get(): KeyCombination {
      controlDown = true
      return this
    }

  /** @return The key combination with the control modifier. */
  val ctrl
    get() = control
  /** @return The key combination with the control modifier. */
  val c
    get() = control

  /** @return The key combination with the shift modifier. */
  val shift
    get(): KeyCombination {
      shiftDown = true
      return this
    }

  /** @return The key combination with the shift modifier. */
  val s
    get() = shift

  /** @return The key combination with the menu modifier. */
  val menu
    get(): KeyCombination {
      menuDown = true
      return this
    }

  /** @return The key combination with the menu(alt) modifier. */
  val alt
    get() = menu
  /** @return The key combination with the menu modifier on Mac, null on others. */
  val option
    get() = menu.mac
  /** @return The key combination with the menu modifier on Mac, null on others. */
  val o
    get() = menu.mac

  /** @return The key combination with the meta modifier */
  val meta
    get(): KeyCombination {
      metaDown = true
      return this
    }

  /** @return The key combination with the meta(cmd) modifier on Mac, null on others */
  val cmd
    get() = meta.mac
  /** @return The key combination with the meta modifier */
  val m
    get() = meta

  /** @return The key combination with cmd on Mac, control on others */
  val usual
    get(): KeyCombination {
      return if (Minecraft.isRunningOnMac) {
        meta
      } else {
        control
      }
    }

  /** @return The key combination with cmd on Mac, control on others */
  val u
    get() = usual

  /** @return The original key combination on Mac, null on others */
  val mac
    get(): KeyCombination? {
      return if (Minecraft.isRunningOnMac) {
        this
      } else {
        null
      }
    }

  /** @return The original key combination on non-Macs, null on Macs */
  val nomac
    get(): KeyCombination? {
      return if (Minecraft.isRunningOnMac) {
        null
      } else {
        this
      }
    }
}
