package corountines.domain

class FeedStore(
    val feedItems: MutableList<FeedItem> = mutableListOf(),
) {
    fun addAll(feedItems: List<FeedItem>) {
        if (feedItems.isNotEmpty()) {
            this.feedItems.addAll(feedItems)
        }
    }

    fun findByTitleContains(value: String): List<FeedItem> {
        if (value.isEmpty()) {
            return feedItems
                .sortedByDescending { it.publishedAt }
                .take(10)
        }

        return feedItems
            .filter { value in it.title }
            .sortedByDescending { it.publishedAt }
    }
}
