package com.tarpa.tourism.security.model;

public enum UserRole {
    SUPER_ADMIN,
    MANAGING_DIRECTOR,
    OPERATIONS_MANAGER,
    SALES_TRAVEL_PLANNER,
    PERMITS_DOCUMENTATION_OFFICER,

    // Field staff accounts (if mobile app/portal access is needed)
    LEAD_GUIDE_SIRDAR,
    BASE_CAMP_MANAGER,
    GOVERNMENT_LIAISON_OFFICER
}