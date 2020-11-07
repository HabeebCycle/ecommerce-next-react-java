package com.habeebcycle.marketplace.component.graphql;

import graphql.language.IntValue;
import graphql.language.StringValue;
import graphql.schema.*;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class GraphQLDateTimeScalar extends GraphQLScalarType {

    private static final String DEFAULT_NAME = "DateTime";

    public GraphQLDateTimeScalar() {
        this(DEFAULT_NAME);
    }

    public GraphQLDateTimeScalar(String name) {
        super(name, "A date-time string at UTC, such as 2007-12-03T10:15:30Z, compliant with the `date-time`",
            new Coercing<Instant, String>() {

                @Override
                public String serialize(Object input) {
                    return serializeDateTime(input);
                }

                @Override
                public Instant parseValue(Object input) {
                    return parseDateTimeValue(input);
                }

                @Override
                public Instant parseLiteral(Object input) {
                    return parseDateTimeFromAsLiteral(input);
                }
            }
        );
    }

    private static String serializeDateTime(Object input) {
        try{
            return ((Instant)input).toString();
        }catch (CoercingSerializeException ex){
            throw new CoercingSerializeException("Unable to serialize " + input + " as a DateTime value");
        }
    }

    private static Instant parseDateTimeValue(Object input) {
        if (input instanceof String) {
            return Instant.parse(((StringValue) input).getValue());
        } else if (input instanceof Instant) {
            return (Instant) input;
        }
        throw new CoercingParseValueException("Unable to parse variable value " + input + " as an email address");
    }

    private static Instant parseDateTimeFromAsLiteral(Object input) {
        if (input instanceof StringValue) {
            return Instant.parse(((StringValue) input).getValue());
        } else if (input instanceof IntValue) {
            return Instant.ofEpochMilli(((IntValue) input).getValue().longValue());
        }
        throw new CoercingParseLiteralException(
                "Value is not any DateTime : '" + input + "'"
        );
    }

}
