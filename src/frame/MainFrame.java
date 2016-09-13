package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import util.ExcelUtil;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {
	JMenuBar menuBar = new JMenuBar();
	JMenu adminMenu = new JMenu("ϵͳ����");
	JMenu dataMenu = new JMenu("���ݲ���");
	JMenu queryMenu = new JMenu("���ݲ�ѯ");
	JMenu importMenu = new JMenu("���ݵ���");
	JMenuItem help = new JMenuItem("����");
	JMenuItem admin_recovery = new JMenuItem("��ɾ�ָ�");
	JMenuItem download = new JMenuItem("����excelģ��");
	JMenuItem data_import = new JMenuItem("excel��������");
	JMenuItem query_all = new JMenuItem("��ѯѧ����Ϣ");
	JMenuItem query_course = new JMenuItem("��ѯ�γ���Ϣ");
	JMenuItem query_class = new JMenuItem("��ѯ�༶��Ϣ");
	JPanel bottom = new JPanel();
	JDesktopPane desktop = new JDesktopPane();
	JLabel backLabel = new JLabel();

	MainFrame() {
		initalAction();
		screen();
		setTitle("ѧ���ɼ�����ϵͳ");
		setSize(1000, 600);
		setResizable(false); // ���ɵ�����С
		setLocationRelativeTo(null); // ������ʾ����
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	/**
	 * ע���������
	 */
	private void initalAction() {
		admin_recovery.addActionListener(this);
		download.addActionListener(this);
		data_import.addActionListener(this);
		query_all.addActionListener(this);
		query_course.addActionListener(this);
		query_class.addActionListener(this);
		help.addActionListener(this);
	}

	/**
	 * ע��˵���
	 */
	private void screen() {
		importMenu.add(download);
		importMenu.add(data_import);
		adminMenu.add(admin_recovery);
		dataMenu.add(importMenu);
		queryMenu.add(query_all);
		queryMenu.add(query_course);
		queryMenu.add(query_class);
		menuBar.add(adminMenu);
		menuBar.add(dataMenu);
		menuBar.add(queryMenu);
		menuBar.add(help);
		setJMenuBar(menuBar);
		desktop.setBounds(0, 0, 1000, 520);
		URL resource = this.getClass().getResource("../image/main.jpg");
		ImageIcon icon = new ImageIcon(resource);
		backLabel.setIcon(icon);
		backLabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
		desktop.add(backLabel, Integer.MIN_VALUE);
		add(desktop);
		bottom = Time();
		add(bottom, BorderLayout.SOUTH);
	}

	/**
	 * ��������
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem menuItem = (JMenuItem) e.getSource();
		if (menuItem.equals(admin_recovery)) {
			JInternalFrame internalFrame = new RecycleFrame();
			internalFrame.setVisible(true);
			internalFrame.setBounds(0, 0, 1000, 500);
			desktop.add(internalFrame, 0);
		} else if (menuItem.equals(download)) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
			int i = fileChooser.showSaveDialog(getContentPane());
			if (i == JFileChooser.APPROVE_OPTION) {
				String url = fileChooser.getSelectedFile().getPath();
				String head[] = { "����", "ѧ��", "�Ա�", "�༶", "���ĳɼ�", "��ѧ�ɼ�",
						"Ӣ��ɼ�" };
				ExcelUtil.exportExcel(url, head, null, 0);
			}
		} else if (menuItem.equals(data_import)) {
			JFileChooser fileChooser = new JFileChooser();
			// ʹ���ļ�������
			FileFilter filter = new FileNameExtensionFilter("excel�ļ�(XLS)",
					"XLS");
			fileChooser.setFileFilter(filter);
			int i = fileChooser.showOpenDialog(getContentPane());
			if (i == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				try {
					InputStream is = new FileInputStream(selectedFile);
					ExcelUtil.importExcel(is);
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "���ļ�ʧ��", "��ܰ��ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		} else if (menuItem.equals(query_all)) {
			JInternalFrame internalFrame = new QueryStudentFrame();
			internalFrame.setVisible(true);
			internalFrame.setBounds(0, 0, 1000, 500);
			desktop.add(internalFrame, 0);
		} else if (menuItem.equals(query_course)) {
			JInternalFrame internalFrame = new QueryCourseFrame();
			internalFrame.setVisible(true);
			internalFrame.setBounds(0, 0, 1000, 500);
			desktop.add(internalFrame, 0);
		} else if (menuItem.equals(query_class)) {
			JInternalFrame internalFrame = new QueryClassFrame();
			internalFrame.setVisible(true);
			internalFrame.setBounds(0, 0, 1000, 500);
			desktop.add(internalFrame, 0);
		} else if (menuItem.equals(help)) {
			String str = "��������:ѧ���ɼ�����ϵͳ\n�����:������ \n\n���ڱ�ϵͳ:\n"
					+ "  ��ϵͳ�Ѿ������˻����Ĳ�������\n  ϣ�������õ������"
					+"\n                    ллʹ��";

			JOptionPane.showMessageDialog(null, str, "����", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * �ײ�״̬��ʱ��
	 * 
	 * @return
	 */
	public JPanel Time() {
		JPanel panel = new JPanel();
		JTextField time = new JTextField(11);
		time.setEditable(false);
		time.setBorder(new LineBorder(Color.WHITE));
		this.getContentPane().add(time);
		panel.add(time);
		this.setTimer(time);
		return panel;
	}

	private void setTimer(JTextField time) {
		final JTextField varTime = time;
		Timer timeAction = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long timemillis = System.currentTimeMillis();
				// ת��������ʾ��ʽ
				SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
				varTime.setText("����ʱ�䣺" + df.format(new Date(timemillis)));
			}
		});
		timeAction.start();
	}
	
	public static void main(String[] args) {
		new MainFrame(); 
	}

}
