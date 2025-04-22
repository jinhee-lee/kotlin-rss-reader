package corountines.domain

import corountines.client.Channel
import corountines.view.OutputView
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class PostStore(
    val channels: List<Channel>,
    val posts: MutableList<Post> = mutableListOf(),
) {
    suspend fun persistAll() {
        val posts = channels.posts()
        if (posts.isNotEmpty()) {
            val newItemList = posts.filter { it !in this.posts }.take(5)

            OutputView.printNewItemResult(newItemList)

            this.posts.addAll(newItemList)
        }
    }

    fun findByTitleIn(title: String): List<Post> {
        if (title.isEmpty()) {
            return posts
                .sortedByDescending { it.publishedAt }
                .take(10)
        }

        return posts
            .filter { title in it.title }
            .sortedByDescending { it.publishedAt }
    }
}

private suspend fun List<Channel>.posts(): List<Post> {
    return coroutineScope {
        this@posts.map {
            async { it.findPosts() }
        }.awaitAll().flatten()
    }
}
