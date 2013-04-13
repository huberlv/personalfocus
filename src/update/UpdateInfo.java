/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package update;

/**
 *更新信息的数据结构类
 * @author Administrator
 */
public class UpdateInfo {
    /**模块id*/
   public String moduleId;
    /**更新内容*/
   public String updatecontent;
    public UpdateInfo(String moduleId,String updatecontent)
    {
        this.moduleId=moduleId;
        this.updatecontent=updatecontent;
    }

}
