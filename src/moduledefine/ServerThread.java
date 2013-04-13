/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduledefine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class ServerThread extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ServerThread(Socket s) throws IOException {
        this.socket = s;
        //   构造该会话中的输入输出流
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        out = new PrintWriter(socket.getOutputStream(), false);
        start();
    }

    public void run() {
        try {
            String line;
            line = in.readLine();
            System.out.println(line);
            int begin = line.indexOf("=");
            int end = line.indexOf(" ", begin + 1);
            if (begin < 0 || end < 0) {
                return;
            }
            line = line.substring(begin + 1, end);
            System.out.println(line);

            HtmlHandler htm = new HtmlHandler(line, HtmlHandler.DEFINE_TOP_HEAD_STR,HtmlHandler.DEFINE_TOP_BODY_STR);
            htm.getHandleString();
            out.print(htm.getHandleString());
            out.flush();
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            try {
                e.printStackTrace();
                out.close();
                in.close();
                socket.close();
            } catch (IOException ex) {
            }
        }
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
