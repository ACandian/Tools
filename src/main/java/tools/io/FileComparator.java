package tools.io;

import java.io.File;
import java.util.Comparator;

/**
 * Comparator to sort files alphabetically.
 * 
 * @author A. CANDIAN
 *
 */
public class FileComparator implements Comparator<File> {
    /**
     * Do a comparison using File.getName().
     * 
     * @param o1
     *            First File to compare.
     * @param o2
     *            Second File to compare.
     */
    @Override
    public int compare(File o1, File o2) {

        return o1.getName().compareTo(o2.getName());
    }
}
