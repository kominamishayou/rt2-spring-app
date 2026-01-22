package jp.co.sss.crud.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class InitialPasswordForm {
	public String getEmpPass() {
		return empPass;
	}

	public void setEmpPass(String empPass) {
		this.empPass = empPass;
	}

	/** パスワード */
	@NotBlank
	@Pattern(regexp = "^[A-Za-z0-9]*$")
	@Size(max = 16, message = "パスワードは16文字までの半角英数字で入力してください。")
	private String empPass;
	
	
}
