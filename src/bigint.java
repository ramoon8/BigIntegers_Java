

public class BigIntegers {

    private byte[] numberArray;
    private boolean isNegative = false;

    public enum CompResult {
        BOTH_ARE_EQUAL,
        THIS_IS_GREATER,
        THIS_IS_SMALLER
    }

    private String cleanStringNumber(String str) {
        if (str.isEmpty()) {
            return "0";
        }

        int lb = 0;
        int ub = str.length();
        StringBuilder x = new StringBuilder();
        boolean haveSign = false;

        if (str.startsWith("+") || str.startsWith("-")) {
            lb = 1;
            x.append(str.charAt(0));
            haveSign = true;
        }
        boolean firstNonZeroNumber = false;
        for (int idx = lb; idx < ub; idx++) {
            char num = str.charAt(idx);
            if (num == '0' && !firstNonZeroNumber) {
                continue;
            } else {
                firstNonZeroNumber = true;
            }
            if (num >= '0' && num <= '9') {
                x.append(num);
            }
        }
        if (x.length() == 0) {
            x.append('0');
        } else if (x.length() == 1 && haveSign) {
            x = new StringBuilder("0");
        }
        return x.toString();
    }

    public BigIntegers(String number) {
        number = cleanStringNumber(number);
        boolean haveSign = false;

        if (number.startsWith("-")) {
            this.isNegative = true;
            haveSign = true;
        } else if (number.startsWith("+")) {
            this.isNegative = false;
            haveSign = true;
        }

        int lb = haveSign ? 1 : 0;
        int ub = number.length();
        this.numberArray = new byte[ub - lb];
        for (int i = lb; i < ub; i++) {
            char num = number.charAt(i);
            this.numberArray[i - lb] = Byte.parseByte(Character.toString(num));
        }
    }

    @Override
    public String toString() {
        StringBuilder x = new StringBuilder(this.isNegative ? "-" : "+");
        for (byte b : this.numberArray) {
            x.append(b);
        }
        if (x.toString().equals("-0") || x.toString().equals("+0")) {
            return "0";
        }
        return x.toString();
    }

    public CompResult compareTo(BigIntegers p2) {
        int p1Length = this.numberArray.length;
        int p2Length = p2.numberArray.length;

        if (this.isNegative && !p2.isNegative) {
            return CompResult.THIS_IS_SMALLER;
        }
        if (!this.isNegative && p2.isNegative) {
            return CompResult.THIS_IS_GREATER;
        }
        if (p1Length > p2Length) {
            return this.isNegative ? CompResult.THIS_IS_SMALLER : CompResult.THIS_IS_GREATER;
        }
        if (p2Length > p1Length) {
            return p2.isNegative ? CompResult.THIS_IS_GREATER : CompResult.THIS_IS_SMALLER;
        }
        for (int idx = 0; idx < p1Length; idx++) {
            if (this.numberArray[idx] > p2.numberArray[idx]) {
                return this.isNegative ? CompResult.THIS_IS_SMALLER : CompResult.THIS_IS_GREATER;
            } else if (this.numberArray[idx] < p2.numberArray[idx]) {
                return p2.isNegative ? CompResult.THIS_IS_GREATER : CompResult.THIS_IS_SMALLER;
            }
        }
        return CompResult.BOTH_ARE_EQUAL;
    }

    /**
     * @param p2
     * @return
     */
    private BigIntegers add(BigIntegers p2) {
        int l1 = this.numberArray.length;
        int l2 = p2.numberArray.length;
        StringBuilder sum = new StringBuilder();
        int carry = 0;
        for (int idx1 = l1 - 1, idx2 = l2 - 1; idx1 >= 0 || idx2 >= 0; idx1--, idx2--) {
            int s = carry;
            if (idx1 >= 0) {
                s += this.numberArray[idx1];
            }
            if (idx2 >= 0) {
                s += this.numberArray[idx2];
            }
        }
    }
}

