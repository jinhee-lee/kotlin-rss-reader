package corountines.domain

class PostStore(
    val posts: MutableList<Post> = mutableListOf(),
) {
    fun persistAll(posts: List<Post>) {
        if (posts.isNotEmpty()) {
            this.posts.addAll(posts)
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
