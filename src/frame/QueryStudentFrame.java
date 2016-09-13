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
	JButton query = new JButton("��ѯ");
	JButton adder = new JButton("����");
	JButton changer = new JButton("�޸�");
	JButton deleter = new JButton("ɾ��");
	JButton exporter = new JButton("����excel");

	QueryStudentFrame() {
		initialPanel();
		initialTable(Query.queryAll("T"));
		setLayout(null);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setClosable(true);
		setTitle("��ѯ������Ϣ");
	}

	/**
	 * ������ѯ���
	 */
	private void initialPanel() {
		comboBox.addItem("����");
		comboBox.addItem("ѧ��");
		comboBox.addItem("�༶");
		comboBox.addItem("���ĳɼ�");
		comboBox.addItem("��ѧ�ɼ�");
		comboBox.addItem("Ӣ��ɼ�");
		comboBox.setSelectedItem("����");
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

	/**
	 * ����������
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("��ѯ")) {
			String str = text.getText();
			text.setText("");
			String option = (String) comboBox.getSelectedItem();
			String name = new String();
			if (option.equals("����")) {
				name = "name";
			} else if (option.equals("ѧ��")) {
				name = "student_id";
			} else if (option.equals("�༶")) {
				name = "major";
			} else if (option.equals("���ĳɼ�")) {
				name = "chinese_grade";
			} else if (option.equals("��ѧ�ɼ�")) {
				name = "math_grade";
			} else if (option.equals("Ӣ��ɼ�")) {
				name = "english_grade";
			}
			// ���û�������ѯ��Ϣ��Ĭ������ȫ��
			if (str == null || str.equals("")) {
				this.remove(scrollPane);
				initialTable(Query.queryAll("T"));
			} else {
				List<Student> list = Query.queryByInfo(name, str);
				if (list.size() != 0) {
					this.remove(scrollPane);
					initialTable(Query.queryByInfo(name, str));
				} else {
					JOptionPane.showMessageDialog(null, "��ѯ�޽��,������");
				}
			}
		} else if (e.getActionCommand().equals("����")) {
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
		} else if (e.getActionCommand().equals("�޸�")) {
			int row = table.getSelectedRow();
			Student student = new Student();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "����ѡ����Ҫ���ĵ�Ŀ��");
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
		} else if (e.getActionCommand().equals("ɾ��")) {
			int row = table.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "����ѡ����Ҫɾ����Ŀ��");
			} else {
				int id = Integer.parseInt((String) table.getValueAt(row, 0));
				int i = JOptionPane.showConfirmDialog(null, "��ȷ���Ƿ�ɾ��������",
						"��ܰ��ʾ", JOptionPane.YES_NO_OPTION);
				if (i == JOptionPane.YES_OPTION) {
					if (Updater.updateStudentState(id, "F")) {
						JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
						defaultModel.removeRow(row);
					} else {
						JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
					}
				}
			}
		} else if (e.getActionCommand().equals("����excel")) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
			int a = fileChooser.showSaveDialog(getContentPane());
			if (a == JFileChooser.APPROVE_OPTION) {
				String url = fileChooser.getSelectedFile().getPath();
				url += "\\ѧ���ɼ���Ϣ������.xls";
				int row = table.getRowCount(); // ���������
				int column = table.getColumnCount(); // ���������
				String[] head = { "ѧ������", "ѧ��", "�Ա�", "�༶", "���ĳɼ�",
						"��ѧ�ɼ�", "Ӣ��ɼ�", "�ܳɼ�", "ƽ���ɼ�" }; // ��ͷ
				String[][] columns = new String[row][column - 1]; // ������
				// ��ȡ������
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
