package corountines.view

import corountines.domain.Post

object OutputView {
    fun printSearchResults(posts: List<Post>) {
        posts.forEachIndexed { index, item -> println(item.display(index + 1)) }
        println("")
    }

    fun printNewPosts(posts: List<Post>) {
        println("새로운 글이 등록되었습니다!")
        posts.forEachIndexed { index, item -> println(item.newItemDisplay(index + 1)) }
        println("")
    }
}
