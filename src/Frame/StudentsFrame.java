package Frame;

import Logic.ManagementSystem;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Vector;

public class StudentsFrame extends JFrame{

    private static final String MOVE_GR = "moveGroup";
    private static final String CLEAR_GR = "clearGroup";
    private static final String INSERT_ST = "insertGroup";
    private static final String UPDATE_ST = "updateGroup";
    private static final String DELETE_ST = "deleteGroup";
    private static final String ALL_STUDENTS = "allStudents";

    ManagementSystem ms = null;
    private JList grpList;
    private JTable stdList;
    private JSpinner spYear;

    public StudentsFrame() throws Exception {

        getContentPane().setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Reports");

        JMenuItem menuItem = new JMenuItem("All students");
        menuItem.setName(ALL_STUDENTS);
        menu.add(menuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);


        JPanel top = new JPanel();

        top.setLayout(new FlowLayout(FlowLayout.LEFT));

        top.add(new JLabel("Year of study"));

        SpinnerModel sm = new SpinnerNumberModel(2006, 1990, 2100, 1);
        spYear = new JSpinner(sm);
        top.add(spYear);

        JPanel bot = new JPanel();
        bot.setLayout(new BorderLayout());

        GroupPanel left = new GroupPanel();
        left.setLayout(new BorderLayout());
        left.setBorder(new BevelBorder(BevelBorder.LOWERED));

        Vector gr;
        Vector st;

        ms = ManagementSystem.getInstance();

        gr = new Vector<>(ms.getGroups());
        st = new Vector<>(ms.getAllStudents());

        left.add(new JLabel("Groups:"), BorderLayout.NORTH);

        grpList = new JList(gr);

        left.add(new JScrollPane(grpList), BorderLayout.CENTER);

        JButton btnMvGr = new JButton("Move");
        btnMvGr.setName(MOVE_GR);
        JButton btnClGr = new JButton("Clear");
        btnClGr.setName(CLEAR_GR);

        JPanel pnlBtnGr = new JPanel();
        pnlBtnGr.setLayout(new GridLayout(1, 2));
        pnlBtnGr.add(btnClGr);
        pnlBtnGr.add(btnMvGr);
        left.add(pnlBtnGr, BorderLayout.SOUTH);

        JPanel right = new JPanel();

        right.setLayout(new BorderLayout());
        right.setBorder(new BevelBorder(BevelBorder.LOWERED));
        right.add(new JLabel("Students"), BorderLayout.NORTH);

        stdList = new JTable(1, 4);
        right.add(new JScrollPane(stdList), BorderLayout.CENTER);

        JButton btnAddSt = new JButton("Add");
        btnAddSt.setName(INSERT_ST);

        JButton btnUpdSt = new JButton("Update");
        btnUpdSt.setName(UPDATE_ST);

        JButton btnDelSt = new JButton("Delete");
        btnDelSt.setName(DELETE_ST);

        JPanel pnlBtnSt = new JPanel();
        pnlBtnSt.setLayout(new GridLayout(1, 3));
        pnlBtnSt.add(btnAddSt);
        pnlBtnSt.add(btnDelSt);
        pnlBtnSt.add(btnDelSt);
        right.add(pnlBtnSt, BorderLayout.SOUTH);

        bot.add(left, BorderLayout.WEST);
        bot.add(right, BorderLayout.CENTER);

        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(bot, BorderLayout.CENTER);

        grpList.setSelectedIndex(0);

        setBounds(100, 100, 700, 500);

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            StudentsFrame sf = null;
            try {
                sf = new StudentsFrame();
                sf.setDefaultCloseOperation(EXIT_ON_CLOSE);
                sf.setVisible(true);

            }
            catch (Exception e) {
                e.printStackTrace();
            }


        });
    }

    class GroupPanel extends JPanel {

        @Override
        public Dimension getPreferredSize() {

            return new Dimension(250, 0);
        }
    }

}
