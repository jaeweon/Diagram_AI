package com.board.flowai.di;

import java.util.*;

public class TopologySorter {

    public static List<String> topologicalSort(List<Edge> edges) {
        Set<String> nodes = new HashSet<>();
        for (Edge e : edges) {
            nodes.add(e.getSource());
            nodes.add(e.getTarget());
        }

        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> inDegree = new HashMap<>();
        for (String node : nodes) {
            graph.put(node, new ArrayList<>());
            inDegree.put(node, 0);
        }
        for (Edge e : edges) {
            String s = e.getSource();
            String t = e.getTarget();
            graph.get(s).add(t);
            inDegree.put(t, inDegree.get(t) + 1);
        }

        Queue<String> queue = new LinkedList<>();
        for (String node : nodes) {
            if (inDegree.get(node) == 0) {
                queue.offer(node);
            }
        }

        List<String> sorted = new ArrayList<>();
        while (!queue.isEmpty()) {
            String cur = queue.poll();
            sorted.add(cur);
            for (String nxt : graph.get(cur)) {
                int deg = inDegree.get(nxt) - 1;
                inDegree.put(nxt, deg);
                if (deg == 0) {
                    queue.offer(nxt);
                }
            }
        }
        return sorted;
    }
}
