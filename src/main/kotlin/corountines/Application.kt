package corountines

import corountines.client.FeedClient
import corountines.domain.FeedItem
import corountines.domain.FeedStore
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
                FeedClient("https://woowabros.github.io/feed.xml"),
                FeedClient("https://toss.tech/rss.xml"),
            )

        val feedStore =
            FeedStore().apply {
                addAll(clients.reads())
            }

        val input = InputView.readSearchKeyword()
        val result = feedStore.findByTitleContains(input)

        OutputView.printResult(result)
    }

private suspend fun List<FeedClient>.reads(): List<FeedItem> {
    return coroutineScope {
        this@reads.map {
            async { it.read() }
        }.awaitAll().flatten()
    }
}
