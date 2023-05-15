package com.safar.repository;

import com.safar.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus,Integer> {
   public List<Bus> findByBusType(String busType);

   public Bus findByRouteFromAndRouteTo(String routeFrom, String routeTo);
}
