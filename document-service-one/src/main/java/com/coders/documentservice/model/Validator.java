package com.coders.documentservice.model;

import java.util.HashMap;
import java.util.Map;

public class Validator {

    private Map<String, Boolean> validationMap;

    public Validator() {
        this.validationMap = new HashMap<>();
    }

    public Validator(Map<String, Boolean> validationMap) {
        this.validationMap = validationMap;
    }

    public Map<String, Boolean> getValidationMap() {
        return validationMap;
    }

    public void setValidationMap(Map<String, Boolean> validationMap) {
        this.validationMap = validationMap;
    }
}
