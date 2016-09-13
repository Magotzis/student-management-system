package po;

public class Student {
	private int id; // 主键
	private String name;// 姓名
	private String studentId;// 学号
	private String sex;// 性别
	private String major;// 班级
	private String chineseGrade;// 语文成绩
	private String mathGrade;// 数学成绩
	private String englishGrade;// 英语成绩
	private String state;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getChineseGrade() {
		return chineseGrade;
	}

	public void setChineseGrade(String chineseGrade) {
		this.chineseGrade = chineseGrade;
	}

	public String getMathGrade() {
		return mathGrade;
	}

	public void setMathGrade(String mathGrade) {
		this.mathGrade = mathGrade;
	}

	public String getEnglishGrade() {
		return englishGrade;
	}

	public void setEnglishGrade(String englishGrade) {
		this.englishGrade = englishGrade;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
