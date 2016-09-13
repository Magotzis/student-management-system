package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import dao.Query;
import dao.Updater;
import po.Student;
import util.ExcelUtil;

@SuppressWarnings("serial")
public class QueryStudentFrame extends JInternalFrame implements ActionListener {
	JTextField text = new JTextField();
	JComboBox<String> comboBox = new JComboBox<>();
	JScrollPane scrollPane;
	JTable table;
	DefaultTableModel defaultModel;
	JButton query = new JButton("查询");
	JButton adder = new JButton("新增");
	JButton changer = new JButton("修改");
	JButton deleter = new JButton("删除");
	JButton exporter = new JButton("导出excel");

	QueryStudentFrame() {
		initialPanel();
		initialTable(Query.queryAll("T"));
		setLayout(null);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setClosable(true);
		setTitle("查询完整信息");
	}

	/**
	 * 条件查询面板
	 */
	private void initialPanel() {
		comboBox.addItem("姓名");
		comboBox.addItem("学号");
		comboBox.addItem("班级");
		comboBox.addItem("语文成绩");
		comboBox.addItem("数学成绩");
		comboBox.addItem("英语成绩");
		comboBox.setSelectedItem("姓名");
		comboBox.setBounds(10, 10, 100, 20);
		add(comboBox);
		text.setBounds(112, 10, 100, 20);
		add(text);
		query.addActionListener(this);
		adder.addActionListener(this);
		changer.addActionListener(this);
		deleter.addActionListener(this);
		exporter.addActionListener(this);
		query.setBounds(212, 10, 60, 20);
		add(query);
		adder.setBounds(350, 350, 60, 30);
		add(adder);
		changer.setBounds(410, 350, 60, 30);
		add(changer);
		deleter.setBounds(470, 350, 60, 30);
		add(deleter);
		exporter.setBounds(530, 350, 100, 30);
		add(exporter);
	}

	/**
	 * 表格模型
	 */
	private void initialTable(List<Student> list) {
		String[] head = { "ID", "学生姓名", "学号", "性别", "班级", "语文成绩", "数学成绩",
				"英语成绩", "总成绩", "平均成绩" };
		String[][] columnName = new String[list.size()][10];
		Iterator<Student> iterator = list.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			Student student = (Student) iterator.next();
			columnName[i][0] = String.valueOf(student.getId());
			columnName[i][1] = student.getName();
			columnName[i][2] = student.getStudentId();
			columnName[i][3] = student.getSex();
			columnName[i][4] = student.getMajor();
			columnName[i][5] = student.getChineseGrade();
			columnName[i][6] = student.getMathGrade();
			columnName[i][7] = student.getEnglishGrade();
			columnName[i][8] = String.valueOf(Float
					.parseFloat(columnName[i][5])
					+ Float.parseFloat(columnName[i][6])
					+ Float.parseFloat(columnName[i][7]));
			columnName[i][9] = String.valueOf(new DecimalFormat("#.00")
					.format(Float.parseFloat(columnName[i][8]) / 3));
		}
		defaultModel = new DefaultTableModel(columnName, head);
		table = new JTable(defaultModel) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setSelectionMode(0); // 设置只能单选
		table.setRowSorter(new TableRowSorter<>(defaultModel));// 设置列排序
		table.getTableHeader().setReorderingAllowed(false);// 不可整列移动
		// 设置id列隐藏
		TableColumn column = table.getColumnModel().getColumn(0);
		column.setMinWidth(0);
		column.setMaxWidth(0);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(35, 50, 900, 300);
		add(scrollPane);
	}

	/**
	 * 动作监听器
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("查询")) {
			String str = text.getText();
			text.setText("");
			String option = (String) comboBox.getSelectedItem();
			String name = new String();
			if (option.equals("姓名")) {
				name = "name";
			} else if (option.equals("学号")) {
				name = "student_id";
			} else if (option.equals("班级")) {
				name = "major";
			} else if (option.equals("语文成绩")) {
				name = "chinese_grade";
			} else if (option.equals("数学成绩")) {
				name = "math_grade";
			} else if (option.equals("英语成绩")) {
				name = "english_grade";
			}
			// 如果没有输入查询信息，默认搜索全表
			if (str == null || str.equals("")) {
				this.remove(scrollPane);
				initialTable(Query.queryAll("T"));
			} else {
				List<Student> list = Query.queryByInfo(name, str);
				if (list.size() != 0) {
					this.remove(scrollPane);
					initialTable(Query.queryByInfo(name, str));
				} else {
					JOptionPane.showMessageDialog(null, "查询无结果,请重试");
				}
			}
		} else if (e.getActionCommand().equals("新增")) {
			InfoFrame infoFrame = new InfoFrame();
			infoFrame.addWindowListener(new WindowListener() {
				@Override
				public void windowOpened(WindowEvent e) {
				}

				@Override
				public void windowIconified(WindowEvent e) {
				}

				@Override
				public void windowDeiconified(WindowEvent e) {

				}

				@Override
				public void windowDeactivated(WindowEvent e) {

				}

				@Override
				public void windowClosing(WindowEvent e) {

				}

				@Override
				public void windowClosed(WindowEvent e) {
					remove(scrollPane);
					initialTable(Query.queryAll("T"));
				}

				@Override
				public void windowActivated(WindowEvent e) {

				}
			});
		} else if (e.getActionCommand().equals("修改")) {
			int row = table.getSelectedRow();
			Student student = new Student();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "请先选择需要更改的目标");
			} else {
				student.setId(Integer.parseInt((String) table
						.getValueAt(row, 0)));
				student.setName((String) table.getValueAt(row, 1));
				student.setStudentId((String) table.getValueAt(row, 2));
				student.setSex((String) table.getValueAt(row, 3));
				student.setMajor((String) table.getValueAt(row, 4));
				student.setChineseGrade((String) table.getValueAt(row, 5));
				student.setMathGrade((String) table.getValueAt(row, 6));
				student.setEnglishGrade((String) table.getValueAt(row, 7));
				InfoFrame infoFrame = new InfoFrame(student);
				infoFrame.addWindowListener(new WindowListener() {
					@Override
					public void windowOpened(WindowEvent e) {
					}

					@Override
					public void windowIconified(WindowEvent e) {
					}

					@Override
					public void windowDeiconified(WindowEvent e) {

					}

					@Override
					public void windowDeactivated(WindowEvent e) {

					}

					@Override
					public void windowClosing(WindowEvent e) {

					}

					@Override
					public void windowClosed(WindowEvent e) {
						remove(scrollPane);
						initialTable(Query.queryAll("T"));
					}

					@Override
					public void windowActivated(WindowEvent e) {

					}
				});
			}
		} else if (e.getActionCommand().equals("删除")) {
			int row = table.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "请先选择需要删除的目标");
			} else {
				int id = Integer.parseInt((String) table.getValueAt(row, 0));
				int i = JOptionPane.showConfirmDialog(null, "请确认是否删除该数据",
						"温馨提示", JOptionPane.YES_NO_OPTION);
				if (i == JOptionPane.YES_OPTION) {
					if (Updater.updateStudentState(id, "F")) {
						JOptionPane.showMessageDialog(null, "删除成功");
						defaultModel.removeRow(row);
					} else {
						JOptionPane.showMessageDialog(null, "删除失败");
					}
				}
			}
		} else if (e.getActionCommand().equals("导出excel")) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
			int a = fileChooser.showSaveDialog(getContentPane());
			if (a == JFileChooser.APPROVE_OPTION) {
				String url = fileChooser.getSelectedFile().getPath();
				url += "\\学生成绩信息导出表.xls";
				int row = table.getRowCount(); // 获得总行数
				int column = table.getColumnCount(); // 获得总列数
				String[] head = { "学生姓名", "学号", "性别", "班级", "语文成绩",
						"数学成绩", "英语成绩", "总成绩", "平均成绩" }; // 表头
				String[][] columns = new String[row][column - 1]; // 表数据
				// 获取行数据
				for (int i = 0; i < row; i++) {
					for (int j = 1; j < column; j++) {
						columns[i][j - 1] = table.getValueAt(i, j).toString();
					}
				}
				ExcelUtil.exportExcel(url, head, columns, 1);
			}
		}
	}
}
