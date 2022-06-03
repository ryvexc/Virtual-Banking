import java.util.ArrayList;

public class Array {
    protected static final void printStringArray(String[] arr) {
        for(int i=0; i<arr.length; i++) {
            if(i == 0) System.out.print("[");
            System.out.printf("\""+arr[i]+"\"%s", (i<arr.length-1? "," : ""));
            if(i == arr.length-1) System.out.print("]\n");
        }
    }

    protected static String[] reverseCharArrayToString(char[] arr) {
        ArrayList<Character> reversedArray = new ArrayList<>();
        for(int i=arr.length-1; i>=0; i--) reversedArray.add(arr[i]);
        String[] arr_return = new String[reversedArray.size()];
        for(int i=0; i<arr.length; i++) arr_return[i] = String.valueOf(reversedArray.get(i));
        return arr_return;
    }
    protected static String[] reverseStringArray(String[] arr) {
        ArrayList<String> reversedArray = new ArrayList<>();
        for(int i=arr.length-1; i>=0; i--) reversedArray.add(arr[i]);
        String[] arr_return = new String[reversedArray.size()];
        for(int i=0; i<arr.length; i++) arr_return[i] = reversedArray.get(i);
        return arr_return;
    }
    protected static String joinableArrayString(String[] arr) {
        String str = ""; for(int i=0; i<arr.length; i++) str += arr[i];
        return str;
    }
}
