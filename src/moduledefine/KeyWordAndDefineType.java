/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package moduledefine;

/**
 *
 * @author Administrator
 */
public class KeyWordAndDefineType {
    public String keyWord;
    /**定义的类型，0代表单行，1代表整个table或div*/
    public int type=1;
    public String name;
    public KeyWordAndDefineType(String keyWord,int type,String name)
    {
       this.keyWord=keyWord;
       this.type=type;
       this.name=name;
    }
}
