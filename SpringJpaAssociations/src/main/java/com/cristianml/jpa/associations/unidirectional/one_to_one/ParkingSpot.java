package com.cristianml.jpa.associations.unidirectional.one_to_one;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "uni_parking_spot_one_to_one")
@Table(name = "uni_parking_spot_one_to_one")
public class ParkingSpot {

}
