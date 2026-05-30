package com.github.anjalin.hospitalManagement.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PatientHistoryRAGService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public PatientHistoryRAGService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        this.chatClient = chatClientBuilder.build();
        this.vectorStore = vectorStore;
    }

    /**
     * Call this whenever a doctor saves a new appointment note.
     * It converts the text into a vector embedding and saves it to PostgreSQL.
     */
    public void ingestAppointmentNote(Long patientId, String clinicalNote) {
        Document document = new Document(clinicalNote, Map.of("patientId", patientId.toString()));
        vectorStore.add(List.of(document));
    }

    /**
     * Call this when a doctor asks a question about a specific patient.
     */
    public String askAboutPatientHistory(Long patientId, String question) {
        // 1. Semantic Search: Find the most relevant past notes for THIS specific patient
        List<Document> relevantDocs = vectorStore.similaritySearch(
                SearchRequest.query(question)
                        .withTopK(3) // Get the top 3 most relevant notes
                        .withFilterExpression("patientId == '" + patientId + "'") // Strict metadata filtering
        );

        // 2. Combine the retrieved notes into a single context string
        String context = relevantDocs.stream()
                .map(Document::getContent)
                .collect(Collectors.joining("\n\n"));

        // 3. Prompt the LLM using the retrieved context (RAG)
        String systemPrompt = "You are a clinical AI assistant helping a doctor review patient history. " +
                "Answer the doctor's question based strictly on the following clinical notes. " +
                "If the answer is not contained in the notes, state that clearly.\n\n" +
                "CONTEXT:\n" + context;

        return chatClient.prompt()
                .system(systemPrompt)
                .user(question)
                .call()
                .content();
    }
}