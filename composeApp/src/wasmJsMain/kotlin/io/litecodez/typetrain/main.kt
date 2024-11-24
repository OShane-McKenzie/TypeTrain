package io.litecodez.typetrain

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import io.litecodez.typetrain.objects.ContentProvider
import kotlinx.browser.document

val contentProvider = ContentProvider()
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        App()
    }
}