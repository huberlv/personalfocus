package com.kingway.model;



/**
 * ModuleUserModulesViewId entity. @author MyEclipse Persistence Tools
 */

public class ModuleUserModulesViewId  implements java.io.Serializable {


    // Fields    

     private Long moduleId;
     private Long contentId;


    // Constructors

    /** default constructor */
    public ModuleUserModulesViewId() {
    }

    
    /** full constructor */
    public ModuleUserModulesViewId(Long moduleId, Long contentId) {
        this.moduleId = moduleId;
        this.contentId = contentId;
    }

   
    // Property accessors

    public Long getModuleId() {
        return this.moduleId;
    }
    
    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getContentId() {
        return this.contentId;
    }
    
    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ModuleUserModulesViewId) ) return false;
		 ModuleUserModulesViewId castOther = ( ModuleUserModulesViewId ) other; 
         
		 return ( (this.getModuleId()==castOther.getModuleId()) || ( this.getModuleId()!=null && castOther.getModuleId()!=null && this.getModuleId().equals(castOther.getModuleId()) ) )
 && ( (this.getContentId()==castOther.getContentId()) || ( this.getContentId()!=null && castOther.getContentId()!=null && this.getContentId().equals(castOther.getContentId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getModuleId() == null ? 0 : this.getModuleId().hashCode() );
         result = 37 * result + ( getContentId() == null ? 0 : this.getContentId().hashCode() );
         return result;
   }   





}