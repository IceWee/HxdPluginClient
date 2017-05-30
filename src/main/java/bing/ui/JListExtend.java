package bing.ui;

import bing.AppUI;
import bing.Constants;
import bing.cache.VisitCache;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

/**
 * 带右键菜单的JList
 *
 * @author IceWee
 */
public class JListExtend extends JList implements MouseListener {

    private final AppUI app;
    private JPopupMenu popMenu = null; // 弹出菜单
    private JMenuItem menuItemVisit = null, menuItemRemove = null, menuItemClear = null; // 功能菜单
    private static final ImageIcon ICON_VISIT = new ImageIcon(JTextAreaExt.class.getResource(Constants.ICON_VISIT_PATH));
    private static final ImageIcon ICON_REMOVE = new ImageIcon(JTextAreaExt.class.getResource(Constants.ICON_REMOVE_PATH));
    private static final ImageIcon ICON_CLEAR = new ImageIcon(JTextAreaExt.class.getResource(Constants.ICON_CLEAR_PATH));

    public JListExtend(AppUI app) {
        this.app = app;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        this.addMouseListener(this);
        popMenu = new JPopupMenu();
        menuItemVisit = new JMenuItem(Constants.TEXT_VISIT);
        menuItemVisit.setIcon(ICON_VISIT);
        menuItemRemove = new JMenuItem(Constants.TEXT_REMOVE);
        menuItemRemove.setIcon(ICON_REMOVE);
        menuItemClear = new JMenuItem(Constants.TEXT_CLEAR);
        menuItemClear.setIcon(ICON_CLEAR);
        popMenu.add(menuItemVisit);
        popMenu.add(createSeparator());
        popMenu.add(menuItemRemove);
        popMenu.add(createSeparator());
        popMenu.add(menuItemClear);
        menuItemVisit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action(e);
            }
        });
        menuItemRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action(e);
            }
        });
        menuItemClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action(e);
            }
        });
        this.add(popMenu);
    }

    /**
     * 菜单动作
     *
     * @param e
     */
    private void action(ActionEvent e) {
        String cmd = e.getActionCommand();
        VisitCache cache = (VisitCache) this.getSelectedValue();
        if (cmd.equals(menuItemVisit.getText())) { // 访问
            app.sendGetRequest(cache.getUrl(), cache.getReq());
        } else if (cmd.equals(menuItemRemove.getText())) { // 删除
            app.removeCache(cache.getUrl());
        } else if (cmd.equals(menuItemClear.getText())) { // 清空
            app.clearCache();
        }
    }

    /**
     * 允许删除判断
     *
     * @return
     */
    public boolean allowRemove() {
        return !this.isSelectionEmpty();
    }

    /**
     * 允许清空判断
     *
     * @return
     */
    public boolean allowClear() {
        DefaultListModel<VisitCache> visitCacheModel = (DefaultListModel<VisitCache>) app.getVisitList().getModel();
        return !visitCacheModel.isEmpty();
    }

    /**
     * 允许访问判断
     *
     * @return
     */
    public boolean allowVisit() {
        return !this.isSelectionEmpty();
    }

    private JSeparator createSeparator() {
        return new JSeparator();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            menuItemVisit.setEnabled(allowVisit());
            menuItemRemove.setEnabled(allowRemove());
            menuItemClear.setEnabled(allowClear());
            popMenu.show(this, e.getX(), e.getY());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
