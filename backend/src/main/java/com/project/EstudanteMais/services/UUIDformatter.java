package com.project.EstudanteMais.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDformatter {
  public String formatUuid(UUID uuid) {
    String uuidString = uuid.toString().replace("-", "");
    return String.format("%s-%s-%s-%s-%s",
            uuidString.substring(0, 8),
            uuidString.substring(8, 12),
            uuidString.substring(12, 16),
            uuidString.substring(16, 20),
            uuidString.substring(20));
  }
}
