package com.alpidi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidationResults {
	@JsonProperty("validationMessages") 
    public List<ValidationMessage> getValidationMessages() { 
		 return this.validationMessages; } 
    public void setValidationMessages(List<ValidationMessage> validationMessages) { 
		 this.validationMessages = validationMessages; } 
    List<ValidationMessage> validationMessages;
    @JsonProperty("isValid") 
    public boolean getIsValid() { 
		 return this.isValid; } 
    public void setIsValid(boolean isValid) { 
		 this.isValid = isValid; } 
    boolean isValid;
}
