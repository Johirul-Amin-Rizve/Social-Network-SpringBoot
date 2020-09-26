package com.goruslan.socialgeeking.repository;

import com.goruslan.socialgeeking.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
