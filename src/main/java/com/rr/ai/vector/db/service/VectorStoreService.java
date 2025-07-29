package com.rr.ai.vector.db.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rr.ai.vector.model.Book;
import com.rr.ai.vector.util.BookDataPopulator;
import lombok.SneakyThrows;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VectorStoreService {

    private  final VectorStore vectorStore;

    private final ChatClient chatClient;

    private final ObjectMapper mapper = new ObjectMapper();

    public VectorStoreService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore){
        this.vectorStore = vectorStore;
        this.chatClient = chatClientBuilder.build();
    }

    @SneakyThrows
    public String init(){
        var books = BookDataPopulator.populate();

        for(var book : books){
            var doc = Document.builder()
                    .id(book.id())
                    .text(mapper.writeValueAsString(book)).build();
            vectorStore.add(List.of(doc));
        }
        return "data initialised";
    }

    public String semanticSearch(String query) {
        String context = vectorStore.similaritySearch(SearchRequest.builder()
                        .query(query)
                        .topK(1)
                        .build())
                .stream()
                .map(Document::getText)
                .reduce("", (a, b) -> a + b + "\n");

        return chatClient.prompt()
                .system(context)
                .user(query)
                .call()
                .content();
    }
}
