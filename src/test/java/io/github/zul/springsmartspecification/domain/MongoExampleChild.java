package io.github.zul.springsmartspecification.domain;

import jakarta.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MongoExampleChild {

    @Id
    private String id;
    private String fullName;

}
