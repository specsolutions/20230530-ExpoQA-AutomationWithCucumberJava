package eu.specsolutions.bddcourse.geekpizza.api_tests.support;

import io.cucumber.java.ParameterType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeRegistryConfiguration {

    @ParameterType("\\d\\d?\\:\\d\\d")
    public LocalTime time(String s){
        return LocalTime.parse(s);
    }

    @ParameterType(".*")
    public LocalDate date(String s){
        return parseDate(s);
    }

    private LocalDate parseDate(String s){
        if (s.toLowerCase().equals("today"))
            return LocalDate.now();
        if (s.toLowerCase().equals("tomorrow"))
            return LocalDate.now().plusDays(1);

        Pattern daysLater = Pattern.compile("(?<days>\\d+) days later");
        Matcher matcher = daysLater.matcher(s);
        if (matcher.find())
            return LocalDate.now().plusDays(Integer.parseInt(matcher.group("days")));

        return LocalDate.parse(s);
    }
}

