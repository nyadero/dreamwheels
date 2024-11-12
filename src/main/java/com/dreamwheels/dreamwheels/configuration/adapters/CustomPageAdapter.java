package com.dreamwheels.dreamwheels.configuration.adapters;

import com.dreamwheels.dreamwheels.configuration.responses.CustomPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@SuppressWarnings("unchecked")
public class CustomPageAdapter<PageData, CustomPageData> implements EntityAdapter<Page<PageData>, CustomPageResponse<CustomPageData>>{
    @Override
    public CustomPageResponse<CustomPageData> toBusiness(Page<PageData> pageData) {
        return CustomPageResponse.<CustomPageData>builder()
                .currentPage(pageData.getNumber())
                .size(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getNumberOfElements())
                .content((List<CustomPageData>) pageData.getContent())
                .build();
    }
}
