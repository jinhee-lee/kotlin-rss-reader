package corountines.domain

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

data class Post(
    val title: String,
    val link: String,
    val publishedAt: OffsetDateTime,
) {
    fun display(number: Int): String =
        "[$number] $title (${
            publishedAt.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            )
        }) - $link"

    fun newItemDisplay(number: Int): String =
        "[NEW] $title (${
            publishedAt.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            )
        }) - $link"
}
