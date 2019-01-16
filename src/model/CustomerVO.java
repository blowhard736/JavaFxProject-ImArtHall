package model;

public class CustomerVO {
	private String cusType;
	private String cusComname;
	private String cusNum;
	private String cusCeoname;
	private String cusName;
	private String cusAddr;
	private String cusPhone;
	private String cusEmail;
	private String cusGenre;
	private String cusTitle;
	private int cusPrice;
	private String cusStartTime;
	private String cusEndTime;
	private int no;

	public CustomerVO() {
		super();
	}
	
	public CustomerVO(String cusType, String cusComname, String cusNum, String cusCeoname, String cusName,
			String cusAddr, String cusPhone, String cusEmail, int no) {
		super();
		this.cusType = cusType;
		this.cusComname = cusComname;
		this.cusNum = cusNum;
		this.cusCeoname = cusCeoname;
		this.cusName = cusName;
		this.cusAddr = cusAddr;
		this.cusPhone = cusPhone;
		this.cusEmail = cusEmail;
		this.no = no;
	}

	public CustomerVO(String cusType, String cusComname, String cusNum, String cusCeoname, String cusName,
			String cusAddr, String cusPhone, String cusEmail, String cusGenre, String cusTitle, int cusPrice,
			String cusStartTime, String cusEndTime, int no) {
		super();
		this.cusType = cusType;
		this.cusComname = cusComname;
		this.cusNum = cusNum;
		this.cusCeoname = cusCeoname;
		this.cusName = cusName;
		this.cusAddr = cusAddr;
		this.cusPhone = cusPhone;
		this.cusEmail = cusEmail;
		this.cusGenre = cusGenre;
		this.cusTitle = cusTitle;
		this.cusPrice = cusPrice;
		this.cusStartTime = cusStartTime;
		this.cusEndTime = cusEndTime;
		this.no = no;
	}

	public CustomerVO(String cusType, String cusComname, String cusNum, String cusCeoname, String cusName,
			String cusAddr, String cusPhone, String cusEmail, String cusGenre, String cusTitle, int cusPrice,
			String cusStartTime, String cusEndTime) {
		super();
		this.cusType = cusType;
		this.cusComname = cusComname;
		this.cusNum = cusNum;
		this.cusCeoname = cusCeoname;
		this.cusName = cusName;
		this.cusAddr = cusAddr;
		this.cusPhone = cusPhone;
		this.cusEmail = cusEmail;
		this.cusGenre = cusGenre;
		this.cusTitle = cusTitle;
		this.cusPrice = cusPrice;
		this.cusStartTime = cusStartTime;
		this.cusEndTime = cusEndTime;
	}

	public CustomerVO(String cusType, String cusComname, String cusName, String cusAddr, String cusPhone,
			String cusEmail, String cusGenre, String cusTitle, int cusPrice, String cusStartTime, String cusEndTime,
			int no) {
		super();
		this.cusType = cusType;
		this.cusComname = cusComname;
		this.cusName = cusName;
		this.cusAddr = cusAddr;
		this.cusPhone = cusPhone;
		this.cusEmail = cusEmail;
		this.cusGenre = cusGenre;
		this.cusTitle = cusTitle;
		this.cusPrice = cusPrice;
		this.cusStartTime = cusStartTime;
		this.cusEndTime = cusEndTime;
		this.no = no;
	}

	public CustomerVO(String cusType, String cusComname, String cusName, String cusAddr, String cusPhone,
			String cusEmail, String cusGenre, String cusTitle, int cusPrice, String cusStartTime, String cusEndTime) {
		super();
		this.cusType = cusType;
		this.cusComname = cusComname;
		this.cusName = cusName;
		this.cusAddr = cusAddr;
		this.cusPhone = cusPhone;
		this.cusEmail = cusEmail;
		this.cusGenre = cusGenre;
		this.cusTitle = cusTitle;
		this.cusPrice = cusPrice;
		this.cusStartTime = cusStartTime;
		this.cusEndTime = cusEndTime;
	}

	public CustomerVO(String cusName, String cusAddr, String cusPhone, String cusEmail, String cusGenre,
			String cusTitle, int cusPrice, String cusStartTime, String cusEndTime, int no) {
		super();
		this.cusName = cusName;
		this.cusAddr = cusAddr;
		this.cusPhone = cusPhone;
		this.cusEmail = cusEmail;
		this.cusGenre = cusGenre;
		this.cusTitle = cusTitle;
		this.cusPrice = cusPrice;
		this.cusStartTime = cusStartTime;
		this.cusEndTime = cusEndTime;
		this.no = no;
	}

	public String getCusType() {
		return cusType;
	}

	public void setCusType(String cusType) {
		this.cusType = cusType;
	}

	public String getCusComname() {
		return cusComname;
	}

	public void setCusComname(String cusComname) {
		this.cusComname = cusComname;
	}

	public String getCusNum() {
		return cusNum;
	}

	public void setCusNum(String cusNum) {
		this.cusNum = cusNum;
	}

	public String getCusCeoname() {
		return cusCeoname;
	}

	public void setCusCeoname(String cusCeoname) {
		this.cusCeoname = cusCeoname;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusAddr() {
		return cusAddr;
	}

	public void setCusAddr(String cusAddr) {
		this.cusAddr = cusAddr;
	}

	public String getCusPhone() {
		return cusPhone;
	}

	public void setCusPhone(String cusPhone) {
		this.cusPhone = cusPhone;
	}

	public String getCusEmail() {
		return cusEmail;
	}

	public void setCusEmail(String cusEmail) {
		this.cusEmail = cusEmail;
	}

	public String getCusGenre() {
		return cusGenre;
	}

	public void setCusGenre(String cusGenre) {
		this.cusGenre = cusGenre;
	}

	public String getCusTitle() {
		return cusTitle;
	}

	public void setCusTitle(String cusTitle) {
		this.cusTitle = cusTitle;
	}

	public int getCusPrice() {
		return cusPrice;
	}

	public void setCusPrice(int cusPrice) {
		this.cusPrice = cusPrice;
	}

	public String getCusStartTime() {
		return cusStartTime;
	}

	public void setCusStartTime(String cusStartTime) {
		this.cusStartTime = cusStartTime;
	}

	public String getCusEndTime() {
		return cusEndTime;
	}

	public void setCusEndTime(String cusEndTime) {
		this.cusEndTime = cusEndTime;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

}
