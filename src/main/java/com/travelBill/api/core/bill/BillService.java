package com.travelBill.api.core.bill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository BillRepository) {
        this.billRepository = BillRepository;
    }

    public List<Bill> getAll() {
        return billRepository.findAll();
    }
}
