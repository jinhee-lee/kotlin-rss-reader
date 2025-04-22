package corountines

import corountines.client.Channel
import corountines.domain.PostStore
import corountines.view.InputView
import corountines.view.OutputView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() =
    runBlocking {
        val channels =
            listOf(
                Channel("https://woowabros.github.io/feed.xml"),
                Channel("https://toss.tech/rss.xml"),
            )

        val postStore = PostStore()

        val readJob =
            launch(Dispatchers.IO) {
                while (isActive) {
                    val posts = postStore.persistAll(channels)
                    OutputView.printNewPosts(posts)
                    delay(1000 * 5)
                }
            }

        val inputJob =
            launch {
                while (isActive) {
                    val input = async { InputView.readSearchKeyword() }
                    val result = postStore.findByTitleIn(input.await())
                    OutputView.printSearchResults(result)
                }
            }
    }
