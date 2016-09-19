import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Given an amount of change (n) list all of the possibilities of coins that can be used
 * to satisfy the amount of change.
 * @author Eric
 *
 */
public class CoinChange{
	private int [] coins = {1,5,10,25};
	
	/**
	 * Uses a greedy algorithm with backtracking and recursion to find the possible sequences
	 * @param sum
	 * @param n
	 * @param pos
	 * @param sequence
	 */
	public void findPossibleCoins(int sum, int n, int pos, List<Integer> sequence){
		for(int i = pos; i<coins.length; i++){
			int coin = coins[i];
			sum += coin;
			sequence.add(coin);
			if(sum > n ){
				sequence.remove(sequence.size() - 1);
				return;
			}
			if(sum == n){
				System.out.println(sequence);
				sequence.remove(sequence.size() - 1);
				return;
			}
			findPossibleCoins(sum, n, pos, sequence);
			sequence.remove(sequence.size() - 1);
			pos++;
			sum -= coin;			
		}
	}
	
	/**
	 * Uses dynamic programming to find the number of possible combinations
	 * @param n
	 * @return
	 */
	public int findPossibleCoinsDP(int n){
		int [][] sol = new int[coins.length + 1][n + 1];
		for(int i = 0; i < coins.length + 1; i++){
			sol[i][0] = 1;
		}
		for(int i = 1; i < n + 1; i++){
			sol[0][i] = 0;
		}
		
		for(int i = 1; i < coins.length + 1; i++){
			int coin = coins[i-1];
			for(int j = 1; j < n + 1; j++){
				sol[i][j] = sol[i-1][j];
				if(j == coin){
					sol[i][j] += 1 ;
				} else if(coin < j){
					sol[i][j] += sol[i][j - coin];
				} 
			}
		}
		
		//Print grid
//		for(int i = 0; i < coins.length + 1; i++){
//			for(int j = 0; j < n + 1; j++){
//				System.out.print(sol[i][j] +" ");
//			}
//			System.out.println();
//		}
		
		return sol[coins.length][n];
	}
	
	public static void main(String[] args) {
		CoinChange cc = new CoinChange();
		int n = 22;
		System.out.println(cc.findPossibleCoinsDP(n) + " sequences found with DP");
		cc.findPossibleCoins(0, n, 0, new ArrayList<Integer>() );
	}

}
