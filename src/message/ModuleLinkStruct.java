/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

/**
 *模块链接短信结构类
 * @author Administrator
 */
public class ModuleLinkStruct {

    public String userName;
    public String phone;

    public ModuleLinkStruct(
            String userName,
            String phone) {
        this.phone = phone;
        this.userName = userName;

    }
}
