package com.anish.emailservice.helper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String from;
    private String content;
    private List<String> files;
    private String subjects;
}
