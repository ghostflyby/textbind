package com.ghostflyby.textbind.actions

import codechicken.nei.SearchField
import com.ghostflyby.textbind.keyboard.Key.*
import com.ghostflyby.textbind.keyboard.KeyCombination
import com.ghostflyby.textbind.util.accessor

enum class SearchFieldActions(val action: Action<SearchField>) {
  PREVIOUS({
    if (it.focused()) {
      it.accessor.callHandleNavigateHistory(codechicken.nei.util.TextHistory.Direction.PREVIOUS)
      true
    } else false
  }),
  NEXT({
    if (it.focused()) {
      it.accessor.callHandleNavigateHistory(codechicken.nei.util.TextHistory.Direction.NEXT)
      true
    } else false
  }),
  TOGGLE_FOCUS({
    it.setFocus(!it.focused())
    true
  }),
  FOCUS({
    if (!it.focused()) {
      it.setFocus(true)
      true
    } else false
  }),
  UNFOCUS({
    if (it.focused()) {
      it.setFocus(false)
      true
    } else false
  });

  companion object :
      ActionCompanion<SearchFieldActions, SearchField>(
          "NeiSearchField",
          mapOf(
              PREVIOUS to keys(KeyCombination(KEY_UP), KEY_P.control),
              NEXT to keys(KeyCombination(KEY_DOWN), KEY_N.control),
              TOGGLE_FOCUS to keys(KEY_F.usual),
              FOCUS to keys(KeyCombination(KEY_F)),
              UNFOCUS to keys())) {
    private val map = SearchFieldActions.values().associateBy(SearchFieldActions::name)
    override fun get(name: String): Action<SearchField>? = map[name]?.action
  }
}
