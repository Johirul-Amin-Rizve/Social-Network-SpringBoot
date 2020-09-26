package com.goruslan.socialgeeking.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Location {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;
}