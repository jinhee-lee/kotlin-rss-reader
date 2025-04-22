package coroutines

import corountines.client.Channel
import corountines.domain.Post
import corountines.domain.PostStore
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class PostStoreTest {
    @Test
    fun test_persist_and_read() =

        runTest {
            val channel1 = mockk<Channel>()
            every { channel1.findPosts() } answers { listOf(mockk<Post>()) }

            val channel2 = mockk<Channel>()
            every { channel2.findPosts() } answers { listOf(mockk<Post>()) }

            val postStore = PostStore()

            repeat(3) {
                val job =
                    launch(Dispatchers.IO) {
                        println("Job1 > in ${Thread.currentThread()}")
                        postStore.persistAll(listOf(channel1, channel2))
                    }
                job.join()

                val checkJob =
                    launch {
                        println("Job2 > in ${Thread.currentThread()}")
                        postStore.posts.size shouldBe (it + 1) * 2
                    }
                checkJob.join()
            }
        }
}
