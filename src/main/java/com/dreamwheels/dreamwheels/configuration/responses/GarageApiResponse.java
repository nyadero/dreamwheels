package com.dreamwheels.dreamwheels.configuration.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GarageApiResponse {
    private Object data;
    private String message;
    private ResponseType status;
}
