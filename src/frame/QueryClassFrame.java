package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import util.ExcelUtil;
import dao.Query;

@SuppressWarnings("serial")
public class QueryClassFrame extends JInternalFrame implements ActionListener {
	JScrollPane scrollPane;
	JTable table;
	DefaultTableModel defaultModel;
	ButtonGroup group = new ButtonGroup();
	JRadioButton chinese = new JRadioButton("语文", true);
	JRadioButton math = new JRadioButton("数学");
	JRadioButton english = new JRadioButton("英语");
	JButton button = new JButton("查询统计信息");
	JButton exporter = new JButton("导出excel");

	QueryClassFrame() {
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
		button.setBounds(190, 10, 120, 30);
		exporter.setBounds(410, 350, 100, 30);
		button.addActionListener(this);
		exporter.addActionListener(this);
		add(chinese);
		add(math);
		add(english);
		add(button);
		add(exporter);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setClosable(true);
		setTitle("查询班级成绩信息");
	}

	/**
	 * 表格模型
	 */
	private void initialTable(String[][] columnName) {
		String[] head = { "班级", "学生人数", "优秀人数", "优秀率", "及格人数", "及格率", "不及格人数",
				"不及格率" };
		defaultModel = new DefaultTableModel(columnName, head);
		table = new JTable(defaultModel) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setSelectionMode(0); // 设置只能单选
		table.setRowSorter(new TableRowSorter<>(defaultModel));// 设置列排序
		table.getTableHeader().setReorderingAllowed(false);// 不可整列移动
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
		List<String> classes = Query.getClassList();
		String[][] columnName = new String[classes.size()][8];
		// 班级名称
		Iterator<String> iterator = classes.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			columnName[i][0] = (String) iterator.next();
		}
		// 获取总人数
		Map<String, Integer> map = Query.getClassInfo(course, 3);
		Set<String> set = map.keySet();
		Collection<Integer> collection = map.values();
		iterator = set.iterator();
		Iterator<Integer> it = collection.iterator();
		while (iterator.hasNext()) {
			String major = iterator.next();
			Integer j = it.next();
			for (int i = 0; i < classes.size(); i++) {
				if (columnName[i][0].equals(major)) {
					columnName[i][1] = String.valueOf(j);
					break;
				}
			}
		}
		// 获取优秀人数
		map = Query.getClassInfo(course, 0);
		set = map.keySet();
		collection = map.values();
		iterator = set.iterator();
		it = collection.iterator();
		for (int i = 0; i < classes.size(); i++) {
			columnName[i][2] = "0";
		}
		while (iterator.hasNext()) {
			String major = iterator.next();
			Integer j = it.next();
			for (int i = 0; i < classes.size(); i++) {
				if (columnName[i][0].equals(major)) {
					columnName[i][2] = String.valueOf(j);
					break;
				}
			}
		}
		// 获取优秀率
		for (int i = 0; i < classes.size(); i++) {
			columnName[i][3] = String.valueOf(new java.text.DecimalFormat(
					"0.00").format(Double.parseDouble(columnName[i][2])
					/ Double.parseDouble(columnName[i][1])));
		}
		// 获取及格人数
		map = Query.getClassInfo(course, 1);
		set = map.keySet();
		collection = map.values();
		iterator = set.iterator();
		it = collection.iterator();
		for (int i = 0; i < classes.size(); i++) {
			columnName[i][4] = "0";
		}
		while (iterator.hasNext()) {
			String major = iterator.next();
			Integer j = it.next();
			for (int i = 0; i < classes.size(); i++) {
				if (columnName[i][0].equals(major)) {
					columnName[i][4] = String.valueOf(j);
					break;
				}
			}
		}
		// 获取及格率
		for (int i = 0; i < classes.size(); i++) {
			columnName[i][5] = String.valueOf(new java.text.DecimalFormat(
					"0.00").format(Double.parseDouble(columnName[i][4])
					/ Double.parseDouble(columnName[i][1])));
		}
		// 获取不及格人数
		map = Query.getClassInfo(course, 2);
		set = map.keySet();
		collection = map.values();
		iterator = set.iterator();
		it = collection.iterator();
		for (int i = 0; i < classes.size(); i++) {
			columnName[i][6] = "0";
		}
		while (iterator.hasNext()) {
			String major = iterator.next();
			Integer j = it.next();
			for (int i = 0; i < classes.size(); i++) {
				if (columnName[i][0].equals(major)) {
					columnName[i][6] = String.valueOf(j);
					break;
				}
			}
		}
		// 获取不及格率
		for (int i = 0; i < classes.size(); i++) {
			columnName[i][7] = String.valueOf(new java.text.DecimalFormat(
					"0.00").format(Double.parseDouble(columnName[i][6])
					/ Double.parseDouble(columnName[i][1])));
		}
		if (e.getActionCommand().equals("查询统计信息")) {
			if (scrollPane != null) {
				this.remove(scrollPane);
			}
			initialTable(columnName);
		} else if (e.getActionCommand().equals("导出excel")) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
			int a = fileChooser.showSaveDialog(getContentPane());
			if (a == JFileChooser.APPROVE_OPTION) {
				String url = fileChooser.getSelectedFile().getPath();
				if (chinese.isSelected()) {
					url += "\\班级语文成绩统计信息表.xls";
				} else if (math.isSelected()) {
					url += "\\班级数学成绩统计信息表.xls";
				} else {
					url += "\\班级英语成绩统计信息表.xls";
				}
				String[] head = { "班级", "学生人数", "优秀人数", "优秀率", "及格人数", "及格率",
						"不及格人数", "不及格率" }; // 表头
				ExcelUtil.exportExcel(url, head, columnName, 1);
			}
		}
	}
}
