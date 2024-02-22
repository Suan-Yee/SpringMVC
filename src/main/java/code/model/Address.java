package code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder
public class Address {

    private int id;
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;

}
