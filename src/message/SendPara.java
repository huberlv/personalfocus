/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package message;
/**
 *
 * @author Administrator
 */
public class SendPara {
    public String receiver;
    public String content;
    public SendPara(String receiver, String content) {
        this.content = content;
        this.receiver = receiver;
    }
}
