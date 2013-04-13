package email;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ReadEmailSender {
	
    private Document document;
    private Element rootElm;
    public ArrayList<EmailSender> senders = new ArrayList<EmailSender>();
    
    public ReadEmailSender(String file) throws DocumentException{
         SAXReader reader = new SAXReader();
         document = (Document) reader.read(new File(file));
         rootElm=document.getRootElement();
    }

    public ArrayList<EmailSender> getSender() throws IOException{
    	List list = rootElm.selectNodes("sender" );
    	for(Object o : list){
        	Element e = (Element) o;
    		String host = e.elementText("host");
    		String from = e.elementText("from");
    		String psw = e.elementText("psw");
    		senders.add(new EmailSender(host,from,psw));
        }
    	return senders;
    }
    
}
