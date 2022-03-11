public class Encryptor {
    /**
     * A two-dimensional array of single-character strings, instantiated in the constructor
     */
    private String[][] letterBlock;

    /**
     * The number of rows of letterBlock, set by the constructor
     */
    private int numRows;

    /**
     * The number of columns of letterBlock, set by the constructor
     */
    private int numCols;

    /**
     * Constructor
     */
    public Encryptor(int r, int c) {
        letterBlock = new String[r][c];
        numRows = r;
        numCols = c;
    }

    public String[][] getLetterBlock() {
        return letterBlock;
    }

    /**
     * Places a string into letterBlock in row-major order.
     *
     * @param str the string to be processed
     *            <p>
     *            Post-condition:
     *            if str.length() < numRows * numCols, "A" in each unfilled cell
     *            if str.length() > numRows * numCols, trailing characters are ignored
     */
    public void fillBlock(String str) {
        int index = 0;
        for (int i = 0; i < letterBlock.length; i++) {
            for (int j = 0; j < letterBlock[0].length; j++) {
                if (index > str.length() - 1) {
                    letterBlock[i][j] = "A";
                } else {
                    letterBlock[i][j] = String.valueOf(str.charAt(index));
                    index++;
                }
            }
        }
    }

    public void fillBlockColumnMajor(String str) {
        int index = 0;
        for (int i = 0; i < letterBlock[0].length; i++) {
            for (int j = 0; j < letterBlock.length; j++) {
                if (index > str.length() - 1) {
                    letterBlock[j][i] = "";
                } else {
                    letterBlock[j][i] = String.valueOf(str.charAt(index));
                    index++;
                }
            }
        }
    }

    /**
     * Extracts encrypted string from letterBlock in column-major order.
     * <p>
     * Precondition: letterBlock has been filled
     *
     * @return the encrypted string from letterBlock
     */
    public String encryptBlock() {
        String returnString = "";
        for (int i = 0; i < letterBlock[0].length; i++) {
            for (String[] strings : letterBlock) {
                returnString += strings[i];
            }
        }
        return returnString;
    }

    public String decryptBlock() {
        String returnString = "";
        for (String[] strings : letterBlock) {
            for (String string : strings) {
                returnString += string;
            }
        }
        return returnString;
    }

    /**
     * Encrypts a message.
     *
     * @param message the string to be encrypted
     * @return the encrypted message; if message is the empty string, returns the empty string
     */
    public String encryptMessage(String message) {
        String encrypted = "";
        int maxIndex = numCols * numRows - 1;
        while (message.length() > 0) {
            fillBlock(message);
            encrypted += encryptBlock();
            if (maxIndex > message.length()) {
                maxIndex = message.length() - 1;
            }
            message = message.substring(maxIndex + 1);
        }
        return encrypted;
    }

    /**
     * Decrypts an encrypted message. All filler 'A's that may have been
     * added during encryption will be removed, so this assumes that the
     * original message (BEFORE it was encrypted) did NOT end in a capital A!
     * <p>
     * NOTE! When you are decrypting an encrypted message,
     * be sure that you have initialized your Encryptor object
     * with the same row/column used to encrypt the message! (i.e.
     * the “encryption key” that is necessary for successful decryption)
     * This is outlined in the precondition below.
     * <p>
     * Precondition: the Encryptor object being used for decryption has been
     * initialized with the same number of rows and columns
     * as was used for the Encryptor object used for encryption.
     *
     * @param encryptedMessage the encrypted message to decrypt
     * @return the decrypted, original message (which had been encrypted)
     * <p>
     * TIP: You are encouraged to create other helper methods as you see fit
     * (e.g. a method to decrypt each section of the decrypted message,
     * similar to how encryptBlock was used)
     */
    public String decryptMessage(String encryptedMessage) {
        String decrypted = "";
        int maxIndex = numCols * numRows - 1;
        while (encryptedMessage.length() > 0) {
            fillBlockColumnMajor(encryptedMessage);
            decrypted += decryptBlock();
            if (maxIndex > encryptedMessage.length()) {
                maxIndex = encryptedMessage.length() - 1;
            }
            encryptedMessage = encryptedMessage.substring(maxIndex + 1);
        }
        for (int i = decrypted.length() - 1; decrypted.charAt(i) == 'A'; i--) {
            decrypted = decrypted.substring(0, i);
        }
        return decrypted;
    }
}