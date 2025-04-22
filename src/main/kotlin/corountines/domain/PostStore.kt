package corountines.domain

import corountines.view.OutputView

class PostStore(
    val posts: MutableList<Post> = mutableListOf(),
) {
    fun persistAll(posts: List<Post>) {
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
