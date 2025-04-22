package study

import org.w3c.dom.Element
import org.w3c.dom.NodeList
import javax.xml.parsers.DocumentBuilderFactory

fun main() {
    val factory = DocumentBuilderFactory.newInstance()
    val xml = factory.newDocumentBuilder().parse("https://woowabros.github.io/feed.xml")
    val channel = xml.getElementsByTagName("channel").item(0) as Element

    val feedItems = mutableListOf<FeedItem>()
    val itemList: NodeList = channel.getElementsByTagName("item")

    for (i in 0 until itemList.length) {
        val itemNode = itemList.item(i) as Element
        val title = itemNode.getElementsByTagName("title").item(0)?.textContent ?: ""
        val link = itemNode.getElementsByTagName("link").item(0)?.textContent ?: ""
        val description = itemNode.getElementsByTagName("description").item(0)?.textContent ?: ""
        val publishedAt = itemNode.getElementsByTagName("pubDate").item(0)?.textContent ?: ""

        val feedItem = FeedItem(title, link, publishedAt, description)
        feedItems.add(feedItem)
    }
}
