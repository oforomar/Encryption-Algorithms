class RC4 {
    private int[] S = new int[256];

    // Key Generation
    RC4(String keyString) {

        // Take string key as input
        // Convert string to character array
        char[] charArray = keyString.toCharArray();
        int[] k = new int[256];

        // First Step: Initialization
        // Initialize State vector and key array
        for (int i = 0; i < 256; i++) {
            S[i] = i;
            k[i] = charArray[i % charArray.length];
        }

        // Permutation of state vector according to key values
        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + k[i]) % 256;
            int temp = S[i];
            S[i] = S[j];
            S[j] = temp;
        }
    }

    String encrypt(String plaintextString){

        // Second Step: Encryption
        // Take input plaintext string and convert to character array
        char[] plaintext = plaintextString.toCharArray();

        int i = 0, j = 0, k;
        int temp;
        int[] result = new int[plaintext.length];
        char[] ciphertext = new char[plaintextString.length()];


        for (int counter = 0; counter < plaintext.length; counter++) {

            // Permutation of state vector according to i and j values
            i = (i + 1) % 256;
            j = (j + S[i]) % 256;
            temp = S[j];
            S[j] = S[i];
            S[i] = temp;

            // Selection of state vector element to serve as key
            k = S[(S[i] + S[j]) % 256];

            // XOR with key and store result
            result[counter] = plaintext[counter] ^ k;
            ciphertext[counter] = (char) result[counter];
        }

        return new String(ciphertext);
    }

    String decrypt(String plaintextString){
        return encrypt(plaintextString);
    }

}