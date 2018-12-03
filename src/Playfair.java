import java.util.LinkedHashSet;
import java.util.Set;
class Playfair {

    private String key;
    private char[][] matrix;

    private String getKey() {
        return key;
    }

    private void setKey(String key) {
        this.key = key;
    }

    void createMatrix(String key){
        setKey(key);
        char[][] matrix = new char[5][5];

        // Create array to populate matrix
        String s = getKey() + "abcdefghijklmnopqrstuvwxyz";

        // Filter duplicate characters
        char[] chars = s.toCharArray();

        Set<Character> charSet = new LinkedHashSet<>();
        for (char c : chars){
            if (c == 'j')
                continue;
            charSet.add(c);
        }

        // Final array ready for matrix
        StringBuilder sb = new StringBuilder();
        for (Character character : charSet){
            sb.append(character);
        }

        // Matrix population
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = sb.charAt((i * 5) + j);
            }
        }
        this.matrix = matrix;
    }

    String encrypt(String word){
        StringBuilder cipher = new StringBuilder();
        for (int i = 0; i < word.length(); i+=2) {

            char char1 = word.charAt(i);
            char char2 = word.charAt(i + 1);

            int[] char1Index = indexOf(matrix, char1);
            int[] char2Index = indexOf(matrix, char2);

            int[] encryptedChar1Index = {0, 0};
            int[] encryptedChar2Index = {0, 0};

            // Compare the Xs (rows) of the 2 characters
            // If they appear at the same row, increment the column index
            if (char1Index[0] == char2Index[0]) {
                encryptedChar1Index[0] = char1Index[0];
                encryptedChar1Index[1] = char1Index[1] + 1;
                if (encryptedChar1Index[1] > 4)
                    encryptedChar1Index[1] = 0;
                encryptedChar2Index[0] = char2Index[0];
                encryptedChar2Index[1] = char2Index[1] + 1;
                if (encryptedChar2Index[1] > 4)
                    encryptedChar2Index[1] = 0;
            } else
                // Compare the Ys (columns) of the 2 characters
                // If they appear at the same column, increment the row index
                if (char1Index[1] == char2Index[1]) {
                    encryptedChar1Index[1] = char1Index[1];
                    encryptedChar1Index[0] = char1Index[0] + 1;
                    if (encryptedChar1Index[0] > 4)
                        encryptedChar1Index[0] = 0;
                    encryptedChar2Index[1] = char2Index[1];
                    encryptedChar2Index[0] = char2Index[0] + 1;
                    if (encryptedChar2Index[0] > 4)
                        encryptedChar2Index[0] = 0;
                } else {
                    // They appear in different rows and columns
                    // Swap column indices
                    encryptedChar1Index[0] = char1Index[0];
                    encryptedChar1Index[1] = char2Index[1];

                    encryptedChar2Index[0] = char2Index[0];
                    encryptedChar2Index[1] = char1Index[1];
                }

            char newChar1 = matrix[encryptedChar1Index[0]][encryptedChar1Index[1]];
            char newChar2 = matrix[encryptedChar2Index[0]][encryptedChar2Index[1]];

            cipher.append(String.valueOf(newChar1) + String.valueOf(newChar2));
        }
        return cipher.toString();
    }
    String decrypt(String word){
        StringBuilder cipher = new StringBuilder();
        for (int i = 0; i < word.length(); i+=2) {

            char char1 = word.charAt(i);
            char char2 = word.charAt(i + 1);

            int[] char1Index = indexOf(matrix, char1);
            int[] char2Index = indexOf(matrix, char2);

            int[] encryptedChar1Index = {0, 0};
            int[] encryptedChar2Index = {0, 0};

            // Compare the Xs (rows) of the 2 characters
            // If they appear at the same row, decrement the column index
            if (char1Index[0] == char2Index[0]) {
                encryptedChar1Index[0] = char1Index[0];
                encryptedChar1Index[1] = char1Index[1] - 1;
                if (encryptedChar1Index[1] < 0)
                    encryptedChar1Index[1] = 0;
                encryptedChar2Index[0] = char2Index[0];
                encryptedChar2Index[1] = char2Index[1] - 1;
                if (encryptedChar2Index[1] < 0)
                    encryptedChar2Index[1] = 0;
            } else
                // Compare the Ys (columns) of the 2 characters
                // If they appear at the same column, decrement the row index
                if (char1Index[1] == char2Index[1]) {
                    encryptedChar1Index[1] = char1Index[1];
                    encryptedChar1Index[0] = char1Index[0] - 1;
                    if (encryptedChar1Index[0] < 0)
                        encryptedChar1Index[0] = 0;
                    encryptedChar2Index[1] = char2Index[1];
                    encryptedChar2Index[0] = char2Index[0] - 1;
                    if (encryptedChar2Index[0] < 0)
                        encryptedChar2Index[0] = 0;
                } else {
                    // They appear in different rows and columns
                    // Swap column indices
                    encryptedChar1Index[0] = char1Index[0];
                    encryptedChar1Index[1] = char2Index[1];

                    encryptedChar2Index[0] = char2Index[0];
                    encryptedChar2Index[1] = char1Index[1];
                }

            char newChar1 = matrix[encryptedChar1Index[0]][encryptedChar1Index[1]];
            char newChar2 = matrix[encryptedChar2Index[0]][encryptedChar2Index[1]];

            cipher.append(String.valueOf(newChar1) + String.valueOf(newChar2));
        }
        return cipher.toString();
    }

    private int[] indexOf(char[][] matrix2d, char c){
        int h = 0;
        int k = 0;
        boolean charFound = false;
        for (int i = 0; i < matrix2d.length && !charFound; i++){
            for (int j =0; j < matrix2d[i].length; j++){
                if (matrix2d[i][j] == c){
                 h = i;
                 k = j;
                 charFound = true;
                 break;
                }
            }
        }
        return new int[]{h, k};
    }
}
