package com.kingway.util;

public class OnlineFocus {
   public static final int MONITOR_BY_PLATEFORM=0x1;  //00000001
   public static final int MONITOR_BY_USER=0x2;  //00000010
   public static final int MONITOR_LINKS=0x4;  //00000100
   public static final int MONITOR_ALL=0x8;   //00001000
   public static final String UPDATESTYLE="updateStyle";
   public static final String UI_CSS_SCRIPT=
	"<link rel='stylesheet' href='/personalfocus/css/jqueryui/jquery.ui.all.css'>"
	+"<script src='/personalfocus/js/jqueryui/jquery.bgiframe-2.1.2.js'></script>" 
	+"<script src='/personalfocus/js/jqueryui/jquery.ui.core.js'></script>" 
	+"<script src='/personalfocus/js/jqueryui/jquery.ui.widget.js'></script>" 
	+"<script src='/personalfocus/js/jqueryui/jquery.ui.mouse.js'></script>" 
	+"<script src='/personalfocus/js/jqueryui/jquery.ui.draggable.js'></script>" 
	+"<script src='/personalfocus/js/jqueryui/jquery.ui.position.js'></script>" 
	+"<script src='/personalfocus/js/jqueryui/jquery.ui.resizable.js'></script>" 
	+"<script src='/personalfocus/js/jqueryui/jquery.ui.dialog.js'></script>";
   public static String DEFAULT_TIME=PropertiesUtil.getProperty("DEFAULT_MTIME");
}
