package com.busmatelk.backend.dto;

import lombok.*;


import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class fleetOperatorDTO {

    private String operator_type;
    private String organization_name;
    private String region;
    private String registration_id;
    private List<String> contact_details;

    // Getters
//    public String getOperatorType() {
//        return operator_type;
//    }
//
//    public String getOrganizationName() {
//        return organization_name;
//    }
//    public String getRegion() {
//        return region;
//    }
//
//    public String getRegistrationId() {
//        return registration_id;
//    }
//
//    public List<String> getContactDetails() {
//        return contact_details;
//    }

    // Setters
//    public void setOperatorType(String operatorType) {
//        this.operator_type = operatorType;
//    }
//
//    public void setOrganizationName(String organizationName) {
//        this.organization_name = organizationName;
//    }
//
//    public void setRegistrationId(String registrationId) {
//        this.registration_id = registrationId;
//    }
//
//    public void setContactDetails(List<String> contactDetails) {
//        this.contact_details = contactDetails;
//    }
}


