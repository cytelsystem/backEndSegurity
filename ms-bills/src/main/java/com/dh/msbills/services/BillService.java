package com.dh.msbills.services;

import com.dh.msbills.models.Bill;
import com.dh.msbills.repositories.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository repository;

    public String guardar(Bill o){
        String respuesta = null;
        if (repository.save(o) != null){
            respuesta = "ok";
        }
        return respuesta;
    }

    public List<Bill> getAllBill() {
        return repository.findAll();
    }
}
