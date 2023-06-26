import java.util.*;
import java.io.File;

public class ReadFile {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            System.out.print("Enter the filename: ");
            Scanner input = new Scanner(System.in);
            File file = new File(input.nextLine());
            input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                list.add(line);
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        int n = Integer.parseInt(list.get(0));
        list.remove(0);
        String[] array = new String[list.size()];
        array = list.toArray(array);
        String array2d[][] = new String[n][n];
        int k = 0;
        for(int i=0; i<n; i++)
        {
            for(int j=0; j<n; j++)
            {
                array2d[i][j] = array[k];
                k++;
            }
        }
        System.out.println(Arrays.deepToString(array2d));
    }
}