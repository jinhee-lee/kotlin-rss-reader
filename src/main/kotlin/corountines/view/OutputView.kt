package corountines.view

import corountines.domain.Post

object OutputView {
    fun printResult(feedItems: List<Post>) {
        feedItems.forEachIndexed { index, item -> println(item.display(index + 1)) }
    }
}
