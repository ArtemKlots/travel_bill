package com.travelBill.api.core.bill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;

    @Autowired
    public BillServiceImpl(BillRepository BillRepository) {
        this.billRepository = BillRepository;
    }

    public List<Bill> getAll() {
        return billRepository.findAll();
    }

    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }

    public List<Bill> selectTop10ByUserIdOrderByCreatedAtDesc(Long id) {
        return billRepository.findTop10ByUserIdOrderByCreatedAtDesc(id);
    }
}
