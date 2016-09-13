package frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import dao.Query;
import dao.Updater;
import po.Student;

@SuppressWarnings("serial")
public class InfoFrame extends JFrame implements ActionListener {

	JLabel label = new JLabel("添加基本信息", JLabel.CENTER);
	JLabel labName = new JLabel("姓名：", JLabel.CENTER);
	JLabel labStudentId = new JLabel("学号：", JLabel.CENTER);
	JLabel labSex = new JLabel("性别：", JLabel.CENTER);
	JLabel labMajor = new JLabel("班级：", JLabel.CENTER);
	JLabel labChinese = new JLabel("语文成绩：", JLabel.CENTER);
	JLabel labMath = new JLabel("数学成绩：", JLabel.CENTER);
	JLabel labEnglish = new JLabel("英语成绩：", JLabel.CENTER);
	JTextField id = new JTextField(20);
	JTextField txtName = new JTextField(20);
	JTextField txtStudentId = new JTextField(18);
	JTextField txtMajor = new JTextField(20);
	JTextField txtChinese = new JTextField(20);
	JTextField txtMath = new JTextField(20);
	JTextField txtEnglish = new JTextField(20);
	ButtonGroup group = new ButtonGroup();
	JRadioButton man = new JRadioButton("男");
	JRadioButton women = new JRadioButton("女");
	JButton btnAdd = new JButton("添加");
	JButton btnUpdate = new JButton("保存");
	JButton btnCancel = new JButton("返回");
	JButton btnReset = new JButton("重置");
	JPanel panel = new JPanel(); // 创建面板对象

	/**
	 * 插入窗口实例化
	 */
	InfoFrame() {
		man.setSelected(true);
		initialPanel(0);
		setTitle("添加学生信息");
		setResizable(false);
		setSize(550, 450);
		setVisible(true);
		setLocation(300, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * 更新窗口实例化
	 * 
	 * @param student
	 *            选中的学生信息
	 */
	InfoFrame(Student student) {
		label.setText("更新基本信息");
		id.setText(String.valueOf(student.getId()));
		txtName.setText(student.getName());
		txtMajor.setText(student.getMajor());
		txtStudentId.setText(student.getStudentId());
		txtChinese.setText(student.getChineseGrade());
		txtMath.setText(student.getMathGrade());
		txtEnglish.setText(student.getEnglishGrade());
		if (student.getSex().equals("女")) {
			women.setSelected(true);
		} else {
			man.setSelected(true);
		}
		initialPanel(1);
		setTitle("更新学生信息");
		setResizable(false);
		setSize(550, 450);
		setVisible(true);
		setLocation(300, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * 初始化面板
	 */
	void initialPanel(int flag) {
		panel.setLayout(null);
		btnAdd.addActionListener(this);
		btnReset.addActionListener(this);
		btnCancel.addActionListener(this);
		btnUpdate.addActionListener(this);
		panel.setBackground(Color.cyan);
		man.setBackground(Color.cyan);
		women.setBackground(Color.cyan);
		label.setBounds(100, 20, 300, 20);
		panel.add(label);
		labName.setBounds(100, 50, 70, 20);
		panel.add(labName);
		txtName.setBounds(190, 50, 140, 20);
		panel.add(txtName);
		labStudentId.setBounds(100, 90, 70, 20);
		panel.add(labStudentId);
		txtStudentId.setBounds(190, 90, 140, 20);
		panel.add(txtStudentId);
		labSex.setBounds(110, 130, 60, 20);
		panel.add(labSex);
		man.setBounds(190, 130, 60, 20);
		women.setBounds(270, 130, 60, 20);
		panel.add(man);
		panel.add(women);
		group.add(man);
		group.add(women);
		labMajor.setBounds(100, 180, 70, 20);
		panel.add(labMajor);
		txtMajor.setBounds(190, 180, 140, 20);
		panel.add(txtMajor);
		labChinese.setBounds(100, 210, 70, 20);
		panel.add(labChinese);
		txtChinese.setBounds(190, 210, 140, 20);
		panel.add(txtChinese);
		labMath.setBounds(100, 240, 70, 20);
		panel.add(labMath);
		txtMath.setBounds(190, 240, 140, 20);
		panel.add(txtMath);
		labEnglish.setBounds(100, 270, 70, 20);
		panel.add(labEnglish);
		txtEnglish.setBounds(190, 270, 140, 20);
		panel.add(txtEnglish);
		btnReset.setBounds(80, 350, 90, 20);
		btnCancel.setBounds(320, 350, 90, 20);
		panel.add(btnReset);
		panel.add(btnCancel);
		if (flag == 0) {
			btnAdd.setBounds(200, 350, 90, 20);
			panel.add(btnAdd);
			add(panel);
		} else if (flag == 1) {
			btnUpdate.setBounds(200, 350, 90, 20);
			panel.add(btnUpdate);
		}
		add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnCancel)) {
			dispose();
			return;
		}
		if (e.getSource().equals(btnReset)) {
			txtName.setText("");
			txtStudentId.setText("");
			txtMajor.setText("");
			txtChinese.setText("");
			txtMath.setText("");
			txtEnglish.setText("");
			return;
		}
		Boolean flag = new Boolean(true);
		if (txtName.getText().toString().equals("")) {
			JOptionPane.showMessageDialog(null, "请输入姓名", "温馨提示",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		} else if (txtStudentId.getText().toString().equals("")) {
			JOptionPane.showMessageDialog(null, "请输入学号", "温馨提示",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		} else if (txtMajor.getText().toString().equals("")) {
			JOptionPane.showMessageDialog(null, "请输入班级", "温馨提示",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		} else if (txtChinese.getText().toString().equals("")) {
			JOptionPane.showMessageDialog(null, "请输入语文成绩", "温馨提示",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		} else if (txtMath.getText().toString().equals("")) {
			JOptionPane.showMessageDialog(null, "请输入数学成绩", "温馨提示",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		} else if (txtEnglish.getText().toString().equals("")) {
			JOptionPane.showMessageDialog(null, "请输入英语成绩", "温馨提示",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		} else if (!txtStudentId.getText().matches("\\d{10}")) {
			JOptionPane.showMessageDialog(null, "请输入正确的学号", "温馨提示",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		} else if (!txtChinese.getText().matches("\\d((\\.\\d){0,1})|([1-9]\\d((\\.\\d){0,1}))|100")) {
			JOptionPane.showMessageDialog(null, "请输入正确的语文成绩", "温馨提示",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		} else if (!txtMath.getText().matches("\\d((\\.\\d){0,1})|([1-9]\\d((\\.\\d){0,1}))|100")) {
			JOptionPane.showMessageDialog(null, "请输入正确的数学成绩", "温馨提示",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		} else if (!txtEnglish.getText().matches("\\d((\\.\\d){0,1})|([1-9]\\d((\\.\\d){0,1}))|100")) {
			JOptionPane.showMessageDialog(null, "请输入正确的英语成绩", "温馨提示",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		}
		String sex = new String();
		if (man.isSelected()) {
			sex = "男";
		} else {
			sex = "女";
		}
		Student student = new Student();
		student.setName(txtName.getText());
		student.setSex(sex);
		student.setStudentId(txtStudentId.getText());
		student.setMajor(txtMajor.getText());
		student.setChineseGrade(txtChinese.getText());
		student.setMathGrade(txtMath.getText());
		student.setEnglishGrade(txtEnglish.getText());
		if (e.getSource().equals(btnAdd)) {
			// 检查学号是否存在
			if (Query.getStudentId(txtStudentId.getText())) {
				JOptionPane.showMessageDialog(null, "学号已经存在,该学员已被录入", "温馨提示",
						JOptionPane.INFORMATION_MESSAGE);
				flag = false;
			}
			if (flag) {
				if (Updater.insertStudent(student)) {
					JOptionPane.showMessageDialog(null, "已成功添加", "温馨提示",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "添加失败", "温馨提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		if (e.getSource().equals(btnUpdate)) {
			student.setId(Integer.parseInt(id.getText()));
			if (flag) {
				if (Updater.updateStudent(student)) {
					JOptionPane.showMessageDialog(null, "修改成功", "温馨提示",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "修改失败", "温馨提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
}
