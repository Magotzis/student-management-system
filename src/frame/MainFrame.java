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
	JMenu adminMenu = new JMenu("系统管理");
	JMenu dataMenu = new JMenu("数据操作");
	JMenu queryMenu = new JMenu("数据查询");
	JMenu importMenu = new JMenu("数据导入");
	JMenuItem help = new JMenuItem("关于");
	JMenuItem admin_recovery = new JMenuItem("误删恢复");
	JMenuItem download = new JMenuItem("下载excel模板");
	JMenuItem data_import = new JMenuItem("excel导入数据");
	JMenuItem query_all = new JMenuItem("查询学生信息");
	JMenuItem query_course = new JMenuItem("查询课程信息");
	JMenuItem query_class = new JMenuItem("查询班级信息");
	JPanel bottom = new JPanel();
	JDesktopPane desktop = new JDesktopPane();
	JLabel backLabel = new JLabel();

	MainFrame() {
		initalAction();
		screen();
		setTitle("学生成绩管理系统");
		setSize(1000, 600);
		setResizable(false); // 不可调整大小
		setLocationRelativeTo(null); // 居中显示窗口
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	/**
	 * 注册监听动作
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
	 * 注册菜单栏
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
	 * 监听动作
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
				String head[] = { "姓名", "学号", "性别", "班级", "语文成绩", "数学成绩",
						"英语成绩" };
				ExcelUtil.exportExcel(url, head, null, 0);
			}
		} else if (menuItem.equals(data_import)) {
			JFileChooser fileChooser = new JFileChooser();
			// 使用文件过滤器
			FileFilter filter = new FileNameExtensionFilter("excel文件(XLS)",
					"XLS");
			fileChooser.setFileFilter(filter);
			int i = fileChooser.showOpenDialog(getContentPane());
			if (i == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				try {
					InputStream is = new FileInputStream(selectedFile);
					ExcelUtil.importExcel(is);
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "打开文件失败", "温馨提示",
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
			String str = "程序名称:学生成绩管理系统\n设计者:邓怡祈 \n\n关于本系统:\n"
					+ "  本系统已经满足了基本的操作需求。\n  希望您能用的順利。"
					+"\n                    谢谢使用";

			JOptionPane.showMessageDialog(null, str, "关于", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 底部状态栏时间
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
				// 转换日期显示格式
				SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
				varTime.setText("现在时间：" + df.format(new Date(timemillis)));
			}
		});
		timeAction.start();
	}
	
	public static void main(String[] args) {
		new MainFrame(); 
	}

}
