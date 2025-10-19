package Assignment_5_Multi_Stage_Delivery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwiftCargoOptimization {

	static class Edge {
		String from, to;
		double cost;
		
		Edge(String from, String to, double cost){
			this.from = from;
			this.to = to;
			this.cost = cost;
		}
	}
	
	static class MultiStageGraph {
		int stages;
		Map<String, Integer> nodeStage;
		Map<String, List<Edge>> adjList;
		
		MultiStageGraph(int stages){
			this.stages = stages;
			this.nodeStage = new HashMap<>();
			this.adjList = new HashMap<>();
		}
		
		void addNode(String node, int stage){
			nodeStage.put(node, stage);
			if(!adjList.containsKey(node)){
				adjList.put(node, new ArrayList<>());
			}
		}
		
		void addEdge(String from, String to, double cost){
			if(!adjList.containsKey(from))
				adjList.put(from, new ArrayList<>());
			adjList.get(from).add(new Edge(from,to,cost));
		}
		
		void updateEdge(String from, String to, double newCost){
			if(adjList.containsKey(from)){
				for(Edge e : adjList.get(from)){
					if(e.to.equals(to)){
						e.cost = newCost;
						break;
					}
				}
			}
		}
	}
	
	static class RouteResult {
		List<String> path;
		double totalCost;
		
		public RouteResult(List<String> path, double totalCost) {
			this.path = path;
			this.totalCost = totalCost;
		}
	}
	
	static RouteResult findOptimalRoute(MultiStageGraph graph){
		int n = graph.stages;
		Map<String, Double>[] dp = new HashMap[n];
		Map<String, String>[] parent = new HashMap[n];
		
		for(int i=0; i<n; i++){
			dp[i] = new HashMap<>();
			parent[i] = new HashMap<>();
		}
		
		for(String node : graph.nodeStage.keySet()){
			if(graph.nodeStage.get(node) == 0)
				dp[0].put(node, 0.0);
		}
		
		for(int stage=0; stage < n-1; stage++){
			for(String u : dp[stage].keySet()){
				if(graph.adjList.containsKey(u)){
					for(Edge e : graph.adjList.get(u)){
						int nextStage = graph.nodeStage.getOrDefault(e.to, -1);
						if(nextStage == stage + 1){
							double newCost = dp[stage].get(u) + e.cost;
							if(!dp[nextStage].containsKey(e.to) || newCost < dp[nextStage].get(e.to)){
								dp[nextStage].put(e.to, newCost);
								parent[nextStage].put(e.to, u);
							}
						}
					}
				}
			}
		}
		
		double minCost = Double.MAX_VALUE;
		String endNode = null;
		for(Map.Entry<String, Double> entry : dp[n - 1].entrySet()){
			if(entry.getValue() < minCost){
				minCost = entry.getValue();
				endNode = entry.getKey();
			}
		}
		
		List<String> path = new ArrayList<>();
		String cur = endNode;
		for(int stage = n - 1; stage >= 0; stage--){
			if(cur == null)
				break;
			path.add(cur);
			cur = parent[stage].get(cur);
		}
		Collections.reverse(path);
		
		return new RouteResult(path, minCost);
	}
	
	public static void main(String[] args) {
		MultiStageGraph graph = new MultiStageGraph(3);
		
		// Stage 0 (Warehouses)
		graph.addNode("W1", 0);
		graph.addNode("W2", 0);
		
		// Stage 1 (Transit Hubs)
		graph.addNode("H1", 1);
		graph.addNode("H2", 1);
		graph.addNode("H3", 1);
		
		// Stage 2 (Delivery Cities)
		graph.addNode("D1", 2);
		graph.addNode("D2", 2);
		
		// Edges: Warehouses -> Hubs
		graph.addEdge("W1", "H1", 10);
		graph.addEdge("W1", "H2", 15);
		graph.addEdge("W2", "H2", 5);
		graph.addEdge("W2", "H3", 20);
		
		// Edges: Hubs -> Delivery
		graph.addEdge("H1", "D1", 30);
		graph.addEdge("H1", "D2", 50);
		graph.addEdge("H2", "D1", 20);
		graph.addEdge("H2", "D2", 30);
		graph.addEdge("H3", "D1", 10);
		graph.addEdge("H3", "D2", 40);
		
		System.out.println("=== SWIFTCARGO LOGISTICS ROUTE OPTIMIZATION ===");
		RouteResult result = findOptimalRoute(graph);
		System.out.println("Optimal Route: " + result.path);
		System.out.println("Total Cost: " + result.totalCost);
		
		System.out.println("\nUpdating cost due to traffic on W2 -> H2...");
		graph.updateEdge("W2", "H2", 35);
		result = findOptimalRoute(graph);
		System.out.println("New Optimal Route: " + result.path);
		System.out.println("New Total Cost: " + result.totalCost);
		
		System.out.println("\nSimulating road closure: H3 -> D1");
		graph.updateEdge("H3", "D1", 9999);
		result = findOptimalRoute(graph);
		System.out.println("Updated Optimal Route: " + result.path);
		System.out.println("Updated Total Cost: " + result.totalCost);
	}

}
