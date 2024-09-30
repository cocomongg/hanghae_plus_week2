package io.hhplus.tdd.lecture.domain.lecture.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Lecture {
    @Id
    private long id;
}
