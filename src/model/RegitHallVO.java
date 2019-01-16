package model;

public class RegitHallVO {
	private int hiNo;
	private String hiName;
	private int hiArea;
	private int hiNum;
	private int genreOne;
	private int genreTwo;
	private int genreThree;
	private int genreFour;
	
	public RegitHallVO() {
		super();
	}

	public RegitHallVO(int hiNo, String hiName, int hiArea, int hiNum, int genreOne, int genreTwo, int genreThree,
			int genreFour) {
		super();
		this.hiNo = hiNo;
		this.hiName = hiName;
		this.hiArea = hiArea;
		this.hiNum = hiNum;
		this.genreOne = genreOne;
		this.genreTwo = genreTwo;
		this.genreThree = genreThree;
		this.genreFour = genreFour;
	}

	public RegitHallVO(String hiName, int hiArea, int hiNum, int genreOne, int genreTwo, int genreThree,
			int genreFour) {
		super();
		this.hiName = hiName;
		this.hiArea = hiArea;
		this.hiNum = hiNum;
		this.genreOne = genreOne;
		this.genreTwo = genreTwo;
		this.genreThree = genreThree;
		this.genreFour = genreFour;
	}
	
	

	public RegitHallVO(int hiNo, int hiArea, int hiNum, int genreOne, int genreTwo, int genreThree, int genreFour) {
		super();
		this.hiNo = hiNo;
		this.hiArea = hiArea;
		this.hiNum = hiNum;
		this.genreOne = genreOne;
		this.genreTwo = genreTwo;
		this.genreThree = genreThree;
		this.genreFour = genreFour;
	}

	public int getHiNo() {
		return hiNo;
	}

	public void setHiNo(int hiNo) {
		this.hiNo = hiNo;
	}

	public String getHiName() {
		return hiName;
	}

	public void setHiName(String hiName) {
		this.hiName = hiName;
	}

	public int getHiArea() {
		return hiArea;
	}

	public void setHiArea(int hiArea) {
		this.hiArea = hiArea;
	}

	public int getHiNum() {
		return hiNum;
	}

	public void setHiNum(int hiNum) {
		this.hiNum = hiNum;
	}

	public int getGenreOne() {
		return genreOne;
	}

	public void setGenreOne(int genreOne) {
		this.genreOne = genreOne;
	}

	public int getGenreTwo() {
		return genreTwo;
	}

	public void setGenreTwo(int genreTwo) {
		this.genreTwo = genreTwo;
	}

	public int getGenreThree() {
		return genreThree;
	}

	public void setGenreThree(int genreThree) {
		this.genreThree = genreThree;
	}

	public int getGenreFour() {
		return genreFour;
	}

	public void setGenreFour(int genreFour) {
		this.genreFour = genreFour;
	}

	
	
}
