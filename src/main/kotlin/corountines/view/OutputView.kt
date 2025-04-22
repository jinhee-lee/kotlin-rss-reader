package corountines.view

import corountines.domain.FeedItem

object OutputView {
    fun printResult(feedItems: List<FeedItem>) {
        feedItems.forEachIndexed { index, item -> println(item.display(index + 1)) }
    }
}
