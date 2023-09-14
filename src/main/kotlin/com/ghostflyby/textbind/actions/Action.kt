package com.ghostflyby.textbind.actions

import com.ghostflyby.textbind.keyboard.KeyCombination

typealias Action<T> = (T) -> Boolean

fun keys(vararg keys: KeyCombination?) = keys.filterNotNull().toTypedArray()
