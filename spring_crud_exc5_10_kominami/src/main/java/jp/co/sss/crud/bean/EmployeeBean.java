package jp.co.sss.crud.bean;

import java.util.Date;

/**
 * 従業員情報を管理するJavaBeanクラス。
 * 従業員の基本情報（ID、パスワード、名前、性別、住所、誕生日、権限、部署情報）を保持します。
 * 
 * @author システム開発チーム
 * @version 1.0
 * @since 1.0
 */
public class EmployeeBean {

	/** 従業員ID */
	private Integer empId;

	/** 従業員パスワード */
	private String empPass;

	/** 従業員名 */
	private String empName;

	/** 性別（1:男性、2:女性） */
	private Integer gender;

	/** 住所 */
	private String address;

	/** 誕生日 */
	private Date birthday;

	/** 権限レベル */
	private Integer authority;

	/** 部署ID */
	private Integer deptId;

	/** 部署名 */
	private String deptName;

	/**
	 * デフォルトコンストラクタ。
	 * 全てのフィールドがnullまたはデフォルト値で初期化されます。
	 */
	public EmployeeBean() {
	}

	/**
	 * 全てのフィールドを指定するコンストラクタ。
	 * 部署IDに基づいて部署名が自動的に設定されます。
	 * 
	 * @param empId 従業員ID
	 * @param empPass 従業員パスワード
	 * @param empName 従業員名
	 * @param gender 性別
	 * @param address 住所
	 * @param birthday 誕生日
	 * @param authority 権限レベル
	 * @param deptId 部署ID（1:営業部、2:経理部、3:総務部）
	 */
	public EmployeeBean(Integer empId, String empPass, String empName, Integer gender, String address, Date birthday,
			Integer authority, Integer deptId) {
		this.empId = empId;
		this.empPass = empPass;
		this.empName = empName;
		this.gender = gender;
		this.address = address;
		this.birthday = birthday;
		this.authority = authority;
		this.deptId = deptId;

		switch (deptId) {
		case 1:
			this.deptName = "営業部";
			break;
		case 2:
			this.deptName = "経理部";
			break;
		case 3:
			this.deptName = "総務部";

			break;

		default:
			break;
		}

	}

	/**
	 * 従業員IDを取得します。
	 * 
	 * @return 従業員ID
	 */
	public Integer getEmpId() {
		return empId;
	}

	/**
	 * 従業員IDを設定します。
	 * 
	 * @param empId 従業員ID
	 */
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	/**
	 * 従業員パスワードを取得します。
	 * 
	 * @return 従業員パスワード
	 */
	public String getEmpPass() {
		return empPass;
	}

	/**
	 * 従業員パスワードを設定します。
	 * 
	 * @param empPass 従業員パスワード
	 */
	public void setEmpPass(String empPass) {
		this.empPass = empPass;
	}

	/**
	 * 従業員名を取得します。
	 * 
	 * @return 従業員名
	 */
	public String getEmpName() {
		return empName;
	}

	/**
	 * 従業員名を設定します。
	 * 
	 * @param empName 従業員名
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * 性別を取得します。
	 * 
	 * @return 性別
	 */
	public Integer getGender() {
		return gender;
	}

	/**
	 * 性別を設定します。
	 * 
	 * @param gender 性別
	 */
	public void setGender(Integer gender) {
		this.gender = gender;
	}

	/**
	 * 住所を取得します。
	 * 
	 * @return 住所
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 住所を設定します。
	 * 
	 * @param address 住所
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 誕生日を取得します。
	 * 
	 * @return 誕生日
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * 誕生日を設定します。
	 * 
	 * @param birthday 誕生日
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * 権限レベルを取得します。
	 * 
	 * @return 権限レベル
	 */
	public Integer getAuthority() {
		return authority;
	}

	/**
	 * 権限レベルを設定します。
	 * 
	 * @param authority 権限レベル
	 */
	public void setAuthority(Integer authority) {
		this.authority = authority;

	}

	/**
	 * 部署IDを取得します。
	 * 
	 * @return 部署ID
	 */
	public Integer getDeptId() {
		return deptId;
	}

	/**
	 * 部署IDを設定します。
	 * 部署IDに基づいて部署名も自動的に設定されます。
	 * 
	 * @param deptId 部署ID（1:営業部、2:経理部、3:総務部）
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;

		switch (deptId) {
		case 1:
			this.deptName = "営業部";
			break;
		case 2:
			this.deptName = "経理部";

			break;
		case 3:
			this.deptName = "総務部";

			break;

		default:
			break;
		}

	}

	/**
	 * 部署名を取得します。
	 * 
	 * @return 部署名
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * 部署名を設定します。
	 * 
	 * @param deptName 部署名
	 */
	public void setDeptName(String deptName) {

		this.deptName = deptName;
	}

}