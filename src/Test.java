import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Test extends JFrame implements ListSelectionListener, ActionListener{

    private JList list;
    private JButton add = new JButton("Add");
    private JButton del = new JButton("Del");


    public Test() throws HeadlessException {

        Vector v = new Vector();
        v.add(1);
        v.add(2);
        v.add(3);
        v.add(4);
        v.add(5);

        list = new JList(v);
        list.setModel(new DefaultListModel());

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);
        add.addActionListener(this);
        del.addActionListener(this);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2));
        buttons.add(add);
        buttons.add(del);

       getContentPane().add(new JScrollPane(list));

       getContentPane().add(buttons, BorderLayout.SOUTH);



        setBounds(100, 100, 200, 200);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Test t = new Test();
            t.setDefaultCloseOperation(t.EXIT_ON_CLOSE);
            t.setVisible(true);
        });
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        if (!e.getValueIsAdjusting())
        System.out.println("New index " + (list.getSelectedIndex() + 1));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        DefaultListModel dlm = (DefaultListModel) list.getModel();

        JButton sender = (JButton) e.getSource();

        if (sender.getText().equals("Add")) {
            dlm.addElement(String.valueOf(dlm.getSize()));
        }
        // Проверяем имя для удаления и проверяем индекс - если он =-1,
        // значит нет выделенной строки
        if (sender.getText().equals("Del") && list.getSelectedIndex() >= 0) {
            dlm.remove(list.getSelectedIndex());
        }
    }
}
