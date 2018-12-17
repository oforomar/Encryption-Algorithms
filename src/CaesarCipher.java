class CaesarCipher {



    static String encrypt(String plaintext, int shift){

        String s = "";

        int length = plaintext.length();

        for (int i = 0; i < length; i++){

            char c = (char) (plaintext.charAt(i) + shift);
            if ((c - shift) == ' ')
                s += ' ';
            else if (c > 'z')
                s += (char) (plaintext.charAt(i) -(26 - shift));
            else
                s += (char) (plaintext.charAt(i) + shift);
        }
        return s;
    }

    static String decrypt(String plaintext, int shift){

        String s = "";

        int length = plaintext.length();

        for (int i = 0; i < length; i++){

            char c = (char) (plaintext.charAt(i) - shift);
            if ((c + shift) == ' ')
                s += ' ';
            else if (c < 'A')
                s += (char) (plaintext.charAt(i) +(26 - shift));
            else
                s += (char) (plaintext.charAt(i) - shift);
        }
        return s;
    }

}
