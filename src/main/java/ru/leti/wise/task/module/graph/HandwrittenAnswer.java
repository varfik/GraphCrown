package ru.leti.wise.task.module.graph;

import ru.leti.wise.task.graph.model.main.Graph;

public interface HandwrittenAnswer extends GraphModule {

    boolean run(Graph graph, String answer);
}
