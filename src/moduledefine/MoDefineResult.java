/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package moduledefine;

import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class MoDefineResult {
    private ArrayList<KeyWordAndDefineType> keywordve;
    public  MoDefineResult(ArrayList<KeyWordAndDefineType> keywordve)
    {
        this.keywordve=keywordve;
    }/**
      * 由关键字容器返回其结果结构，用于得到模块定义结果
     * @return 模块定义结果
     */
    public ArrayList<DefineResult> getdefineResult()
    {
        if(this.keywordve==null||this.keywordve.size()==0)
        {
            return null;
        }
        ArrayList<DefineResult> temp=new ArrayList<DefineResult>(this.keywordve.size());
        for(int i=0;i<this.keywordve.size();i++)
        {
            temp.add(new DefineResult(this.keywordve.get(i).keyWord));
        }
        return temp;
    }
    /**
     * 打印结果
     * @param d
     * @return 字符串
     */
    public  static String printResult(ArrayList<DefineResult> d)
    {
        StringBuilder temp=new StringBuilder(60);
        for(int i=0;i<d.size();i++)
        {
            temp.append(d.get(i).keyword+" "+d.get(i).sueecss+"\r\n");
        }
        return new String(temp);
    }
}

