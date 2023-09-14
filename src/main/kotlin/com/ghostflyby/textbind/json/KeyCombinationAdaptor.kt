package com.ghostflyby.textbind.json

import com.ghostflyby.textbind.keyboard.Key
import com.ghostflyby.textbind.keyboard.KeyCombination
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class KeyCombinationAdaptor : TypeAdapter<KeyCombination>() {
  override fun write(writer: JsonWriter?, value: KeyCombination?) {
    if (writer == null || value == null) return
    writer.apply {
      beginObject()
      name("control").value(value.controlDown)
      name("shift").value(value.shiftDown)
      name("alt").value(value.menuDown)
      name("meta").value(value.metaDown)
      name("key").value(value.key?.name)
      endObject()
    }
  }

  override fun read(reader: JsonReader?): KeyCombination {
    if (reader == null) return KeyCombination.none
    var control = false
    var shift = false
    var alt = false
    var meta = false
    var key = Key.KEY_NONE
    reader.apply {
      beginObject()
      while (hasNext()) {
        when (nextName()) {
          "control" -> control = nextBoolean()
          "shift" -> shift = nextBoolean()
          "alt" -> alt = nextBoolean()
          "meta" -> meta = nextBoolean()
          "key" -> key = Key.valueOf(nextString())
        }
      }
      endObject()
    }
    return KeyCombination(control, shift, alt, meta, key)
  }
}
