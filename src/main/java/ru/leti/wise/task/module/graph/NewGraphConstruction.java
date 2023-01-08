package ru.leti.wise.task.module.graph;

import ru.leti.wise.task.graph.model.main.Graph;

public interface NewGraphConstruction extends GraphModule {

    boolean run(Graph graphQuestion, Graph graphAnswer);
}
