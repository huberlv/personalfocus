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
 *   �������˳���
 *   1.   ����һ�˿ڣ��ȴ��ͻ����룻
 *   2.   һ���пͻ����룬�͹���һ��Socket�Ự����
 *   3.   ������Ự�����̴߳���Ȼ�����������������
 */
public class Server extends ServerSocket {

    private static Server server;

    private Server(int serverPort) throws IOException {
        //��ָ���Ķ˿ڹ���һ��ServerSocket
        super(serverPort);
        try {
            while (true) {
                //����һ�˿ڣ��ȴ��ͻ�����
             
                Socket socket = accept();
                //���Ự�����̴߳���
                new ServerThread(socket);
            }
        } catch (IOException e) {
        } finally {
            close();     //�رռ����˿�
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
