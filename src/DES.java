import java.util.ArrayList;
import java.util.Collections;

class DES {
    private ArrayList<StringBuilder> keyList;

    DES(String key){
        StringBuilder keySB = new StringBuilder();

        String sTemp ;
        for(char c : key.toCharArray()){
            sTemp = Integer.toBinaryString(c);
            while (sTemp.length() < 8)
                sTemp  = "0" + sTemp;
            keySB.append(sTemp);
        }

        while (keySB.length() != 64)
            keySB.append("0");

        keySB = new StringBuilder(keySB.substring(0, 64));
        keySB = initalKeyPermute(keySB);

        ArrayList<StringBuilder> keyList = new ArrayList<>();
        StringBuilder nextRoundKey;

        nextRoundKey = keyShift(keySB, 1);               //k1
        keyList.add(compressionKeyPermute(nextRoundKey));

        nextRoundKey = keyShift(nextRoundKey, 2);        //k2
        keyList.add(compressionKeyPermute(nextRoundKey));

        nextRoundKey = keyShift(nextRoundKey, 2);        //k3
        keyList.add(compressionKeyPermute(nextRoundKey));

        nextRoundKey = keyShift(nextRoundKey, 2);        //k4
        keyList.add(compressionKeyPermute(nextRoundKey));

        nextRoundKey = keyShift(nextRoundKey, 2);        //k5
        keyList.add(compressionKeyPermute(nextRoundKey));

        nextRoundKey = keyShift(nextRoundKey, 2);        //k6
        keyList.add(compressionKeyPermute(nextRoundKey));

        nextRoundKey = keyShift(nextRoundKey, 2);        //k7
        keyList.add(compressionKeyPermute(nextRoundKey));

        nextRoundKey = keyShift(nextRoundKey, 2);        //k8
        keyList.add(compressionKeyPermute(nextRoundKey));

        nextRoundKey = keyShift(nextRoundKey, 1);        //k9
        keyList.add(compressionKeyPermute(nextRoundKey));

        nextRoundKey = keyShift(nextRoundKey, 2);        //k10
        keyList.add(compressionKeyPermute(nextRoundKey));

        nextRoundKey = keyShift(nextRoundKey, 2);        //k11
        keyList.add(compressionKeyPermute(nextRoundKey));

        nextRoundKey = keyShift(nextRoundKey, 2);        //k12
        keyList.add(compressionKeyPermute(nextRoundKey));

        nextRoundKey = keyShift(nextRoundKey, 2);        //k13
        keyList.add(compressionKeyPermute(nextRoundKey));

        nextRoundKey = keyShift(nextRoundKey, 2);        //k14
        keyList.add(compressionKeyPermute(nextRoundKey));

        nextRoundKey = keyShift(nextRoundKey, 2);        //k15
        keyList.add(compressionKeyPermute(nextRoundKey));

        nextRoundKey = keyShift(nextRoundKey, 1);        //k16
        keyList.add(compressionKeyPermute(nextRoundKey));

        this.keyList = keyList;
    }

    // Key Generation
    // Maps 64-bit input key to 56-bit key
    private StringBuilder initalKeyPermute(StringBuilder block){
        int[] indexMap = {57, 49, 41, 33, 25, 17,  9,
                           1, 58, 50, 42, 34, 26, 18,
                          10,  2, 59, 51, 43, 35, 27,
                          19, 11,  3, 60, 52, 44, 36,
                          63, 55, 47, 39, 31, 23, 15,
                           7, 62, 54, 46, 38, 30, 22,
                          14,  6, 61, 53, 45, 37, 29,
                          21, 13,  5, 28, 20, 12,  4};

        StringBuilder permutated = new StringBuilder();

        for (int i = 0; i < 56; i++)
            permutated.append(block.charAt(indexMap[i]-1));

        return permutated;
    }

    // Key shift
    // Splits 56-bit key and applies required shift
    private StringBuilder keyShift(StringBuilder key, int n){
        String c0 = key.substring( 0, 28);
        String d0 = key.substring(28, 56);

        for (int i = 0; i < n; i++){
            c0 = c0.substring(1, 28) + c0.charAt(0);
            d0 = d0.substring(1, 28) + d0.charAt(0);
        }

        return new StringBuilder(c0 + d0);
    }

    // Key compression P-Box
    // Maps 56-bit key to 48-bit key
    private StringBuilder compressionKeyPermute(StringBuilder block){
        int[] indexMap = {14, 17, 11, 24,  1,  5,
                           3, 28, 15,  6, 21, 10,
                          23, 19, 12,  4, 26,  8,
                          16,  7, 27, 20, 13,  2,
                          41, 52, 31, 37, 47, 55,
                          30, 40, 51, 45, 33, 48,
                          44, 49, 39, 56, 34, 53,
                          46, 42, 50, 36, 29, 32};

        StringBuilder permutated = new StringBuilder();

        for (int i = 0; i < 48; i++)
            permutated.append(block.charAt(indexMap[i] - 1));

        return permutated;
    }

    private static final int[][][] sBoxes = {
            {{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},        // S1
                    {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                    {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                    {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}},
            {{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},        // S2
                    {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                    {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                    {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}},
            {{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},        // S3
                    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}},
            {{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},        // S4
                    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}},
            {{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},        // S5
                    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                    {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                    {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}},
            {{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},        // S6
                    {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                    {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                    {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}},
            {{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},        // S7
                    {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                    {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                    {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}},
            {{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},        // S8
                    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}}
    };

    // Step 1
    // Initial and Final permutations
    // Maps 64-bit block to another 64-bit block according to predefined tables
    private StringBuilder initialPermutation(StringBuilder block){
        int[] indexMap = {58, 50, 42, 34, 26, 18, 10, 2,
                          60, 52, 44, 36, 28, 20, 12, 4,
                          62, 54, 46, 38, 30, 22, 14, 6,
                          64, 56, 48, 40, 32, 24, 16, 8,
                          57, 49, 41, 33, 25, 17,  9, 1,
                          59, 51, 43, 35, 27, 19, 11, 3,
                          61, 53, 45, 37, 29, 21, 13, 5,
                          63, 55, 47, 39, 31, 23, 15, 7};

        StringBuilder permutated = new StringBuilder();

        for (int i = 0; i < 64; i++)
            permutated.append(block.charAt(indexMap[i]-1));

        return permutated;
    }

    private StringBuilder finalPermutation(StringBuilder block){
        int[] indexMap = {40,  8, 48, 16, 56, 24, 64, 32,
                          39,  7, 47, 15, 55, 23, 63, 31,
                          38,  6, 46, 14, 54, 22, 62, 30,
                          37,  5, 45, 13, 53, 21, 61, 29,
                          36,  4, 44, 12, 52, 20, 60, 28,
                          35,  3, 43, 11, 51, 19, 59, 27,
                          34,  2, 42, 10, 50, 18, 58, 26,
                          32,  1, 41,  9, 49, 17, 57, 25};

        StringBuilder permutated = new StringBuilder();

        for (int i = 0; i < 64; i++)
            permutated.append(block.charAt(indexMap[i]-1));

        return permutated;
    }

    // Step 1 of DES function
    // Expansion D-Box: expands 32-bit right block part to 48-bit blocks
    private StringBuilder expDbox(StringBuilder rBlock){
        int[] indexMap = {32,  1,  2,  3,  4,  5,
                           4,  5,  6,  7,  8,  9,
                           9,  9, 10, 11, 12, 13,
                          12, 13, 14, 15, 16, 17,
                          16, 17, 18, 19, 20, 21,
                          20, 21, 22, 23, 24, 25,
                          24, 25, 26, 27, 28, 29,
                          28, 29, 31, 31, 31, 32};

        StringBuilder permutated = new StringBuilder();

        for (int i = 0; i < 48; i++)
            permutated.append(rBlock.charAt(indexMap[i]-1));

        return permutated;
    }

    // Step 2 of DES function
    // XOR of right block with 48-bit key

    // Step 3 of DES function
    // Map 48-bit input to 32-bit block
    private StringBuilder sBox(StringBuilder block){
        String part;
        String s;
        StringBuilder result = new StringBuilder();
        int row;
        int column;

        for (int i = 0; i < 48; i+=6){
            part = block.substring(i, i+6);
            row = Character.getNumericValue(part.charAt(0))*2 + Character.getNumericValue(part.charAt(5));
            column = Integer.parseInt(part.substring(1, 5), 2);

            s = Integer.toString(sBoxes[i/6][row][column], 2);
            while (s.length() != 4)
                s = ("0000" + s).substring(s.length());
            result.append(s);
        }
        return result;
    }

    // Step 4 of DES function
    // Permutation of 32-bit blocks
    private StringBuilder straightDbox(StringBuilder rBlock){
        int[] indexMap = {16,  7, 20, 21,
                          29, 12, 28, 17,
                           1, 15, 23, 26,
                           5, 18, 31, 10,
                           2,  8, 24, 14,
                          32, 27,  3,  9,
                          19, 13, 30,  6,
                          22, 11,  4, 25};

        StringBuilder permutated = new StringBuilder();

        for (int i = 0; i < 32; i++)
            permutated.append(rBlock.charAt(indexMap[i]-1));

        return permutated;
    }

    String encrypt(String plaintext){
        while ((plaintext.length() % 64) != 0)
            plaintext += ' ';

        StringBuilder stringArray = new StringBuilder();
        String sTemp ;
        for(char c : plaintext.toCharArray()){
            sTemp = Integer.toBinaryString(c);
            while (sTemp.length() < 8)
                sTemp  = "0" + sTemp;
            stringArray.append(sTemp);

        }

        StringBuilder currentBlock;
        StringBuilder cipher = new StringBuilder();

        for (int i = 0; i < stringArray.length(); i+=64){
            // Take 64-bit block of input
            currentBlock = new StringBuilder(stringArray.substring(i, i + 64));

            // Step 1: Initial Permutation
            currentBlock = initialPermutation(currentBlock);

            // Split the block for fiestel rounds
            StringBuilder leftBlock =  new StringBuilder(currentBlock.substring( 0, 32));
            StringBuilder rightBlock = new StringBuilder(currentBlock.substring(32, 64));
            StringBuilder newRight = new StringBuilder();
            StringBuilder temp;
            StringBuilder opRight = new StringBuilder();

            // 16 fiestel rounds
            for (int j = 0; j < 16; j++){
                // 1.Expansion D-Box
                temp = expDbox(rightBlock);

                // 2.XOR with round key
                for (int k = 0; k < temp.length(); k++)
                    opRight.append(temp.charAt(k) ^ keyList.get(j).charAt(k));

                // 3.S-Box + 4.Straight D-Box
                temp = straightDbox(sBox(opRight));

                // XOR with left block
                for (int k = 0; k < temp.length(); k++)
                    newRight.append(temp.charAt(k) ^ leftBlock.charAt(k));

                // Swap left with right
                leftBlock  = rightBlock;
                rightBlock = newRight;
                newRight = new StringBuilder();
            }
            // Final Swap
            StringBuilder stringBuilder = new StringBuilder(rightBlock.toString() + leftBlock.toString());

            // Final Permutation
            cipher.append(finalPermutation(stringBuilder));
        }

        String c = cipher.toString() ;
        StringBuilder n = new StringBuilder();
        int cipherInteger ;
        for (int i = 0; i <c.length(); i+=8) {
            cipherInteger = Integer.parseInt( c.substring(i , i+8), 2 );
            n.append((char)cipherInteger);
        }
        return n.toString();
    }

    String decrypt(String plaintext){
         Collections.reverse(keyList);
         return encrypt(plaintext);
    }
}
