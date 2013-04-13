/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package update;

/**
 *
 * @author Administrator
 */
public class Module{
    /**模块在网页中的特征*/
   public String path;
     /**当前文本*/
   public String text;
         /**模块的id*/
   public String moduleId;
   
    public  Module(String path,String text,String moduleId)
    {
        this.path=path;
        this.text=text;
        this.moduleId=moduleId;
        
    }
}
