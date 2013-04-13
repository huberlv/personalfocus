/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduledefine;

/**
 *
 * @author Administrator
 */
import java.net.*;
import java.io.*;
import org.dom4j.DocumentException;

/**
 *
 *   服务器端程序：
 *   1.   监听一端口，等待客户接入；
 *   2.   一旦有客户接入，就构造一个Socket会话对象；
 *   3.   将这个会话交给线程处理，然后主程序继续监听。
 */
public class Server extends ServerSocket {

    private static Server server;

    private Server(int serverPort) throws IOException {
        //用指定的端口构造一个ServerSocket
        super(serverPort);
        try {
            while (true) {
                //监听一端口，等待客户接入
             
                Socket socket = accept();
                //将会话交给线程处理
                new ServerThread(socket);
            }
        } catch (IOException e) {
        } finally {
            close();     //关闭监听端口
        }
    }

    public static Server openServer() throws DocumentException {
        if (server != null) {
            return server;
        }

        try {
            server = new Server(2000);
            return server;
        } catch (Exception ex) {
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        new Server(2000);
    }
}
