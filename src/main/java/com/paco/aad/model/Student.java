package com.paco.aad.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@Data
@ToString
@NoArgsConstructor
public class Student {

    Integer Id;
    String FirstName;
    String LastName;
    Date BirthDate;
    Double AverageGrade;
}
