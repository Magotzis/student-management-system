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

	JLabel label = new JLabel("��ӻ�����Ϣ", JLabel.CENTER);
	JLabel labName = new JLabel("������", JLabel.CENTER);
	JLabel labStudentId = new JLabel("ѧ�ţ�", JLabel.CENTER);
	JLabel labSex = new JLabel("�Ա�", JLabel.CENTER);
	JLabel labMajor = new JLabel("�༶��", JLabel.CENTER);
	JLabel labChinese = new JLabel("���ĳɼ���", JLabel.CENTER);
	JLabel labMath = new JLabel("��ѧ�ɼ���", JLabel.CENTER);
	JLabel labEnglish = new JLabel("Ӣ��ɼ���", JLabel.CENTER);
	JTextField id = new JTextField(20);
	JTextField txtName = new JTextField(20);
	JTextField txtStudentId = new JTextField(18);
	JTextField txtMajor = new JTextField(20);
	JTextField txtChinese = new JTextField(20);
	JTextField txtMath = new JTextField(20);
	JTextField txtEnglish = new JTextField(20);
	ButtonGroup group = new ButtonGroup();
	JRadioButton man = new JRadioButton("��");
	JRadioButton women = new JRadioButton("Ů");
	JButton btnAdd = new JButton("���");
	JButton btnUpdate = new JButton("����");
	JButton btnCancel = new JButton("����");
	JButton btnReset = new JButton("����");
	JPanel panel = new JPanel(); // ����������

	/**
	 * ���봰��ʵ����
	 */
	InfoFrame() {
		man.setSelected(true);
		initialPanel(0);
		setTitle("���ѧ����Ϣ");
		setResizable(false);
		setSize(550, 450);
		setVisible(true);
		setLocation(300, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * ���´���ʵ����
	 * 
	 * @param student
	 *            ѡ�е�ѧ����Ϣ
	 */
	InfoFrame(Student student) {
		label.setText("���»�����Ϣ");
		id.setText(String.valueOf(student.getId()));
		txtName.setText(student.getName());
		txtMajor.setText(student.getMajor());
		txtStudentId.setText(student.getStudentId());
		txtChinese.setText(student.getChineseGrade());
		txtMath.setText(student.getMathGrade());
		txtEnglish.setText(student.getEnglishGrade());
		if (student.getSex().equals("Ů")) {
			women.setSelected(true);
		} else {
			man.setSelected(true);
		}
		initialPanel(1);
		setTitle("����ѧ����Ϣ");
		setResizable(false);
		setSize(550, 450);
		setVisible(true);
		setLocation(300, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * ��ʼ�����
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
			JOptionPane.showMessageDialog(null, "����������", "��ܰ��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		} else if (txtStudentId.getText().toString().equals("")) {
			JOptionPane.showMessageDialog(null, "������ѧ��", "��ܰ��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		} else if (txtMajor.getText().toString().equals("")) {
			JOptionPane.showMessageDialog(null, "������༶", "��ܰ��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		} else if (txtChinese.getText().toString().equals("")) {
			JOptionPane.showMessageDialog(null, "���������ĳɼ�", "��ܰ��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		} else if (txtMath.getText().toString().equals("")) {
			JOptionPane.showMessageDialog(null, "��������ѧ�ɼ�", "��ܰ��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		} else if (txtEnglish.getText().toString().equals("")) {
			JOptionPane.showMessageDialog(null, "������Ӣ��ɼ�", "��ܰ��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		} else if (!txtStudentId.getText().matches("\\d{10}")) {
			JOptionPane.showMessageDialog(null, "��������ȷ��ѧ��", "��ܰ��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		} else if (!txtChinese.getText().matches("\\d((\\.\\d){0,1})|([1-9]\\d((\\.\\d){0,1}))|100")) {
			JOptionPane.showMessageDialog(null, "��������ȷ�����ĳɼ�", "��ܰ��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		} else if (!txtMath.getText().matches("\\d((\\.\\d){0,1})|([1-9]\\d((\\.\\d){0,1}))|100")) {
			JOptionPane.showMessageDialog(null, "��������ȷ����ѧ�ɼ�", "��ܰ��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		} else if (!txtEnglish.getText().matches("\\d((\\.\\d){0,1})|([1-9]\\d((\\.\\d){0,1}))|100")) {
			JOptionPane.showMessageDialog(null, "��������ȷ��Ӣ��ɼ�", "��ܰ��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			flag = false;
		}
		String sex = new String();
		if (man.isSelected()) {
			sex = "��";
		} else {
			sex = "Ů";
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
			// ���ѧ���Ƿ����
			if (Query.getStudentId(txtStudentId.getText())) {
				JOptionPane.showMessageDialog(null, "ѧ���Ѿ�����,��ѧԱ�ѱ�¼��", "��ܰ��ʾ",
						JOptionPane.INFORMATION_MESSAGE);
				flag = false;
			}
			if (flag) {
				if (Updater.insertStudent(student)) {
					JOptionPane.showMessageDialog(null, "�ѳɹ����", "��ܰ��ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "���ʧ��", "��ܰ��ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		if (e.getSource().equals(btnUpdate)) {
			student.setId(Integer.parseInt(id.getText()));
			if (flag) {
				if (Updater.updateStudent(student)) {
					JOptionPane.showMessageDialog(null, "�޸ĳɹ�", "��ܰ��ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "�޸�ʧ��", "��ܰ��ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
}
