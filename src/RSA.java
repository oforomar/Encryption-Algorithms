import java.math.BigInteger;
import java.util.Random;

class RSA {
    private BigInteger d;
    private BigInteger e;
    private BigInteger N;

    RSA(){
        // Generate public and private keys
        // Generate 2 random prime numbers
        final int BITLENGTH = 1024;
        Random random = new Random();
        BigInteger p1 = BigInteger.probablePrime(BITLENGTH, random);
        BigInteger p2 = BigInteger.probablePrime(BITLENGTH, random);

        this.N = p1.multiply(p2);

        // Calculate Phi(N) for p and q
        BigInteger temp = p1.subtract(BigInteger.ONE);
        BigInteger phiN = temp.multiply(p2.subtract(BigInteger.ONE));

        // Generate Public key for encryption
        // Choose e prime to Phi(N)
        BigInteger e  = BigInteger.ONE;
        BigInteger remainder;
        do {
            e = e.add(BigInteger.ONE);
            remainder = phiN.remainder(e);
        } while (remainder.equals(BigInteger.ZERO));

        this.e = e;

        // Calculate d
        BigInteger m = new BigInteger("2");
        BigInteger d, quotient;
        BigInteger natural = BigInteger.ZERO;
        do {
            natural = natural.add(BigInteger.ONE);
            quotient = phiN.add(BigInteger.ONE).multiply(natural);
            d = quotient.divide(e);
        }while (!m.modPow(e.multiply(d), N).equals(m) && d.compareTo(phiN) < 0);

        this.d = d;
    }

    String encrypt(BigInteger plaintext){
        BigInteger cipher = plaintext.modPow(e, N);

        return cipher.toString();
    }

    String decrypt(BigInteger cipher){
        BigInteger plain = cipher.modPow(d, N);

        return plain.toString();
    }
}
