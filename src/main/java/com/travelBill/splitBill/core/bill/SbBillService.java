package com.travelBill.splitBill.core.bill;

import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserService;
import com.travelBill.splitBill.core.MemberAlreadyInBillException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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

    public SbBill findById(Long id) throws EntityNotFoundException {
        return this.SBBillRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public SbBill save(SbBill sbBill) {
        return SBBillRepository.save(sbBill);
    }

    public SbBill save(String title, User creator, Long chatId) {
        SbBill sbBill = new SbBill();
        sbBill.setTitle(title);
        sbBill.setOwner(creator);
        sbBill = save(sbBill);

        return sbBill;
    }

    public SbBill addMember(SbBill sbBill, User member) {
        if (isNewMember(sbBill, member)) {
            sbBill.getMembers().add(member);
            return save(sbBill);
        } else {
            throw new MemberAlreadyInBillException();
        }

    }

    private boolean isNewMember(SbBill sbBill, User member) {
        List<User> matchedUser = sbBill.getMembers()
                .stream()
                .filter(u -> u.getId().equals(member.getId()))
                .collect(Collectors.toList());

        return matchedUser.size() == 0;
    }
}
