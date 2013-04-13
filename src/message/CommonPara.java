/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

/**
 *通用配置参数类
 * @author Administrator
 */
public class CommonPara {

    public String address;
    public String user;
    public String password;

    public CommonPara(String address, String user, String password) {
        this.address = address;
        this.user = user;
        this.password = password;
    }
}
