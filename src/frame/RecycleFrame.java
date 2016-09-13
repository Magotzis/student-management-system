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
	JButton recycler = new JButton("�ָ�");
	JButton deleter = new JButton("����ɾ��");

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
		setTitle("��ɾ��Ϣ�ָ�");
	}

	/**
	 * ���ģ��
	 */
	private void initialTable(List<Student> list) {
		String[] head = { "ID", "ѧ������", "ѧ��", "�Ա�", "�༶", "���ĳɼ�", "��ѧ�ɼ�",
				"Ӣ��ɼ�", "�ܳɼ�", "ƽ���ɼ�" };
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
		if (e.getActionCommand().equals("�ָ�")) {
			int row = table.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "����ѡ����Ҫ�ָ�������");
			} else {
				int id = Integer.parseInt((String) table.getValueAt(row, 0));
				int i = JOptionPane.showConfirmDialog(null, "��ȷ���Ƿ�ָ�������",
						"��ܰ��ʾ", JOptionPane.YES_NO_OPTION);
				if (i == JOptionPane.YES_OPTION) {
					if (Updater.updateStudentState(id, "T")) {
						JOptionPane.showMessageDialog(null, "�ָ��ɹ�");
						defaultModel.removeRow(row);
					} else {
						JOptionPane.showMessageDialog(null, "�ָ�ʧ��");
					}
				}
			}
		}
		if (e.getActionCommand().equals("����ɾ��")) {
			int row = table.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "����ѡ����Ҫ����ɾ��������");
			} else {
				int id = Integer.parseInt((String) table.getValueAt(row, 0));
				int i = JOptionPane.showConfirmDialog(null, "��ȷ���Ƿ񳹵�ɾ��������",
						"��ܰ��ʾ", JOptionPane.YES_NO_OPTION);
				if (i == JOptionPane.YES_OPTION) {
					if (Updater.deleteStudent(id)) {
						JOptionPane.showMessageDialog(null, "����ɾ���ɹ�");
						defaultModel.removeRow(row);
					} else {
						JOptionPane.showMessageDialog(null, "����ɾ��ʧ��");
					}
				}
			}
		}
	}

}
