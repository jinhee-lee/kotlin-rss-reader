package corountines.domain

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class FeedItem(
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
}
