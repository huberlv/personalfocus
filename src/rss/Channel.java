package rss;
import org.dom4j.Element;

public class Channel {
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
	public Element getCategory() {
		return category;
	}
	public void setCategory(Element category) {
		this.category = category;
	}
	public Element getCloud() {
		return cloud;
	}
	public void setCloud(Element cloud) {
		this.cloud = cloud;
	}
	public Element getCopyright() {
		return copyright;
	}
	public void setCopyright(Element copyright) {
		this.copyright = copyright;
	}
	public Element getDocs() {
		return docs;
	}
	public void setDocs(Element docs) {
		this.docs = docs;
	}
	public Element getGenerator() {
		return generator;
	}
	public void setGenerator(Element generator) {
		this.generator = generator;
	}
	public Element getLanguage() {
		return language;
	}
	public void setLanguage(Element language) {
		this.language = language;
	}
	public Element getLastBuildDate() {
		return lastBuildDate;
	}
	public void setLastBuildDate(Element lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}
	public Element getManagingEditor() {
		return managingEditor;
	}
	public void setManagingEditor(Element managingEditor) {
		this.managingEditor = managingEditor;
	}
	public Element getPubDate() {
		return pubDate;
	}
	public void setPubDate(Element pubDate) {
		this.pubDate = pubDate;
	}
	public Element getTtl() {
		return ttl;
	}
	public void setTtl(Element ttl) {
		this.ttl = ttl;
	}
	public Element getWebMaster() {
		return webMaster;
	}
	public void setWebMaster(Element webMaster) {
		this.webMaster = webMaster;
	}
	
	public Element getChannel(Element channel){
		this.channel=channel;
		return this.channel;
	}
	private Element link;
	private Element description;
	private Element category;
	private Element cloud;
	private Element copyright;
	private Element docs;
	private Element generator;
	private Element language;
	private Element lastBuildDate;
	private Element managingEditor;
	private Element pubDate;
	private Element ttl;
	private Element webMaster;
	private Element channel;
}
