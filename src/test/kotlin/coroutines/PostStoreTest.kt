package coroutines

import corountines.client.Channel
import corountines.domain.PostStore
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class PostStoreTest {
    @Test
    fun test_persist_all() =
        runTest {
            val postStore =
                PostStore(
                    listOf(
                        Channel("https://woowabros.github.io/feed.xml"),
                        Channel("https://toss.tech/rss.xml"),
                    ),
                )

            repeat(3) {
                val job =
                    launch(Dispatchers.IO) {
                        println("Job1 > in ${Thread.currentThread()}")
                        postStore.persistAll()
                    }
                job.join()

                val checkJob =
                    launch {
                        println("Job2 > in ${Thread.currentThread()}")
                        postStore.posts.size shouldBe (it + 1) * 5
                    }
                checkJob.join()
            }
        }
}
