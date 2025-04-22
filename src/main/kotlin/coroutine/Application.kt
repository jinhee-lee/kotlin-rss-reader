package coroutine

import coroutine.client.Channel
import coroutine.domain.PostStore
import coroutine.view.InputView
import coroutine.view.OutputView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.Duration

fun main() =
    runBlocking(Dispatchers.IO) {
        val channels =
            listOf(
                "https://woowabros.github.io/feed.xml",
                "https://toss.tech/rss.xml",
                "https://helloworld.kurly.com/feed.xml",
                "https://developers.hyundaimotorgroup.com/blog/rss",
            ).map { Channel(it) }

        val postStore = PostStore()

        val readJob =
            launch {
                while (isActive) {
                    val posts = postStore.persistAll(channels)
                    OutputView.printNewPosts(posts)
                    delay(Duration.ofMinutes(10).toMillis())
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

        inputJob.join()
        readJob.cancel()
    }
