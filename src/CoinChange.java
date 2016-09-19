import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Given an amount of change (n) list all of the possibilities of coins that can
 * be used to satisfy the amount of change.
 * 
 * Example: n = 12
 * 
 * 1,1,1,1,1,1,1,1,1,1,1,1
 * 1,1,1,1,1,1,1,5
 * 1,1,5,5
 * 1,1,10
 * 
 * @author Eric
 *
 */
public class CoinChange {
	private int[] coins = { 1, 5, 10, 25 };

	/**
	 * Uses a greedy algorithm with backtracking to find the
	 * possible combinations
	 * 
	 * @param sum - the sum of the current combination
	 * @param n - the target
	 * @param pos - position for which coin to start at
	 * @param combination - the current combination
	 */
	public void findPossibleCoins(int sum, int n, int pos, List<Integer> combination) {
		// Begin at pos. We use pos so that we don't have duplicate combinations
		// in different orders ex: 1,1,10 is the same as 1,10,1 or 10,1,1
		// This is possible because when we are at a larger coin, we know that
		// combinations with smaller coins and the larger/current coin
		// have already been found, so we no longer need to consider them.
		// If we did consider them, we would have duplicates.
		// Therefore, pos allows you to only look ahead at larger coins,
		// ignoring smaller coins
		for (int i = pos; i < coins.length; i++) {
			int coin = coins[i];
			sum += coin; // Add the coin to the sum

			// If the sum is larger than n, then we have reached an invalid
			// combination.
			if (sum > n) {
				return;
			}

			combination.add(coin); // Add the coin to the current combination

			// If the sum is equal to n, then we have reached a valid
			// combination. Return from the recursive call
			// because any continuation would be unnecessary as adding more
			// coins or a larger coin would cause the sum to be larger than n.
			if (sum == n) {
				System.out.println(combination);
				combination.remove(combination.size() - 1);
				return;
			}

			findPossibleCoins(sum, n, pos, combination);

			// Remove the last coin
			combination.remove(combination.size() - 1);
			sum -= coin; // remove the coin from the sum
			pos++;
		}
	}

	/**
	 * Uses dynamic programming to find the possible combinations
	 * 
	 * @param n - the target
	 * @return
	 */
	public List<List<Integer>> findPossibleCoinsDP(int n) {
		/**
		 * Cell is a class to represent each cell in the n*m grid
		 * 
		 * @author Eric
		 *
		 */
		class Cell {
			// All of the possible combinations at each cell
			private List<List<Integer>> combinations = new ArrayList<List<Integer>>();

			List<List<Integer>> getCombinations() {
				return combinations;
			}

			void setCombinations(List<List<Integer>> combinations) {
				this.combinations = combinations;
			}

			void addCombination(List<Integer> combination) {
				if (combination != null) {
					combinations.add(new ArrayList<Integer>(combination));
				}
			}

		}

		// Create the grid
		Cell[][] sol = new Cell[coins.length + 1][n + 1];

		// Create new cells for the boundary values
		for (int i = 0; i < coins.length + 1; i++) {
			sol[i][0] = new Cell();
		}
		for (int i = 1; i < n + 1; i++) {
			sol[0][i] = new Cell();
		}

		for (int i = 1; i < coins.length + 1; i++) {
			int coin = coins[i - 1];
			for (int j = 1; j < n + 1; j++) {
				Cell cell = new Cell();

				Cell prevCoinCell = sol[i - 1][j];
				// Copy the combinations
				cell.setCombinations(prevCoinCell.getCombinations());

				if (j == coin) {
					// Only need to add the coin as a combination by itself in this case
					cell.addCombination(new ArrayList<Integer>(Arrays.asList(coin)));
				} else if (coin < j) {
					// In this case we need to get the previous cell minus the
					// size of the coin. Each combination needs to have
					// the current combination added to it
					Cell prevCell = sol[i][j - coin];
					for (List<Integer> prevCombination : prevCell.getCombinations()) {
						List<Integer> combination = new ArrayList<Integer>(prevCombination);
						combination.add(coin);
						cell.addCombination(combination);
					}
				}
				sol[i][j] = cell;
			}
		}
		return sol[coins.length][n].getCombinations();
	}

	public static void main(String[] args) {
		CoinChange cc = new CoinChange();
		int n = 21;
		List<List<Integer>> combinations = cc.findPossibleCoinsDP(n);
		System.out.println("Possible Combinations using Dynamic Programming");
		for(List<Integer> combination : combinations){
			System.out.println(combination);
		}
		System.out.println("\nPossible Combinations using Greedy Algorithm with Backtracking");
		cc.findPossibleCoins(0, n, 0, new ArrayList<Integer>());
	}

}
