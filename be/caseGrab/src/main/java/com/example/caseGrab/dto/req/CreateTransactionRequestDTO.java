package com.example.caseGrab.dto.req;

import org.hibernate.validator.constraints.UUID;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTransactionRequestDTO {
    @NotEmpty(message = "Product Id is required")
    @UUID
    private String productId;
}
