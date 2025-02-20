package com.diagram.diagram_ai.di;

import java.util.List;

public class DefaultPlantUmlDiagramFormatter implements DiagramFormatter {

    @Override
    public String format(List<Edge> edges) {
        // 여기는 단일 연결 요소에 대한 PlantUML 소스를 생성하는 기본 포매터 코드
        // participant 선언은 TopologySorter로 정렬된 순서대로 한다고 가정
        List<String> sortedNodes = TopologySorter.topologicalSort(edges);

        StringBuilder sb = new StringBuilder();
        sb.append("@startuml\n");
        for (String node : sortedNodes) {
            sb.append("participant \"").append(node).append("\"\n");
        }
        for (Edge e : edges) {
            sb.append("\"").append(e.getSource())
                    .append("\" --> \"").append(e.getTarget())
                    .append("\" : ").append(e.getLabel()).append("\n");
        }
        sb.append("@enduml\n");
        return sb.toString();
    }
}
