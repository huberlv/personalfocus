/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package update;

import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
 public class ModuleNeedToBeUpdate{
    public ArrayList<Module> modules;
    public String address;
    public ModuleNeedToBeUpdate(ArrayList<Module> modules,String address)
    {
        this.modules=modules;
        this.address=address;
    }

}
