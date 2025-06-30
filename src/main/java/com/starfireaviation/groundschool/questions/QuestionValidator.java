package com.starfireaviation.groundschool.questions;

import org.springframework.stereotype.Component;

@Component
public class QuestionValidator {

    private final Questions questions;

    public QuestionValidator(Questions questions) {
        this.questions = questions;
    }

    public void exists(Long id) throws Exception {
        if (!questions.exists(id)) {
            throw new Exception();
        }
    }
}
