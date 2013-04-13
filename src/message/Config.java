/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package message;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author Administrator
 */
public class Config {

    private File configFile = new File("/MSconfig.xml");
    private Document document;
    private Element rootElm;

    public Config(String file) throws DocumentException {
        SAXReader reader = new SAXReader();   
        document = (Document) reader.read(new File(file));
        rootElm = document.getRootElement();
    }

    public String getParameter(String name) throws IOException {
        Element e = this.rootElm.element(name);
        if (e != null) {
            return e.getText();
        }

        throw new IOException();
    }

    public void setParameter(String name, String value) throws IOException {
        if (this.document != null) {
            Element e = this.rootElm.element(name);
            if (e != null) {
                e.setText(value);
                return;
            }
        }
        throw new IOException();
    }

    public void save() throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8"); //
        XMLWriter writer = new XMLWriter(new FileWriter(this.configFile.toString()), format);
        writer.write(document);
        writer.close();
    }

    public CommonPara getCommonPara() {
        try {
            return new CommonPara(this.getParameter("address"), this.getParameter("user"), this.getParameter("password"));
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void main(String args[]) throws IOException {
        Config con;
        try {
            con = new Config("MSconfig.xml");
            CommonPara db = con.getCommonPara();
        } catch (DocumentException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
