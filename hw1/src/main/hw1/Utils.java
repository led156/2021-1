package hw1;

public class Utils {

    /**
     * Find an index of an element matching `target`.
     * @param xs
     * @param target
     * @return index of a matching element, -1 otherwise
     */
    public static int findIndex(int[] xs, int target) {
    	for(int i = 0; i<xs.length; i++) {
    		if(xs[i] == target) return i;
    	}

        return -1;
    }

    /**
     * Calculate the sum of an array.
     * @param xs
     * @return sum of an array
     */
    public static double sum(double[] xs) {
    	double ans = 0;
    	
    	for(int i = 0; i<xs.length; i++) {
    		ans += xs[i];
    	}

        return ans;
    }

    /**
     * Reverse the elements of a String array.
	 * For example, ["A", "BB", "C"] => ["C", "BB", "A"]
     * @param xs
     * @return a newly created array containing elements of xs in reversed order
     */
    public static String[] reverse(String[] xs) {
    	String[] ans = new String[xs.length];
    	
    	for(int i = 0; i<xs.length; i++) {
    		ans[i] = xs[xs.length-i-1];
    	}

        return ans;
    }

    /**
     * Returns an array containing running averages of an array.
     * @param xs
     * @return an array containing running average
     *
     * Given an input xs = [1, 2, 3, 4], `average()` returns a new array
     * containing running averages [1.0, 1.5, 2.0, 2.5].
     * Here,
     *      1.0 = 1 / 1
     *      1.5 = (1 + 2) / 2
     *      2.0 = (1 + 2 + 3) / 3
     *      2.5 = (1 + 2 + 3 + 4) / 4
     */
    public static double[] average(int[] xs) {
    	double[] ans = new double[xs.length];
    	
    	for(int i = 0; i<xs.length; i++) {
    		for(int j = 0; j<=i; j++) {
    			ans[i] += xs[j];
    		}
    		ans[i] = ans[i]/(i+1);
    	}

        return ans;
    }

}
