package com.dreamwheels.dreamwheels.configuration.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GarageApiResponse<T> {
    private Data<T> data;
    private String message;
    private ResponseType status;
}
