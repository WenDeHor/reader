package com.example.reader.repository;

import com.example.reader.model.BaseReserve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveAssetsRepository extends JpaRepository<BaseReserve, Integer> {
}
