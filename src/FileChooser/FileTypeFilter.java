package FileChooser;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by Mhwan on 2016. 6. 15..
 *
 * JFileChooser에 파일 확장자 Filter
 *
 * 출력형태 : description (*.xlsx)
 */
public class FileTypeFilter extends FileFilter {
    private String extension, description;

    public FileTypeFilter(String extension, String description){
        this.extension = extension;
        this.description = description;
    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        return file.getName().toLowerCase().endsWith(extension);
    }

    @Override
    public String getDescription() {
        return String.format("(*%s) ", extension) +description ;
    }
}
