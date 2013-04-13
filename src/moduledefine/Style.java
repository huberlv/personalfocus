package moduledefine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Style {
	
	private HashMap<String, String> style = new HashMap<String, String>();

	public Style(String sty) {
		StringTokenizer st = new StringTokenizer(sty, ";:");
		while (st.hasMoreTokens()) {
			style.put(((String)st.nextToken()).trim(), ((String)st.nextToken()).trim());
		}
	}

	public String getAStyle(String name) {
		String value = (String) this.style.get(name);
		if(value == null)
			return null;
		else
			return value.trim();
	}

	public void setAStyle(String name, String value) {
		this.style.put(name.trim(), value.trim());
	}
	
	/**
	 * 获取style的字符串表示
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getStyleStr(){
		StringBuffer styleStr = new StringBuffer();
		Collection c = style.entrySet();
		Iterator itr = c.iterator();
		while (itr.hasNext()) {
			styleStr.append(itr.next().toString().replace("=", ":")).append(";");
		}
		return styleStr.toString();
	}
	
}
