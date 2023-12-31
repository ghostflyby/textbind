package com.ghostflyby.textbind.actions

import com.ghostflyby.textbind.config.Config
import com.ghostflyby.textbind.json.GsonAdapted.gson
import com.ghostflyby.textbind.keyboard.KeyCombination
import com.google.gson.reflect.TypeToken

sealed class ActionCompanion<E : Enum<*>, ActionParam>(
    private val configGroup: String,
    private val enumToKeyCombinations: Map<E, Array<KeyCombination>>
) {
  operator fun get(keyCombination: KeyCombination): Action<ActionParam>? = bindMap[keyCombination]

  abstract operator fun get(name: String): Action<ActionParam>?

  private var bindMap: Map<KeyCombination, Action<ActionParam>> = mapOf()
  private fun defaultBindMap(): Map<String, Array<KeyCombination>> {
    return enumToKeyCombinations.map { it.key.name to it.value }.toMap()
  }

  fun loadConfig() {
    val (exist, file) = Config["$configGroup.json"]
    val type = object : TypeToken<LinkedHashMap<String, Array<KeyCombination>>>() {}.type
    var map: Map<String, Array<KeyCombination>>

    if (exist) file.reader().use { reader -> map = gson.fromJson(reader, type) }
    else {
      map = defaultBindMap()
      file.writer().use { writer -> gson.toJson(map, type, writer) }
    }

    bindMap =
        map.flatMap { (name, combinations) ->
              combinations.map { combination -> combination to get(name) }
            }
            .filter { it.second != null }
            .associate { it.first to it.second!! }
  }
}
