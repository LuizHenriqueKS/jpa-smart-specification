package io.github.zul.springsmartspecification.creator;

import io.github.zul.springsmartspecification.domain.JpaExampleChild;

public class JpaExampleChildCreator {

    public static JpaExampleChild createAlex() {
        return JpaExampleChild.builder()
                .fullName("Alex")
                .build();
    }

}
