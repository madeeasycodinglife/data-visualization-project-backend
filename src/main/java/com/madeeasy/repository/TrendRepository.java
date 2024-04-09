package com.madeeasy.repository;

import com.madeeasy.entity.Trend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrendRepository extends JpaRepository<Trend,Long> {
}
