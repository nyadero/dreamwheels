package com.dreamwheels.dreamwheels.configuration.responses;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CustomPageResponse<T> {
    private List<T> content;
    private int currentPage;
    private int totalPages;
    private int totalElements;
    private int size;
}
