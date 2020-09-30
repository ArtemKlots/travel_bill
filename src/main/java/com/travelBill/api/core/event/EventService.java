package com.travelBill.api.core.event;

import com.travelBill.api.core.debt.Debt;
import com.travelBill.api.core.debt.DebtService;
import com.travelBill.api.core.debt.debtCalculator.DebtCalculator;
import com.travelBill.api.core.event.exceptions.ClosedEventException;
import com.travelBill.api.core.event.exceptions.MemberAlreadyInEventException;
import com.travelBill.api.core.exceptions.AccessDeniedException;
import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {
    private final EventRepository eventRepository;
    private final DebtService debtService;
    private final UserService userService;
    private final EventAccessService eventAccessService;
    DebtCalculator debtCalculator = new DebtCalculator();

    public EventService(EventRepository eventRepository,
                        DebtService debtService,
                        UserService userService,
                        EventAccessService eventAccessService) {
        this.eventRepository = eventRepository;
        this.debtService = debtService;
        this.userService = userService;
        this.eventAccessService = eventAccessService;
    }

    /**
     * @param eventId event to close
     * @param userId  user who closes an event
     * @return updated event
     * @throws AccessDeniedException when user dont have access to the event
     * TODO return close event result with event and debts
     */
    public Event closeEvent(Long eventId, Long userId) {
        if (!eventAccessService.hasAccessToEvent(userId, eventId)) {
            throw new AccessDeniedException("User don't have access to the provided event");
        }

        Event event = this.eventRepository.getOne(eventId);

        if (!event.isOpened()) {
            throw new ClosedEventException();
        }

        event.setOpened(false);
        event.setClosedAt(LocalDateTime.now()); //todo get rid from it
        event = eventRepository.save(event);

        List<Debt> debts = debtCalculator.calculate(event);

        debtService.saveAll(debts, event);
        return event;
    }

    public List<Event> getAll() {
        return this.eventRepository.findAll();
    }

    public List<Event> getEventsByOwnerId(Long id) {
        return this.eventRepository.getEventsByOwnerId(id);
    }

    public Event findById(Long id) throws EntityNotFoundException {
        return this.eventRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Event findByTelegramChatId(Long id) throws EntityNotFoundException {
        return eventRepository.findByTelegramChatId(id);
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public Event save(String title, User creator, Long chatId) {
        Event event = new Event();
        event.setTitle(title);
        event.setOwner(creator);
        event.setTelegramChatId(chatId);
        event = save(event);

        switchCurrentEvent(creator, event);

        return event;
    }

    public Event addMember(Event event, User member) {
        if (isNewMember(event, member)) {
            event.getMembers().add(member);
            return save(event);
        } else {
            throw new MemberAlreadyInEventException();
        }

    }

    private boolean isNewMember(Event event, User member) {
        List matchedUser = event.getMembers()
                .stream()
                .filter(u -> u.getId().equals(member.getId()))
                .collect(Collectors.toList());

        return matchedUser.size() == 0;
    }

    public void switchCurrentEvent(User user, Event event) {
        user.setCurrentEvent(event);
        userService.save(user);
    }
}
