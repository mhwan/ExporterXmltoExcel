package Main;

import FileChooser.*;
import FileChooser.JFileChooser;
import FileManage.FileInputOutput;
import FileManage.FileSupporter;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Mhwan on 2016. 6. 12..
 */
public class ExportExcelMain{
    private static ArrayList<String> path_list;
    private static String save_path;
    private static JFileChooser chooser;
    public static void main(String[] args){
        chooser = new JFileChooser(new JFileChooser.FileChooseListner() {
            @Override
            public void chooseActioned(ArrayList<String> pathlist, String savepath) {
                path_list = pathlist;
                save_path = savepath;

                boolean result = work();
                chooser.showResult(result, save_path);
            }
        });
    }

    private static boolean work(){
        FileInputOutput io = new FileInputOutput();
        FileSupporter supporter = new FileSupporter();

        //xml파일에서 읽어온 파일 리스트들
        ArrayList<ArrayList<String>> languagelist = new ArrayList<ArrayList<String>>();
        for (int i=0; i<path_list.size(); i++)
            languagelist.add(supporter.stringSpliter(io.readXmlFile(path_list.get(i))));
        //엑셀 상단에 적을 제목 리스트
        ArrayList<String> titlelist = supporter.getTitleList(path_list);
        //파일의 string key
        ArrayList<String> keylist = supporter.getKeyList(languagelist);
        //각 파일마다의 key-value list
        ArrayList<Map<String, Object>> keyvaluelist = supporter.makeKeyValue(languagelist);

        return io.writeExcelFile(keylist, supporter.combineValue(keylist, keyvaluelist), titlelist, save_path);
    }
}
