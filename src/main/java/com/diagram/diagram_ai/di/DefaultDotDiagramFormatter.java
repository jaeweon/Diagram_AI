package com.board.flowai.di;

import java.util.List;

public class DefaultDotDiagramFormatter implements DiagramFormatter {
    @Override
    public String format(List<Edge> edges) {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph DI {\n");
        for (Edge edge : edges) {
            sb.append("  \"").append(edge.getSource()).append("\" -> \"")
                    .append(edge.getTarget()).append("\" [ label=\"")
                    .append(edge.getLabel()).append("\" ];\n");
        }
        sb.append("}\n");
        return sb.toString();
    }
}
