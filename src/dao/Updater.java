package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import po.Student;

public class Updater {
	static Connection con = Conn.getConnection();

	/**
	 * 更新学生状态
	 * 
	 * @param id
	 *            学生id
	 * @return 是否删除成功
	 */

	public static Boolean updateStudentState(int id, String state) {
		Boolean flag = new Boolean(true);
		try {
			Statement stmt = con.createStatement();
			int result = stmt.executeUpdate("UPDATE `student` SET state = '"
					+ state + "' WHERE id = " + id);
			if (result == 0) {
				flag = false;
			}
		} catch (SQLException e) {
			flag = false;
			JOptionPane.showMessageDialog(null, "删除失败");
		}
		return flag;
	}

	/**
	 * 插入学生信息
	 * 
	 * @param student
	 *            学生信息集合
	 * @return 是否插入成功
	 */
	public static Boolean insertStudent(Student student) {
		Boolean flag = new Boolean(true);
		try {
			String sql = "INSERT `student`(name,student_id,sex,major,chinese_grade,math_grade,english_grade)VALUES('"
					+ student.getName()
					+ "','"
					+ student.getStudentId()
					+ "','"
					+ student.getSex()
					+ "','"
					+ student.getMajor()
					+ "','"
					+ student.getChineseGrade()
					+ "','"
					+ student.getMathGrade()
					+ "','"
					+ student.getEnglishGrade() + "')";
			Statement stmt = con.createStatement();
			int result = stmt.executeUpdate(sql);
			if (result == 0) {
				flag = false;
			}
		} catch (SQLException e) {
			flag = false;
			JOptionPane.showMessageDialog(null, "插入失败");
		}
		return flag;
	}

	/**
	 * 更新学生信息
	 * 
	 * @param student
	 *            学生信息封装类
	 * @return 是否更新成功
	 */
	public static Boolean updateStudent(Student student) {
		Boolean flag = new Boolean(true);
		try {
			String sql = "UPDATE `student`SET name='" + student.getName()
					+ "',student_id='" + student.getStudentId() + "',sex='"
					+ student.getSex() + "',major='" + student.getMajor()
					+ "',chinese_grade='" + student.getChineseGrade()
					+ "',math_grade='" + student.getMathGrade()
					+ "',english_grade='" + student.getEnglishGrade()
					+ "' WHERE id=" + student.getId();
			Statement stmt = con.createStatement();
			int result = stmt.executeUpdate(sql);
			if (result == 0) {
				flag = false;
			}
		} catch (SQLException e) {
			flag = false;
			JOptionPane.showMessageDialog(null, "更新失败");
		}
		return flag;
	}

	/**
	 * 彻底删除学生信息
	 * 
	 * @param id
	 *            学生id
	 * @return 成功与否
	 */
	public static Boolean deleteStudent(int id) {
		Boolean flag = new Boolean(true);
		try {
			Statement stmt = con.createStatement();
			int result = stmt.executeUpdate("DELETE FROM `student` WHERE id = "
					+ id);
			if (result == 0) {
				flag = false;
			}
		} catch (SQLException e) {
			flag = false;
			JOptionPane.showMessageDialog(null, "删除失败");
		}
		return flag;
	}

	/**
	 * 批量插入学生信息
	 * 
	 * @param list
	 *            学生信息集合
	 */
	public static void batchInsertStudent(List<Student> list) {
		try {
			String sql = "insert into `student`(name,student_id,sex,major,chinese_grade,math_grade,english_grade) values(?,?,?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			for (Iterator<Student> iterator = list.iterator(); iterator
					.hasNext();) {
				Student student = iterator.next();
				pstmt.setString(1, student.getName());
				pstmt.setString(2, student.getStudentId());
				pstmt.setString(3, student.getSex());
				pstmt.setString(4, student.getMajor());
				pstmt.setString(5, student.getChineseGrade());
				pstmt.setString(6, student.getMathGrade());
				pstmt.setString(7, student.getEnglishGrade());
				pstmt.addBatch();// 用PreparedStatement的批量处理
			}
			pstmt.executeBatch();// 执行批处理
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "插入失败");
		}
	}
}
