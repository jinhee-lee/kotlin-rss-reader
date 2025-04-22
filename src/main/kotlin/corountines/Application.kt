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
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() =
    runBlocking {
        val channels =
            listOf(
                Channel("https://woowabros.github.io/feed.xml"),
                Channel("https://toss.tech/rss.xml"),
            )


        val postStore = PostStore()



        val readJob = launch(Dispatchers.IO) {
            while (isActive) {
                postStore.persistAll(posts3(channels))
                delay(1000 * 5)
            }
        }



        val inputJob = launch {
            while (isActive) {
                val input = async { InputView.readSearchKeyword() }
                val result = postStore.findByTitleIn(input.await())
                OutputView.printResult(result)
            }
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
