package com.travelBill.splitBill.core.bill;

import com.travelBill.TravelBillException;
import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserService;
import com.travelBill.splitBill.core.AccessDeniedException;
import com.travelBill.splitBill.core.ClosedBillException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.Objects.isNull;

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

    public List<SbBill> getAllBillsForUser(Long id) {
        return this.SBBillRepository.getSbBillsByMembersIdOrderByCreatedAtDesc(id);
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
        if (sbBill.getCurrency() == null) {
            throw new TravelBillException("Currency cannot be empty");
        }
        boolean containsOwner = sbBill.getMembers().stream().anyMatch(user -> Objects.equals(user.getId(), requesterId));

        if (!sbBill.getOwner().getId().equals(requesterId)) throw new AccessDeniedException();
        if (!containsOwner) throw new TravelBillException("You cannot remove yourself from member list");

        return SBBillRepository.save(sbBill);
    }

    public SbBill add(SbBill sbBill, Long requesterId) {
        if (sbBill.getItems() != null) {
            sbBill.getItems().forEach(item -> item.setAssigns(null));
        }

        User creator = userService.findById(requesterId);
        sbBill.setOpened(true);
        sbBill.setOwner(creator);
        sbBill.setMembers(Collections.singletonList(creator));
        sbBill.setInviteId(UUID.randomUUID().toString());
        return save(sbBill, requesterId);
    }

    public SbBill close(Long billId, Long requesterId) {
        SbBill bill = findById(billId, requesterId);
        if (!bill.isOpened()) throw new ClosedBillException();
        if (!bill.getOwner().getId().equals(requesterId)) throw new AccessDeniedException();

        bill.setOpened(false);
        bill.setClosedAt(LocalDateTime.now());
        return SBBillRepository.save(bill);
    }

    public void delete(Long billId, Long userId) {
        SbBill bill = this.findById(billId);
        if (!bill.getOwner().getId().equals(userId)) throw new AccessDeniedException();
        if (!bill.getItems().isEmpty()) throw new TravelBillException("Could not delete bill with items");
        SBBillRepository.delete(bill);
    }

    public Long invite(String inviteId, Long userId) {
        SbBill bill = SBBillRepository.getSbBillByInviteId(inviteId).orElseThrow(() -> new TravelBillException("Invite link is invalid!"));
        boolean isUserAlreadyMember = !isNull(bill.getMembers().stream().filter(user -> user.getId().equals(userId)).findFirst().orElse(null));
        if (isUserAlreadyMember) {
            return bill.getId();
        } else {
            User user = userService.findById(userId);
            bill.getMembers().add(user);
            return SBBillRepository.save(bill).getId();
        }
    }
}
