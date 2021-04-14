package com.travelBill.splitBill.core.bill;

import com.travelBill.TravelBillException;
import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserService;
import com.travelBill.splitBill.core.AccessDeniedException;
import com.travelBill.splitBill.core.ClosedBillException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class SbBillService {
    private final SbBillRepository SBBillRepository;
    private final UserService userService;

    public SbBillService(SbBillRepository SBBillRepository,
                         UserService userService) {
        this.SBBillRepository = SBBillRepository;
        this.userService = userService;
    }

    public List<SbBill> getAll() {
        return this.SBBillRepository.findAll();
    }

    public List<SbBill> getBillsByOwnerId(Long id) {
        return this.SBBillRepository.getSbBillsByOwnerId(id);
    }

    private SbBill findById(Long id) throws EntityNotFoundException {
        return this.SBBillRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public SbBill findById(Long billId, Long requesterId) {
        SbBill bill = this.findById(billId);
        //TODO: validate whether owner is always in member list
        boolean hasAccess = bill.getMembers().stream().anyMatch(user -> Objects.equals(user.getId(), requesterId));
        if (!hasAccess) throw new AccessDeniedException();

        return bill;
    }

    public SbBill save(SbBill sbBill, Long requesterId) {
        if (!sbBill.isOpened()) throw new ClosedBillException();
        boolean containsOwner = sbBill.getMembers().stream().anyMatch(user -> Objects.equals(user.getId(), requesterId));

        if (!sbBill.getOwner().getId().equals(requesterId)) throw new AccessDeniedException();
        if (!containsOwner) throw new TravelBillException("You cannot remove yourself from member list");

        return SBBillRepository.save(sbBill);
    }

    public SbBill add(SbBill sbBill, Long requesterId) {
        if (sbBill.getCurrency() == null) {
            throw new TravelBillException("Currency cannot be empty");
        }

        if (sbBill.getItems() != null) {
            sbBill.getItems().forEach(item -> item.setAssigns(null));
        }

        User creator = userService.findById(requesterId);
        sbBill.setOpened(true);
        sbBill.setOwner(creator);
        sbBill.setMembers(Collections.singletonList(creator));
        return save(sbBill, requesterId);
    }

}
