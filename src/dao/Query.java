package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import po.Student;

public class Query {
	static Connection con = Conn.getConnection();

	/**
	 * 查询全部学生成绩信息
	 * 
	 * @return 学生信息集合
	 */
	public static List<Student> queryAll(String state) {
		List<Student> list = new ArrayList<Student>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM `student` WHERE state = '"
							+ state + "' ORDER BY major");
			while (rs.next()) {
				Student student = new Student();
				student.setId((Integer.parseInt(rs.getString("id").trim())));
				student.setName(rs.getString("name").trim());
				student.setSex(rs.getString("sex").trim());
				student.setStudentId(rs.getString("student_id").trim());
				student.setMajor(rs.getString("major").trim());
				student.setChineseGrade(rs.getString("chinese_grade").trim());
				student.setMathGrade(rs.getString("math_grade").trim());
				student.setEnglishGrade(rs.getString("english_grade").trim());
				student.setState(rs.getString("state").trim());
				list.add(student);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "查找失败");
		}
		return list;
	}

	/**
	 * 按照关键字模糊查询
	 */
	public static List<Student> queryByInfo(String name, String value) {
		List<Student> list = new ArrayList<Student>();
		try {
			String sql = "SELECT * FROM `student` WHERE state = 'T' AND "
					+ name + " like '%" + value + "%'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Student student = new Student();
				student.setId((Integer.parseInt(rs.getString("id").trim())));
				student.setName(rs.getString("name").trim());
				student.setSex(rs.getString("sex").trim());
				student.setStudentId(rs.getString("student_id").trim());
				student.setMajor(rs.getString("major").trim());
				student.setChineseGrade(rs.getString("chinese_grade").trim());
				student.setMathGrade(rs.getString("math_grade").trim());
				student.setEnglishGrade(rs.getString("english_grade").trim());
				student.setState(rs.getString("state").trim());
				list.add(student);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "查找失败");
		}
		return list;
	}

	/**
	 * 查询最高或最低分
	 * 
	 * @param course
	 *            课程名
	 * @param index
	 *            最高或最低分的判断
	 * @return 学生信息集合
	 */
	public static List<Student> getMaxOrMinGrade(String course, int index) {
		List<Student> list = new ArrayList<Student>();
		try {
			String sql = "SELECT * FROM `student` WHERE " + course
					+ " = (SELECT ";
			if (index == 0) {
				sql += "MAX(" + course + ") FROM `student`) AND state = 'T'";
			} else if (index == 1) {
				sql += "MIN(" + course + ") FROM `student`) AND state = 'T'";
			}
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Student student = new Student();
				student.setId((Integer.parseInt(rs.getString("id").trim())));
				student.setName(rs.getString("name").trim());
				student.setSex(rs.getString("sex").trim());
				student.setStudentId(rs.getString("student_id").trim());
				student.setMajor(rs.getString("major").trim());
				student.setChineseGrade(rs.getString("chinese_grade").trim());
				student.setMathGrade(rs.getString("math_grade").trim());
				student.setEnglishGrade(rs.getString("english_grade").trim());
				student.setState(rs.getString("state").trim());
				list.add(student);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "查找失败");
		}
		return list;
	}

	/**
	 * 获取班级集合
	 * 
	 * @return 班级集合
	 */
	public static List<String> getClassList() {
		List<String> list = new ArrayList<>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT DISTINCT major FROM `student` WHERE state = 'T' ORDER BY major");
			while (rs.next()) {
				list.add(rs.getString("major"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "查找失败");
		}
		return list;
	}

	/**
	 * 获取班级成绩情况
	 * 
	 * @param course
	 *            所选课程
	 * @param index
	 *            模式
	 * @return 信息集合
	 */
	public static Map<String, Integer> getClassInfo(String course, int index) {
		Map<String, Integer> map = new HashMap<>();
		try {
			String sql = "SELECT major,count(1) FROM `student` WHERE " + course;
			if (index == 0) {
				sql += ">=90";
			} else if (index == 1) {
				sql += ">=60";
			} else if (index == 2) {
				sql += "<60";
			} else if (index == 3) {
				sql += ">=0";
			}
			sql += " AND state = 'T' GROUP BY major";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String major = rs.getString("major");
				Integer count = Integer.parseInt(rs.getString("count(1)"));
				map.put(major, count);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "查找失败");
		}
		return map;
	}

	/**
	 * 判断是否存在相同的学号
	 * 
	 * @param studentId
	 *            需要判断的学号
	 * @return 是否存在
	 */
	public static Boolean getStudentId(String studentId) {
		Boolean flag = new Boolean(false);
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM `student` WHERE student_id = '"
							+ studentId + "'");
			if (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "查找失败");
		}
		return flag;
	}
}
