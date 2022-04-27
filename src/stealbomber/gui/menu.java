package stealbomber.gui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class menu {
    protected final static JMenuBar menuBar = new JMenuBar();

    protected static void show() {
        menuBar.add(basicMenu());
        menuBar.add(optionMenu());
        menuBar.add(moreMenu());
    }

    private static JMenu basicMenu() {
        final JMenu basicMenu = new JMenu("基本");
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

        basicMenu.add(chooseproper);
        basicMenu.add(ontop);
        basicMenu.addSeparator();// 添加一个分割线
        basicMenu.add(exitMenu);
        return basicMenu;
    }

    private static JMenu optionMenu() {
        final JMenu optionMenu = new JMenu("选项");
        final JCheckBoxMenuItem genoutput = new JCheckBoxMenuItem("生成输出", stealbomber.manage.file.genoutput);
        genoutput.addItemListener((ItemListener) new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (genoutput.getState()) {
                    stealbomber.manage.file.genoutput = true;
                } else {
                    stealbomber.manage.file.genoutput = false;
                }
            }
        });

        final JCheckBoxMenuItem proxy = new JCheckBoxMenuItem("代理", stealbomber.manage.file.proxyswitch);
        proxy.addItemListener((ItemListener) new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (proxy.getState()) {
                    stealbomber.manage.file.proxyswitch = true;
                } else {
                    stealbomber.manage.file.proxyswitch = false;
                }
            }
        });

        optionMenu.add(genoutput);
        optionMenu.add(proxy);
        return optionMenu;
    }

    private static JMenu moreMenu() {
        final JMenu moreMenu = new JMenu("更多");
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
                stealbomber.manage.thread.executPool.execute(new stealbomber.manage.update());
            }
        });

        final JMenuItem about = new JMenuItem("关于");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "StealBomber - v" + stealbomber.App.version + "\n作者: ObcbO", "关于",
                        JOptionPane.INFORMATION_MESSAGE,
                        null);
            }
        });

        moreMenu.add(igithub);
        moreMenu.addSeparator();
        moreMenu.add(checkupdate);
        moreMenu.add(about);
        return moreMenu;
    }
}
