package com.github.cleytonorocha.vue_dashboard_back.helper;

import java.io.Serializable;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

/**
 * ListData
 */

@Getter
@Setter
public class ListData implements Serializable {
    private static final String DEFAULT_PAGE = "0";
    private static final String DEFAULT_LINES_PER_PAGE = "10";
    private static final String DEFAULT_ORDER_BY = "id";
    private static final String DEFAULT_DIRECTION = "ASC";

    @Min(0)
    private Integer page = Integer.parseInt(DEFAULT_PAGE);

    @Min(1)
    private Integer linesPerPage = Integer.parseInt(DEFAULT_LINES_PER_PAGE);

    private String orderBy = DEFAULT_ORDER_BY;

    private String direction = DEFAULT_DIRECTION;
}