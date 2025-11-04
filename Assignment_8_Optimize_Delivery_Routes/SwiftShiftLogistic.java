package Assignment_8_Optimize_Delivery_Routes;

import java.util.Arrays;
import java.util.Scanner;

public class SwiftShiftLogistic {

	private static int N; //Number of cities
	private static int[][] costMatrix; //Cost Matrix (distance/fuel/time)
	
	private static int calculateInitialBound(int[][] matrix){
		int bound = 0;
		for(int i = 0; i < N; i++){
			int firstMin = Integer.MAX_VALUE;
			int secondMin = Integer.MAX_VALUE;
			for(int j = 0; j < N; j++){
				if(i != j){
					if(matrix[i][j] <= firstMin){
						secondMin = firstMin;
						firstMin = matrix[i][j];
					}
					else if(matrix[i][j] <= secondMin){
						secondMin = matrix[i][j];
					}
				}
			}
			bound += (firstMin + secondMin);
		}
		return (bound % 2 == 0) ? bound / 2 : bound / 2+1;
	}
	
	private static void tspRecursion(int level, int currCost, int bound, int[] path, boolean[] visited, int[] finalPath, int[] finalCost){
		if(level == N){
			int totalCost = currCost + costMatrix[path[level - 1]][path[0]];
			if(totalCost < finalCost[0]){
				finalCost[0] = totalCost;
				System.arraycopy(path, 0, finalPath, 0, N);
			}
			return;
		}
		
		for(int i = 0; i < N; i++){
			if(!visited[i]){
				int tempBound = bound;
				currCost += costMatrix[path[level - 1]][i];
				
				if(level == 1)
					bound -= ((getFirstMin(costMatrix, path[level-1])) + getFirstMin(costMatrix, i)) / 2;
				
				else
					bound -= ((getSecondMin(costMatrix, path[level-1])) + getFirstMin(costMatrix, i) / 2);
				
				if(currCost + bound < finalCost[0]){
					path[level] = i;
					visited[i] = true;
					tspRecursion(level + 1, currCost, bound, path, visited, finalPath, finalCost);
				}
				
				//BackTrack
				currCost -= costMatrix[path[level - 1]][i];
				bound =  tempBound;
				Arrays.fill(visited, false);
				for(int j = 0; j < level; j++)
					visited[path[j]] = true;
			}
		}
	}
	
	private static int getFirstMin(int[][] matrix, int i){
		int min = Integer.MAX_VALUE;
		for(int k = 0; k < N; k++){
			if(i != k && matrix[i][k] < min)
				min = matrix[i][k];
		}
		return min;
	}
	
	private static int getSecondMin(int[][] matrix, int i){
		int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
		for(int j = 0; j < N; j++){
			if(i == j)
				continue;
			if(matrix[i][j] <= first){
				second = first;
				first = matrix[i][j];
			}
			else if(matrix[i][j] <= second){
				second = matrix[i][j];
			}
		}
		return second;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter Number of Cities: ");
		N = sc.nextInt();
		
		costMatrix = new int[N][N];
		System.out.println("Enter Cost Matrix (distance/fuel/time between Cities): ");
		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++)
				costMatrix[i][j] = sc.nextInt();
		}
		
		int[] path = new int[N];
		boolean[] visited = new boolean[N];
		int[] finalPath = new int[N];
		int[] finalCost = {Integer.MAX_VALUE};
		
		//Start from City 0
		path[0] = 0;
		visited[0] = true;
		int bound = calculateInitialBound(costMatrix);
		tspRecursion(1, 0, bound, path, visited, finalPath, finalCost);
		
		//Display Results
		System.out.println("\n=== Optimized Delivery Route ===");
		for(int i = 0; i < N; i++)
			System.out.print(finalPath[i] + " -> ");
		System.out.println(finalPath[0]);
		System.out.println("Minimum Delivery Cost: " + finalCost[0]);
	}
}
