package ru.leti.wise.task.graph.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.leti.wise.task.graph.model.Graph;

import java.io.IOException;


public class JsonParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static Graph parseGraph(String graph) {
        try {
            return objectMapper.readValue(graph, Graph.class);
        } catch (IOException e) {
            //TODO: придумать нормальные ошибки при ошибке чтения графа
            throw new RuntimeException(e.getCause());
        }

    }
}
