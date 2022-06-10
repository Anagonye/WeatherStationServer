package org.example.measurements;

import java.util.Optional;


public interface MeasurementDao<E extends Measurement> {

    void save(E e);

    Optional<E> findById(Long id);


}
