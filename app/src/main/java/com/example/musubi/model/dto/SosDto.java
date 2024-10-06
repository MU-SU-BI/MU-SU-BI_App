package com.example.musubi.model.dto;

public class SosDto {
    private final long guardianId;

    public SosDto(long guardianId) {
        this.guardianId = guardianId;
    }

    public long getGuardianId() {
        return guardianId;
    }
}
