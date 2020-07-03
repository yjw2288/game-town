package com.gametown.page;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class PageDto<T> {
    private int pageNumber;
    private int totalPages;
}
