import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // start coding here
        StringBuilder stringArray = new StringBuilder();

        int inData = reader.read();
        while (inData != -1) {
            stringArray.append((char) inData);
            inData = reader.read();
        }
        System.out.println(stringArray.reverse());
        reader.close();
    }
}