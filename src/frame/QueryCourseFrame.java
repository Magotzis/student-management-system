package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import dao.Query;
import po.Student;

@SuppressWarnings("serial")
public class QueryCourseFrame extends JInternalFrame implements ActionListener {
	JScrollPane scrollPane;
	JTable table;
	DefaultTableModel defaultModel;
	ButtonGroup group = new ButtonGroup();
	JRadioButton chinese = new JRadioButton("����", true);
	JRadioButton math = new JRadioButton("��ѧ");
	JRadioButton english = new JRadioButton("Ӣ��");
	JButton btnMax = new JButton("��ѯ��߷�");
	JButton btneMin = new JButton("��ѯ��ͷ�");

	public QueryCourseFrame() {
		initUI();
	}

	void initUI() {
		setLayout(null);
		group.add(chinese);
		group.add(math);
		group.add(english);
		chinese.setBounds(10, 15, 60, 20);
		math.setBounds(70, 15, 60, 20);
		english.setBounds(130, 15, 60, 20);
		btnMax.setBounds(190, 10, 100, 30);
		btneMin.setBounds(290, 10, 100, 30);
		btnMax.addActionListener(this);
		btneMin.addActionListener(this);
		add(chinese);
		add(math);
		add(english);
		add(btnMax);
		add(btneMin);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setClosable(true);
		setTitle("��ѯ�γ���Ϣ");
	}

	/**
	 * ���ģ��
	 */
	private void initialTable(List<Student> list, String course) {
		String[] head = { "ID", "ѧ������", "ѧ��", "�Ա�", "�༶", "" };
		if (course.equals("chinese_grade")) {
			head[5] = "���ĳɼ�";
		} else if (course.equals("math_grade")) {
			head[5] = "��ѧ�ɼ�";
		} else if (course.equals("english_grade")) {
			head[5] = "Ӣ��ɼ�";
		}
		String[][] columnName = new String[list.size()][6];
		Iterator<Student> iterator = list.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			Student student = (Student) iterator.next();
			columnName[i][0] = String.valueOf(student.getId());
			columnName[i][1] = student.getName();
			columnName[i][2] = student.getStudentId();
			columnName[i][3] = student.getSex();
			columnName[i][4] = student.getMajor();
			if (course.equals("chinese_grade")) {
				columnName[i][5] = student.getChineseGrade();
			} else if (course.equals("math_grade")) {
				columnName[i][5] = student.getMathGrade();
			} else if (course.equals("english_grade")) {
				columnName[i][5] = student.getEnglishGrade();
			}
		}
		defaultModel = new DefaultTableModel(columnName, head);
		table = new JTable(defaultModel) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setSelectionMode(0); // ����ֻ�ܵ�ѡ
		table.setRowSorter(new TableRowSorter<>(defaultModel));// ����������
		table.getTableHeader().setReorderingAllowed(false);// ���������ƶ�
		// ����id������
		TableColumn column = table.getColumnModel().getColumn(0);
		column.setMinWidth(0);
		column.setMaxWidth(0);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(35, 50, 900, 300);
		add(scrollPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String course = new String();
		if (chinese.isSelected()) {
			course = "chinese_grade";
		} else if (math.isSelected()) {
			course = "math_grade";
		} else {
			course = "english_grade";
		}
		int index = 0;
		if (e.getActionCommand().equals("��ѯ��߷�")) {
			index = 0;
		} else if (e.getActionCommand().equals("��ѯ��ͷ�")) {
			index = 1;
		}
		List<Student> list = Query.getMaxOrMinGrade(course, index);
		if (scrollPane != null) {
			this.remove(scrollPane);
		}
		initialTable(list, course);
	}

}
