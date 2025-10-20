package Assignment_4_Smart_Traffic_Management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SmartTrafficManagement {

	static final int INF = 9999;
	
	static void dijkstra(int[][] graph, int src, int dest){
		int V = graph.length;
		int[] dist = new int[V];
		boolean[] visited = new boolean[V];
		int[] parent = new int[V];
		
		Arrays.fill(dist, INF);
		Arrays.fill(parent, -1);
		dist[src] = 0;
		
		for(int i=0; i < V-1; i++){
			int u = minDistance(dist, visited);
			if(u == -1)
				break;
			visited[u] = true;
			
			for(int v=0; v < V; v++){
				if(!visited[v] && graph[u][v] != INF &&
						dist[u] + graph[u][v] < dist[v]){
					dist[v] = dist[u] + graph[u][v];
					parent[v] = u;
				}
			}
		}
		
		printResult(src, dest, dist, parent);
	}
	
	static int minDistance(int [] dist, boolean[] visited){
		int min = INF, minIndex = -1;
		for(int v=0; v < dist.length; v++){
			if(!visited[v] && dist[v] < min){
				min = dist[v];
				minIndex = v;
			}
		}
		return minIndex;
	}
	
	static void printResult(int src, int dest, int[] dist, int[] parent){
		System.out.println("\n=== EMERGENCY ROUTE CALCULATION ===");
		if(dist[dest] == INF){
			System.out.println("No Path Available!!");
			return;
		}
		System.out.println("Shortest Travel Time: " + dist[dest] + " minutes");
		
		List<Integer> path = new ArrayList<>();
		for(int v = dest; v != -1; v = parent[v])
			path.add(v);
		Collections.reverse(path);
		
		System.out.println("Optimal Route: " + path);
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter Number of Intersections: ");
		int V = sc.nextInt();
		int[][] graph = new int[V][V];
		
		for(int i=0; i < V;i++)
			Arrays.fill(graph[i], INF);
		
		System.out.print("Enter Number of Roads: ");
		int E = sc.nextInt();
		System.out.println("Enter Roads (src dest time): ");
		for(int i=0; i < E; i++){
			int u = sc.nextInt();
			int v = sc.nextInt();
			int w = sc.nextInt();
			graph[u][v] = w;
			graph[v][u] = w;
		}
		
		System.out.print("Enter Ambulance Location (source): ");
		int src = sc.nextInt();
		System.out.print("Enter Hospital Location (destination): ");
		int dest = sc.nextInt();
		
		dijkstra(graph, src, dest);
		
		System.out.print("\nSimulate Traffic Update? (1=yes / 0=no): ");
		int choice = sc.nextInt();
		if(choice == 1){
			System.out.print("Enter Road to update (src dest new_time): ");
			int u = sc.nextInt();
			int v = sc.nextInt();
			int newW = sc.nextInt();
			graph[u][v] = newW;
			graph[v][u] = newW;
			System.out.println("Traffic Updated! Recalculating...");
			dijkstra(graph, src, dest);
		}
		sc.close();
	}
}

