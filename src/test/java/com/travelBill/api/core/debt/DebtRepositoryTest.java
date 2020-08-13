package com.travelBill.api.core.debt;

import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = NONE)
@DataJpaTest
class DebtRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DebtRepository debtRepository;

    private static User poorUser;
    private static User richUser;
    private static String USD = "USD";
    private static String EUR = "EUR";

    @BeforeEach
    void setupContext() {
        poorUser = new User();
        poorUser.setFirstName("Poor First");
        poorUser.setLastName("Poor Last");

        richUser = new User();
        richUser.setFirstName("Rich First");
        richUser.setLastName("Rich Last");

        poorUser = userRepository.save(poorUser);
        richUser = userRepository.save(richUser);
    }

    @Test
    void getDebtsForUser_shouldResolveDebts_whenOnePeronPaidInOneCurrency() {
        Debt poorUserDebt1 = Debt.newBuilder()
                .withDebtor(poorUser)
                .withPayer(richUser)
                .withAmount(10)
                .withCurrency(USD)
                .build();

        Debt poorUserDebt2 = Debt.newBuilder()
                .withDebtor(poorUser)
                .withPayer(richUser)
                .withAmount(100)
                .withCurrency(USD)
                .build();

        debtRepository.saveAll(Arrays.asList(poorUserDebt1, poorUserDebt2));

        List<DebtSumDto> debtsForPoorUser = debtRepository.getDebtsForUser(poorUser.getId());
        List<DebtSumDto> debtsForRichUser = debtRepository.getDebtsForUser(richUser.getId());

        assertEquals(debtsForPoorUser.size(), 0);
        assertEquals(debtsForRichUser.size(), 1);
        assertEquals(debtsForRichUser.get(0).getAmount(), 110);
        assertEquals(debtsForRichUser.get(0).getCurrency(), USD);
        assertEquals(debtsForRichUser.get(0).getDebtorId(), poorUser.getId());
        assertEquals(debtsForRichUser.get(0).getUserFirstName(), poorUser.getFirstName());
        assertEquals(debtsForRichUser.get(0).getUserLastName(), poorUser.getLastName());
    }

    @Test
    void getDebtsForUser_shouldResolveDebts_whenTwoPersonPaidDifferentAmountInOneCurrency() {
        Debt poorUserDebt = Debt.newBuilder()
                .withDebtor(richUser)
                .withPayer(poorUser)
                .withAmount(10)
                .withCurrency(USD)
                .build();

        Debt richUserDebt = Debt.newBuilder()
                .withDebtor(poorUser)
                .withPayer(richUser)
                .withAmount(100)
                .withCurrency(USD)
                .build();

        debtRepository.saveAll(Arrays.asList(poorUserDebt, richUserDebt));

        List<DebtSumDto> debtsForPoorUser = debtRepository.getDebtsForUser(poorUser.getId());
        List<DebtSumDto> debtsForRichUser = debtRepository.getDebtsForUser(richUser.getId());

        assertEquals(debtsForPoorUser.size(), 1);
        assertEquals(debtsForRichUser.size(), 1);

        assertEquals(debtsForPoorUser.get(0).getAmount(), -90);
        assertEquals(debtsForPoorUser.get(0).getCurrency(), USD);
        assertEquals(debtsForPoorUser.get(0).getDebtorId(), richUser.getId());
        assertEquals(debtsForPoorUser.get(0).getUserFirstName(), richUser.getFirstName());
        assertEquals(debtsForPoorUser.get(0).getUserLastName(), richUser.getLastName());

        assertEquals(debtsForRichUser.get(0).getAmount(), 90);
        assertEquals(debtsForRichUser.get(0).getCurrency(), USD);
        assertEquals(debtsForRichUser.get(0).getDebtorId(), poorUser.getId());
        assertEquals(debtsForRichUser.get(0).getUserFirstName(), poorUser.getFirstName());
        assertEquals(debtsForRichUser.get(0).getUserLastName(), poorUser.getLastName());
    }

    @Test
    void getDebtsForUser_shouldResolveDebts_whenOnePeronPaidInTwoCurrencies() {
        Debt poorUserUsdDebt1 = Debt.newBuilder()
                .withDebtor(poorUser)
                .withPayer(richUser)
                .withAmount(10)
                .withCurrency(USD)
                .build();
        Debt poorUserUsdDebt2 = Debt.newBuilder()
                .withDebtor(poorUser)
                .withPayer(richUser)
                .withAmount(20)
                .withCurrency(USD)
                .build();

        Debt poorUserEurDebt1 = Debt.newBuilder()
                .withDebtor(poorUser)
                .withPayer(richUser)
                .withAmount(10)
                .withCurrency(EUR)
                .build();
        Debt poorUserEurDebt2 = Debt.newBuilder()
                .withDebtor(poorUser)
                .withPayer(richUser)
                .withAmount(15)
                .withCurrency(EUR)
                .build();

        debtRepository.saveAll(Arrays.asList(poorUserUsdDebt1, poorUserUsdDebt2, poorUserEurDebt1, poorUserEurDebt2));

        List<DebtSumDto> debtsForPoorUser = debtRepository.getDebtsForUser(poorUser.getId());
        List<DebtSumDto> debtsForRichUser = debtRepository.getDebtsForUser(richUser.getId());

        assertEquals(debtsForPoorUser.size(), 0);
        assertEquals(debtsForRichUser.size(), 2);
        assertEquals(debtsForRichUser.get(0).getCurrency(), USD);
        assertEquals(debtsForRichUser.get(0).getAmount(), 30);
        assertEquals(debtsForRichUser.get(0).getDebtorId(), poorUser.getId());
        assertEquals(debtsForRichUser.get(1).getAmount(), 25);
        assertEquals(debtsForRichUser.get(1).getCurrency(), EUR);
        assertEquals(debtsForRichUser.get(1).getDebtorId(), poorUser.getId());
    }

    @Test
    void getDebtsForUser_shouldResolveDebts_whenTwoPersonPaidDifferentAmountInTwoCurrencies() {
        Debt poorUserUsdDebt = Debt.newBuilder()
                .withDebtor(poorUser)
                .withPayer(richUser)
                .withAmount(150)
                .withCurrency(USD)
                .build();
        Debt poorUserEurDebt = Debt.newBuilder()
                .withDebtor(poorUser)
                .withPayer(richUser)
                .withAmount(100)
                .withCurrency(EUR)
                .build();

        Debt richUserUsdDebt = Debt.newBuilder()
                .withDebtor(richUser)
                .withPayer(poorUser)
                .withAmount(15)
                .withCurrency(USD)
                .build();
        Debt richUserEurDebt = Debt.newBuilder()
                .withDebtor(richUser)
                .withPayer(poorUser)
                .withAmount(10)
                .withCurrency(EUR)
                .build();

        debtRepository.saveAll(Arrays.asList(poorUserUsdDebt, poorUserEurDebt, richUserUsdDebt, richUserEurDebt));

        List<DebtSumDto> debtsForPoorUser = debtRepository.getDebtsForUser(poorUser.getId());
        List<DebtSumDto> debtsForRichUser = debtRepository.getDebtsForUser(richUser.getId());

        assertEquals(debtsForPoorUser.size(), 2);
        assertEquals(debtsForRichUser.size(), 2);

        assertEquals(debtsForPoorUser.get(0).getCurrency(), USD);
        assertEquals(debtsForPoorUser.get(0).getAmount(), -135);
        assertEquals(debtsForPoorUser.get(0).getDebtorId(), richUser.getId());
        assertEquals(debtsForPoorUser.get(1).getAmount(), -90);
        assertEquals(debtsForPoorUser.get(1).getCurrency(), EUR);
        assertEquals(debtsForPoorUser.get(1).getDebtorId(), richUser.getId());

        assertEquals(debtsForRichUser.get(0).getCurrency(), USD);
        assertEquals(debtsForRichUser.get(0).getAmount(), 135);
        assertEquals(debtsForRichUser.get(0).getDebtorId(), poorUser.getId());
        assertEquals(debtsForRichUser.get(1).getAmount(), 90);
        assertEquals(debtsForRichUser.get(1).getCurrency(), EUR);
        assertEquals(debtsForRichUser.get(1).getDebtorId(), poorUser.getId());
    }
}