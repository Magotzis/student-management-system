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
	 * ����ѧ��״̬
	 * 
	 * @param id
	 *            ѧ��id
	 * @return �Ƿ�ɾ���ɹ�
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
			JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
		}
		return flag;
	}

	/**
	 * ����ѧ����Ϣ
	 * 
	 * @param student
	 *            ѧ����Ϣ����
	 * @return �Ƿ����ɹ�
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
			JOptionPane.showMessageDialog(null, "����ʧ��");
		}
		return flag;
	}

	/**
	 * ����ѧ����Ϣ
	 * 
	 * @param student
	 *            ѧ����Ϣ��װ��
	 * @return �Ƿ���³ɹ�
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
			JOptionPane.showMessageDialog(null, "����ʧ��");
		}
		return flag;
	}

	/**
	 * ����ɾ��ѧ����Ϣ
	 * 
	 * @param id
	 *            ѧ��id
	 * @return �ɹ����
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
			JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
		}
		return flag;
	}

	/**
	 * ��������ѧ����Ϣ
	 * 
	 * @param list
	 *            ѧ����Ϣ����
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
				pstmt.addBatch();// ��PreparedStatement����������
			}
			pstmt.executeBatch();// ִ��������
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "����ʧ��");
		}
	}
}
