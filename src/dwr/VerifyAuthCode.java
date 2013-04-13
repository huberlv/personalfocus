package dwr;

import com.opensymphony.xwork2.ActionContext;

public class VerifyAuthCode {

	public boolean verifycode(String code) {
		String authCode = (String) ActionContext.getContext().getSession().get(
				"rand");
		System.out.println(authCode);
		System.out.println(code);
		if (authCode.equals(code)) {
			return true;

		} else
			return false;
	}
}
