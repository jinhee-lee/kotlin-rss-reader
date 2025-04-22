package coroutines

import corountines.client.Channel
import corountines.domain.PostStore
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class PostStoreTest {
    @Test
    fun test_persist_and_read() =
        runTest {
            val postStore =
                PostStore(
                    listOf(
                        Channel("https://woowabros.github.io/feed.xml"),
                        Channel("https://toss.tech/rss.xml"),
                    ),
                )

            val job = launch { postStore.persistAll() }
            job.join()
            postStore.posts.size shouldBe 5
        }
}
