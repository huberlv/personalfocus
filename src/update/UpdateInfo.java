/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package update;

/**
 *������Ϣ�����ݽṹ��
 * @author Administrator
 */
public class UpdateInfo {
    /**ģ��id*/
   public String moduleId;
    /**��������*/
   public String updatecontent;
    public UpdateInfo(String moduleId,String updatecontent)
    {
        this.moduleId=moduleId;
        this.updatecontent=updatecontent;
    }

}
