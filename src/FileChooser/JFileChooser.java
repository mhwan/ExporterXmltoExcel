package FileChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.SyncFailedException;
import java.util.ArrayList;

/**
 * Created by Mhwan on 2016. 6. 15..
 */
public class JFileChooser extends JFrame implements ActionListener {
    private JPanel rootpanel;
    private JTextField textField1;
    private JButton select;
    private JPanel checkboxpanel;
    private JButton savebutton;
    private JPanel selectpanel;
    private JPanel addpanel;
    private ArrayList<JCheckBox> boxes;
    private FileChooseListner listner;
    private boolean cansave = false;

    public JFileChooser(FileChooseListner listner) {
        super();
        this.listner = listner;
        setContentPane(rootpanel);
        pack();
        setfield();
        setVisible(true);
    }

    private void setfield() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Xml to Excel Export");

        select.addActionListener(this);
        savebutton.addActionListener(this);
        textField1.setEditable(false);
        textField1.setText("경로를 선택해주세요.");
        checkboxpanel.setLayout(new BoxLayout(checkboxpanel, BoxLayout.PAGE_AXIS));
        addpanel = new JPanel();
        addpanel.setLayout(new BoxLayout(addpanel, BoxLayout.PAGE_AXIS));
        addpanel.setBorder(BorderFactory.createEmptyBorder());
        JScrollPane scrollPane = new JScrollPane(addpanel);
        checkboxpanel.add(scrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == select) {
            javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
            chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            chooser.setDialogTitle("폴더 선택");
            chooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(selectpanel) == javax.swing.JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getAbsolutePath();
                addCheckbox(path);
                textField1.setText(path);
                addpanel.revalidate();
            }
        } else if (e.getSource() == savebutton) {
            if (!cansave)
                JOptionPane.showMessageDialog(this, "먼저 앱 경로 폴더를 선택해주세요.", "경고", JOptionPane.OK_OPTION, null);
            else {
                javax.swing.JFileChooser savechooser = new javax.swing.JFileChooser();
                savechooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                FileTypeFilter excelnewfilter = new FileTypeFilter(".xlsx", "Microsoft Excel 2007 이후버전");
                FileTypeFilter exceloldfilter = new FileTypeFilter(".xls", "Microsoft Excel 2007 이전버전");
                savechooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
                savechooser.addChoosableFileFilter(excelnewfilter);
                savechooser.addChoosableFileFilter(exceloldfilter);
                savechooser.setFileFilter(excelnewfilter);
                savechooser.setAcceptAllFileFilterUsed(false);

                int result = savechooser.showSaveDialog(this);
                if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                    File selectedFile = getFileWithExtension(savechooser.getSelectedFile(), savechooser.getFileFilter().getDescription());
                    listner.chooseActioned(getpathfromCheckbox(), selectedFile.getAbsolutePath());
                }
            }
        }
    }

    private ArrayList<String> getpathfromCheckbox(){
        ArrayList<String> pathlist = new ArrayList<String>();
        for (JCheckBox box : boxes) {
            if (box.isSelected())
                pathlist.add(box.getText());
        }

        return pathlist;
    }

    private void addCheckbox(String path) {
        System.out.println(path);
        FileFinder finder = new FileFinder(path);
        ArrayList<String> pathlist = finder.findFile();

        if (pathlist == null || pathlist.isEmpty()) {
            JOptionPane.showMessageDialog(this, "올바른 앱 경로가 아닙니다. 경로를 다시 선택해주세요.", "경고", JOptionPane.OK_OPTION);
            cansave = false;
            return;
        }

        cansave = true;
        //이미 한번 체크박스를 만들었다면 화면상에 다 없애줌
        if (boxes == null) {
            boxes = new ArrayList<JCheckBox>();
        } else {
            removeCheckboxpanel();
        }
        for (String s : pathlist) {
            JCheckBox box = new JCheckBox(s);
            box.setSelected(true);
            addpanel.add(box);
            boxes.add(box);
        }
        addpanel.revalidate();
    }

    private void removeCheckboxpanel(){
        if (boxes!= null){
            for (JCheckBox box : boxes) {
                addpanel.remove(box);
                addpanel.revalidate();
            }
            boxes.clear();
        }
    }

    private File getFileWithExtension(File selected, String description) {
        if (description.startsWith("(*.xls)"))
            return new File(selected + ".xls");
        else
            return new File(selected + ".xlsx");
    }

    public void showResult(boolean issuccess, String save_path){
        if (issuccess)
            JOptionPane.showMessageDialog(this, "성공적으로 엑셀 파일이 추출되었습니다!\n(경로 : "+save_path+")");
        else
            JOptionPane.showMessageDialog(this, "엑셀파일 추출에 실패하였습니다 ㅠㅠ");

        removeCheckboxpanel();
        textField1.setText("");
    }
    public interface FileChooseListner {
        void chooseActioned(ArrayList<String> pathlist, String savepath);
    }
}
