package com.github.anjalin.hospitalManagement.service;

import com.github.anjalin.hospitalManagement.dto.TriageResponseDto;
import com.github.anjalin.hospitalManagement.entity.Department;
import com.github.anjalin.hospitalManagement.repository.DepartmentRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AiTriageService {

    private final ChatClient chatClient;
    private final DepartmentRepository departmentRepository;

    public AiTriageService(ChatClient.Builder chatClientBuilder, DepartmentRepository departmentRepository) {
        this.chatClient = chatClientBuilder.build();
        this.departmentRepository = departmentRepository;
    }

    public TriageResponseDto analyzeSymptoms(String symptoms) {
        // 1. Fetch live departments from your PostgreSQL database
        List<String> availableDepartments = departmentRepository.findAll()
                .stream()
                .map(Department::getName)
                .collect(Collectors.toList());

        // 2. Format them into a comma-separated string for the AI
        String validDepartmentsStr = String.join(", ", availableDepartments);

        // 3. Create a dynamic system prompt that enforces strict adherence to your DB
        String systemPrompt = String.format(
                "You are a professional medical triage assistant for a hospital. " +
                        "Analyze the patient's symptoms and return a structured JSON response containing: " +
                        "1) 'recommendedDepartment': You MUST choose ONLY from this exact list of available departments: [%s]. " +
                        "If none fit perfectly, pick the closest match. " +
                        "2) 'urgencyLevel': (LOW, MEDIUM, HIGH, CRITICAL). " +
                        "3) 'generalAdvice': A brief, safe piece of general advice with a disclaimer that you are not a doctor.",
                validDepartmentsStr
        );

        // 4. Call the LLM with the dynamic context
        return this.chatClient.prompt()
                .system(systemPrompt)
                .user(symptoms)
                .call()
                .entity(TriageResponseDto.class);
    }
}