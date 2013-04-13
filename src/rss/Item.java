package rss;

import org.dom4j.Element;

public class Item {
	private Element title;
	public Element getTitle() {
		return title;
	}
	public void setTitle(Element title) {
		this.title = title;
	}
	public Element getLink() {
		return link;
	}
	public void setLink(Element link) {
		this.link = link;
	}
	public Element getDescription() {
		return description;
	}
	public void setDescription(Element description) {
		this.description = description;
	}
	public Element getGuid() {
		return guid;
	}
	public void setGuid(Element guid) {
		this.guid = guid;
	}
	public Element getPubDate() {
		return pubDate;
	}
	public void setPubDate(Element pubDate) {
		this.pubDate = pubDate;
	}
	public Element getItem() {
		return item;
	}
	public Item(Element channel){
		item=channel.addElement("item");
		link=item.addElement("link");
		description=item.addElement("description");
		guid=item.addElement("guid");
		pubDate=item.addElement("pubDate");	
	}
	private Element link;
	private Element description;
	private Element guid;
	private Element pubDate;
	private Element item;
}
