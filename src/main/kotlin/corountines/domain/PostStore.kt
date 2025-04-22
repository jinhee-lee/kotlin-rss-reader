package corountines.domain

import corountines.client.Channel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class PostStore(
    val posts: MutableList<Post> = mutableListOf(),
) {
    suspend fun persistAll(channels: List<Channel>): List<Post> {
        val posts = channels.posts()
        if (posts.isNotEmpty()) {
            val newPosts = posts.filter { it !in this.posts }.take(5)
            this.posts.addAll(newPosts)
            return newPosts
        }
        return listOf()
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
