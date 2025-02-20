package com.board.flowai.di;

import java.util.*;

public class GraphComponents {

    /**
     * 주어진 Edge 목록을 바탕으로 무방향 그래프를 구성하고,
     * 연결 요소(connected components)를 DFS로 찾는다.
     */
    public static List<Set<String>> findConnectedComponents(List<Edge> edges) {
        Set<String> allNodes = new HashSet<>();
        for (Edge e : edges) {
            allNodes.add(e.getSource());
            allNodes.add(e.getTarget());
        }

        // 무방향 인접 리스트 구성
        Map<String, List<String>> graph = new HashMap<>();
        for (String node : allNodes) {
            graph.put(node, new ArrayList<>());
        }
        for (Edge e : edges) {
            graph.get(e.getSource()).add(e.getTarget());
            graph.get(e.getTarget()).add(e.getSource());
        }

        List<Set<String>> components = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        for (String node : allNodes) {
            if (!visited.contains(node)) {
                Set<String> comp = new HashSet<>();
                dfs(node, graph, visited, comp);
                components.add(comp);
            }
        }
        return components;
    }

    private static void dfs(String node, Map<String, List<String>> graph, Set<String> visited, Set<String> comp) {
        visited.add(node);
        comp.add(node);
        for (String nxt : graph.get(node)) {
            if (!visited.contains(nxt)) {
                dfs(nxt, graph, visited, comp);
            }
        }
    }
}
