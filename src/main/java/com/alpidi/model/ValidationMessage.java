package com.alpidi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidationMessage {
	@JsonProperty("source") 
    public String getSource() { 
		 return this.source; } 
    public void setSource(String source) { 
		 this.source = source; } 
    String source;
    @JsonProperty("code") 
    public String getCode() { 
		 return this.code; } 
    public void setCode(String code) { 
		 this.code = code; } 
    String code;
    @JsonProperty("text") 
    public String getText() { 
		 return this.text; } 
    public void setText(String text) { 
		 this.text = text; } 
    String text;
}
