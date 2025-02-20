package com.board.flowai.di;

import java.util.List;

public class DefaultMermaidDiagramFormatter implements DiagramFormatter {

    @Override
    public String format(List<Edge> edges) {
        StringBuilder sb = new StringBuilder();
        sb.append("graph TD\n");
        for (Edge edge : edges) {
            sb.append("  ")
                    .append(edge.getSource())
                    .append(" -->|")
                    .append(edge.getLabel())
                    .append("| ")
                    .append(edge.getTarget())
                    .append("\n");
        }
        return sb.toString();
    }
}
