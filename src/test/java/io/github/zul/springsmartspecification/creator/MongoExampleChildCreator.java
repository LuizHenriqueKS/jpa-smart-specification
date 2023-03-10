package io.github.zul.springsmartspecification.creator;

import io.github.zul.springsmartspecification.domain.MongoExampleChild;

public class MongoExampleChildCreator {

    public static MongoExampleChild createAlex() {
        return MongoExampleChild.builder()
                .fullName("Alex")
                .build();
    }

}
