package com.dh.msbills.repositories;

import com.dh.msbills.models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;



public interface BillRepository extends JpaRepository<Bill, String> {

    @Query("SELECT b FROM Bill b WHERE b.customerBill = :idBill")
    List<Bill> findByidBill(String idBill);


}
