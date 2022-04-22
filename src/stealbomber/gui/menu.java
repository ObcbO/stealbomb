package stealbomber.gui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class menu {
    protected final static JMenuBar menuBar = new JMenuBar();
    private final static JMenu optionMenu = new JMenu("基本");
    private final static JMenu moreMenu = new JMenu("更多");

    protected static void show() {
        optionMenu();
        moreMenu();
        menuBar.add(optionMenu);
        menuBar.add(moreMenu);
    }

    private static void optionMenu() {
        final JMenuItem chooseproper = new JMenuItem("选择配置文件");
        chooseproper.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int val = fileChooser.showOpenDialog(null);
                if (val == JFileChooser.APPROVE_OPTION) {
                    stealbomber.manage.file.start(fileChooser.getSelectedFile().toString());
                    stealbomber.manage.file.manage();
                    main.refresh();
                }
            }
        });

        final JCheckBoxMenuItem ontop = new JCheckBoxMenuItem("置顶", false);
        ontop.addItemListener((ItemListener) new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (ontop.getState()) {
                    main.jf.setAlwaysOnTop(true);
                } else {
                    main.jf.setAlwaysOnTop(false);
                }
            }
        });

        final JMenuItem exitMenu = new JMenuItem("退出");
        exitMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.jf.setVisible(false);
                System.exit(0);
            }
        });

        optionMenu.add(chooseproper);
        optionMenu.add(ontop);
        optionMenu.addSeparator();// 添加一个分割线
        optionMenu.add(exitMenu);
    }

    private static void moreMenu() {
        final JMenuItem igithub = new JMenuItem("Github地址");
        igithub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.browse(new URI("https://github.com/obcbo/stealbomber"));
                    } catch (IOException | URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showInputDialog(null, "Github地址", "https://github.com/obcbo/stealbomber");
                }
            }
        });

        final JMenuItem checkupdate = new JMenuItem("检查更新");
        checkupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder();
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(
                            "https://api.github.com/repos/obcbo/stealbomber/releases/latest").openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setReadTimeout(10000);
                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.setRequestProperty("accept", "*/*");
                    httpURLConnection.connect();
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }

                    int firsti = sb.indexOf("\"tag_name\":\"") + 12;
                    if (firsti == -1) {
                        System.out.println("没有找到字符串");
                    } else {
                        int secondi = sb.indexOf("\"", firsti);
                        System.out.println("最新版本:" + sb.substring(firsti, secondi));
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        final JMenuItem about = new JMenuItem("关于");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "StealBomber - v1.4\n作者: ObcbO", "关于",
                        JOptionPane.INFORMATION_MESSAGE,
                        null);
            }
        });

        moreMenu.add(igithub);
        moreMenu.addSeparator();
        moreMenu.add(checkupdate);
        moreMenu.add(about);
    }
}
