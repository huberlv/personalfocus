package document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
        String html="<html><div><span class='content_r'>22</span><span class='content_r'>11</span></div></html>";
        String t2="<div name='name1'>此处显示 name1 的内容</div><div name='name1'>此处显示 name2 的内容</div>";
        Document d=Jsoup.parse(new URL("http://localhost:8080/personalfocus/test/1.html"), 50000);
       // Document d=Jsoup.parse(html);
       // System.out.println(d.body().html());
      //  Document d=Jsoup.parse(t2);
        System.out.println(d.select("div[name=name1]:eq(0)").size()+"///");
	}

}
