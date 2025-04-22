package corountines

import corountines.client.Channel
import corountines.domain.Post
import corountines.domain.PostStore
import corountines.view.InputView
import corountines.view.OutputView
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

fun main() =
    runBlocking {
        val clients =
            listOf(
                Channel("https://woowabros.github.io/feed.xml"),
                Channel("https://toss.tech/rss.xml"),
            )

        val postStore =
            PostStore().apply {
                persistAll(clients.posts())
            }

        val input = InputView.readSearchKeyword()
        val result = postStore.findByTitleIn(input)

        OutputView.printResult(result)
    }

private suspend fun List<Channel>.posts(): List<Post> {
    return coroutineScope {
        this@posts.map {
            async { it.findPosts() }
        }.awaitAll().flatten()
    }
}
