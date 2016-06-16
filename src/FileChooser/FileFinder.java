package FileChooser;

/**
 * Created by Mhwan on 2016. 6. 13..
 */

import java.io.File;
import java.util.ArrayList;

/**
 * FileChooser로 선택한 폴더에서 실제 strings.xml파일을 갖고있는 위치를 반환한다.
 */
public class FileFinder {
    private String folderpath;
    public static final String FILE_NAME = "strings.xml";
    private ArrayList<String> filepathlist;

    public FileFinder(String path){
        this.folderpath = path;
    }

    public ArrayList<String> findFile(){
        File file = new File(folderpath);

        if (!file.exists() || !file.isDirectory()) {
            System.out.println("오류!!!!");
            return null;
        }

        filepathlist = new ArrayList<String>();
        finder(file.listFiles());

        return filepathlist;
    }


    //
    private void finder(File [] list) {
        for (int i=0; i<list.length; i++){
            System.out.println(list[i].getName());
            if (list[i].getName().equals(FILE_NAME)) {
                filepathlist.add(list[i].getAbsolutePath());
                System.out.println(list[i].getAbsolutePath());
            }

            else if (list[i].isDirectory())
                finder(list[i].listFiles());
        }
    }
}
