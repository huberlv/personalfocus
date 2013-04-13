/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

/**
 *信息参数数据结构 
 * @author Administrator
 */
public class MessageParameter {

    /**字段名*/
    public String parameter = null;
    /**字段值*/
    public String value = null;

    public MessageParameter(String parameter, String value) {
        this.parameter = parameter;
        this.value = value;
    }
}

