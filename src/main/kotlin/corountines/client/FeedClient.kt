package corountines.client

import corountines.domain.FeedItem
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.xml.parsers.DocumentBuilderFactory

class FeedClient(
    val link: String,
) {
    fun read(): List<FeedItem> {
        val factory = DocumentBuilderFactory.newInstance()
        val xml = factory.newDocumentBuilder().parse(this.link)
        val channel = xml.getElementsByTagName("channel").item(0) as Element

        val feedItems = mutableListOf<FeedItem>()
        val itemList: NodeList = channel.getElementsByTagName("item")

        for (i in 0 until itemList.length) {
            val itemNode = itemList.item(i) as Element
            val title = itemNode.getElementsByTagName("title").item(0)?.textContent ?: ""
            val link = itemNode.getElementsByTagName("link").item(0)?.textContent ?: ""

            val publishedAtAsString = itemNode.getElementsByTagName("pubDate").item(0)?.textContent ?: ""
            val publishedAt = OffsetDateTime.parse(publishedAtAsString, DateTimeFormatter.RFC_1123_DATE_TIME)

            val feedItem = FeedItem(title, link, publishedAt)
            feedItems.add(feedItem)
        }
        return feedItems
    }
}
