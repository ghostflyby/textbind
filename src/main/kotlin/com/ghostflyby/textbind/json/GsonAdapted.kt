package com.ghostflyby.textbind.json

import com.ghostflyby.textbind.keyboard.KeyCombination
import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonAdapted {
  val gson: Gson =
      GsonBuilder()
          .registerTypeAdapter(KeyCombination::class.java, KeyCombinationAdaptor())
          .create()
}
