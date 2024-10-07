package com.example.musubi.model.dto;

public class SosRequestDto {
    private final long guardianId;

    public SosRequestDto(long guardianId) {
        this.guardianId = guardianId;
    }

    public long getGuardianId() {
        return guardianId;
    }
}
