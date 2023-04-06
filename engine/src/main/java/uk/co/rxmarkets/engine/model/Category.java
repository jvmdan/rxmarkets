package uk.co.rxmarkets.engine.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Category {

    INDUSTRY_SENTIMENT("The outlook for the industry as a whole");
//    POLITICAL_EXPOSURE("The company's exposure to world events"),
//    PRODUCTS_AND_SERVICES("The quality of the company's products & services"),
//    CUSTOMER_SATISFACTION("The overall satisfaction of customers"),
//    GROWTH_POTENTIAL("The belief that the company has room to grow");
//    CONFIDENCE_IN_BOARD("The level of confidence in the company leadership"),
//    EMPLOYEE_SATISFACTION("The overall satisfaction of company employees"),
//    OVERALL_SENTIMENT("The combined sentiment for the company");

    private final String description;

}
