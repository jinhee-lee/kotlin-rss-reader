package corountines

import corountines.client.Channel
import corountines.domain.Post
import corountines.domain.PostStore
import corountines.view.InputView
import corountines.view.OutputView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() =
    runBlocking {
        val channels =
            listOf(
                Channel("https://woowabros.github.io/feed.xml"),
                Channel("https://toss.tech/rss.xml"),
            )

        val postStore =
            PostStore().apply {
                persistAll(channels.posts())
            }

        while (true) {
            val input = InputView.readSearchKeyword()
            val result = postStore.findByTitleIn(input)

            OutputView.printResult(result)
        }
    }

private suspend fun List<Channel>.posts(): List<Post> {
    return coroutineScope {
        this@posts.map {
            async { it.findPosts() }
        }.awaitAll().flatten()
    }
}

private suspend fun List<Channel>.posts2(): List<Post> {
    return withContext(Dispatchers.IO) {
        map {
            async { it.findPosts() }
        }.awaitAll().flatten()
    }
}

private suspend fun CoroutineScope.posts3(channels: List<Channel>): List<Post> {
    return channels.map { async { it.findPosts() } }
        .awaitAll().flatten()
}
