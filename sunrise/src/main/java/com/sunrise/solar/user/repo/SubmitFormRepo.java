package com.sunrise.solar.user.repo;


import com.sunrise.solar.user.SubmitFormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmitFormRepo extends JpaRepository<SubmitFormEntity, Long> {
}
