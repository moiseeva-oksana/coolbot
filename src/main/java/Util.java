public class Util {
    public static final String HELP =
            "Type any string and bot will tell you is it a palindrome \n" +
                    "Type '/history' to see list of words you have already typed";

    public static boolean isPalindrome(String string) {
        final char[] word = string.toCharArray();
        int i = 0;
        int j = word.length - 1;
        while (j > i) {
            if (word[i] != word[j]) {
                return false;
            }
            ++i;
            --j;
        }
        return true;
    }
}
