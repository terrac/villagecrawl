package gwt.client.map;

public class StringUtils {
	  private static final int PAD_LIMIT = 39;
	public static String rightPad(String str, int size, char padChar) {
	      if (str == null) {
	          return null;
	      }
	      int pads = size - str.length();
	      if (pads <= 0) {
	          return str; // returns original String when possible
	      }
	      if (pads > PAD_LIMIT) {
	          return rightPad(str, size, String.valueOf(padChar));
	      }
	      return str.concat(padding(pads, padChar));
	  }
	  
	  public static String rightPad(String str, int size, String padStr) {
	      if (str == null) {
	          return null;
	      }
	      if (isEmpty(padStr)) {
	          padStr = " ";
	      }
	      int padLen = padStr.length();
	      int strLen = str.length();
	      int pads = size - strLen;
	      if (pads <= 0) {
	          return str; // returns original String when possible
	      }
	      if (padLen == 1 && pads <= PAD_LIMIT) {
	          return rightPad(str, size, padStr.charAt(0));
	      }

	      if (pads == padLen) {
	          return str.concat(padStr);
	      } else if (pads < padLen) {
	          return str.concat(padStr.substring(0, pads));
	      } else {
	          char[] padding = new char[pads];
	          char[] padChars = padStr.toCharArray();
	          for (int i = 0; i < pads; i++) {
	              padding[i] = padChars[i % padLen];
	          }
	          return str.concat(new String(padding));
	      }
	  }
	  public static boolean isEmpty(String str) {
	      return str == null || str.length() == 0;
	  }
	  private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
	      if (repeat < 0) {
	          throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
	      }
	      final char[] buf = new char[repeat];
	      for (int i = 0; i < buf.length; i++) {
	          buf[i] = padChar;
	      }
	      return new String(buf);
	  }
	  
	  public static String implode(String[] ary, String delim) {
		    String out = "";
		    for(int i=0; i<ary.length; i++) {
		        if(i!=0) { out += delim; }
		        out += ary[i];
		    }
		    return out;
		}
}
