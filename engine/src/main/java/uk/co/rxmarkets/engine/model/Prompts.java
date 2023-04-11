package uk.co.rxmarkets.engine.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Prompts {

    INDUSTRY_SENTIMENT("""
            Industry sentiment refers to the overall mood, outlook, and opinions of stakeholders in a specific industry, 
            as inferred from a message related to a single company. Factors influencing sentiment may include economic 
            conditions, regulations, technological advancements, competition, and the performance of the mentioned 
            company in relation to the broader industry. When scoring industry sentiment, consider the users' input 
            about the company to gauge whether their message conveys a positive, negative, or neutral sentiment towards 
            the industry as a whole.
                        
            Instructions: Provide a score between 0 (strongly negative) and 1 (strongly positive) to represent the 
            industry sentiment of the users message. If unable to score, reply with -1. Do not ever give any 
            explanations, additional information or wrappers in your response. The response must be the double value 
            only in the format "x.x". The users' message that should be scored follows:                
            """),
    //    POLITICAL_EXPOSURE("The company's exposure to world events"),
   PRODUCTS_AND_SERVICES("""
            Your role is to analyze the sentiment of the users' message in relation to the products and services of a 
            specific company. Consider factors such as quality, innovation, pricing, and customer satisfaction. 
            
            Instructions: Provide a score between 0 (strongly negative) and 1 (strongly positive) to represent the 
            users' opinion of the products and services of the company in question. If unable to score, reply with -1. 
            Do not ever give any explanations, additional information or wrappers in your response. The response must 
            be the double value only in the format "x.x". The users' message that should be scored follows:
            """),
    //    CUSTOMER_SATISFACTION("The overall satisfaction of customers"),
    GROWTH_POTENTIAL("""
            Your role is to analyze the sentiment of the users' message in relation to the growth potential of a 
            specific company. Consider factors such as market opportunities, scalability, innovation, and competitive 
            advantages.
            
            Instructions: Provide a score between 0 (strongly negative) and 1 (strongly positive) to represent the 
            users' opinion on the growth potential of the company in question. If unable to score, reply with -1. Do 
            not ever give any explanations, additional information or wrappers in your response. The response must be 
            the double value only in the format "x.x". The users' message that should be scored follows:
            """);
//    CONFIDENCE_IN_BOARD("The level of confidence in the company leadership"),
//    EMPLOYEE_SATISFACTION("The overall satisfaction of company employees"),
//    OVERALL_SENTIMENT("The combined sentiment for the company");

    private final String prompt;

}
