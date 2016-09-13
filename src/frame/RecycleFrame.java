package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import dao.Query;
import dao.Updater;
import po.Student;

@SuppressWarnings("serial")
public class RecycleFrame extends JInternalFrame implements ActionListener {
	JScrollPane scrollPane;
	JTable table;
	DefaultTableModel defaultModel;
	JButton recycler = new JButton("恢复");
	JButton deleter = new JButton("彻底删除");

	RecycleFrame() {
		setLayout(null);
		recycler.addActionListener(this);
		deleter.addActionListener(this);
		recycler.setBounds(390, 350, 60, 30);
		add(recycler);
		deleter.setBounds(450, 350, 90, 30);
		add(deleter);
		initialTable(Query.queryAll("F"));
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setClosable(true);
		setTitle("误删信息恢复");
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
			columnName[i][8] = String.valueOf(Float.parseFloat(student
					.getChineseGrade())
					+ Float.parseFloat(student.getMathGrade())
					+ Float.parseFloat(student.getEnglishGrade()));
			columnName[i][9] = String.valueOf(new java.text.DecimalFormat(
					"#.00").format(Float.parseFloat(columnName[i][8]) / 3));
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("恢复")) {
			int row = table.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "请先选择需要恢复的数据");
			} else {
				int id = Integer.parseInt((String) table.getValueAt(row, 0));
				int i = JOptionPane.showConfirmDialog(null, "请确认是否恢复该数据",
						"温馨提示", JOptionPane.YES_NO_OPTION);
				if (i == JOptionPane.YES_OPTION) {
					if (Updater.updateStudentState(id, "T")) {
						JOptionPane.showMessageDialog(null, "恢复成功");
						defaultModel.removeRow(row);
					} else {
						JOptionPane.showMessageDialog(null, "恢复失败");
					}
				}
			}
		}
		if (e.getActionCommand().equals("彻底删除")) {
			int row = table.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "请先选择需要彻底删除的数据");
			} else {
				int id = Integer.parseInt((String) table.getValueAt(row, 0));
				int i = JOptionPane.showConfirmDialog(null, "请确认是否彻底删除该数据",
						"温馨提示", JOptionPane.YES_NO_OPTION);
				if (i == JOptionPane.YES_OPTION) {
					if (Updater.deleteStudent(id)) {
						JOptionPane.showMessageDialog(null, "彻底删除成功");
						defaultModel.removeRow(row);
					} else {
						JOptionPane.showMessageDialog(null, "彻底删除失败");
					}
				}
			}
		}
	}

}
