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
	JRadioButton chinese = new JRadioButton("����", true);
	JRadioButton math = new JRadioButton("��ѧ");
	JRadioButton english = new JRadioButton("Ӣ��");
	JButton button = new JButton("��ѯͳ����Ϣ");
	JButton exporter = new JButton("����excel");

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
		setTitle("��ѯ�༶�ɼ���Ϣ");
	}

	/**
	 * ���ģ��
	 */
	private void initialTable(String[][] columnName) {
		String[] head = { "�༶", "ѧ������", "��������", "������", "��������", "������", "����������",
				"��������" };
		defaultModel = new DefaultTableModel(columnName, head);
		table = new JTable(defaultModel) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setSelectionMode(0); // ����ֻ�ܵ�ѡ
		table.setRowSorter(new TableRowSorter<>(defaultModel));// ����������
		table.getTableHeader().setReorderingAllowed(false);// ���������ƶ�
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
		// �༶����
		Iterator<String> iterator = classes.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			columnName[i][0] = (String) iterator.next();
		}
		// ��ȡ������
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
		// ��ȡ��������
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
		// ��ȡ������
		for (int i = 0; i < classes.size(); i++) {
			columnName[i][3] = String.valueOf(new java.text.DecimalFormat(
					"0.00").format(Double.parseDouble(columnName[i][2])
					/ Double.parseDouble(columnName[i][1])));
		}
		// ��ȡ��������
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
		// ��ȡ������
		for (int i = 0; i < classes.size(); i++) {
			columnName[i][5] = String.valueOf(new java.text.DecimalFormat(
					"0.00").format(Double.parseDouble(columnName[i][4])
					/ Double.parseDouble(columnName[i][1])));
		}
		// ��ȡ����������
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
		// ��ȡ��������
		for (int i = 0; i < classes.size(); i++) {
			columnName[i][7] = String.valueOf(new java.text.DecimalFormat(
					"0.00").format(Double.parseDouble(columnName[i][6])
					/ Double.parseDouble(columnName[i][1])));
		}
		if (e.getActionCommand().equals("��ѯͳ����Ϣ")) {
			if (scrollPane != null) {
				this.remove(scrollPane);
			}
			initialTable(columnName);
		} else if (e.getActionCommand().equals("����excel")) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
			int a = fileChooser.showSaveDialog(getContentPane());
			if (a == JFileChooser.APPROVE_OPTION) {
				String url = fileChooser.getSelectedFile().getPath();
				if (chinese.isSelected()) {
					url += "\\�༶���ĳɼ�ͳ����Ϣ��.xls";
				} else if (math.isSelected()) {
					url += "\\�༶��ѧ�ɼ�ͳ����Ϣ��.xls";
				} else {
					url += "\\�༶Ӣ��ɼ�ͳ����Ϣ��.xls";
				}
				String[] head = { "�༶", "ѧ������", "��������", "������", "��������", "������",
						"����������", "��������" }; // ��ͷ
				ExcelUtil.exportExcel(url, head, columnName, 1);
			}
		}
	}
}
