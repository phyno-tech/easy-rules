package ke.co.phyno.learning.easy.rules.rules.age;

import ke.co.phyno.learning.easy.rules.data.customer.CustomerInfoData;
import ke.co.phyno.learning.easy.rules.data.error.ErrorData;
import lombok.extern.java.Log;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.logging.Level;

@Log
@Rule(name = "Minimum Age Rule", description = "Customer's Minimum Age Rule", priority = 1)
public class MinimumAgeRule {
    @Condition
    public boolean when(
            @Fact(value = "REQUEST_ID") String requestId,
            @Fact(value = "FINACLE_CUSTOMER_INFO") CustomerInfoData customerInfo,
            Facts facts
    ) {
        log.log(Level.INFO, String.format("Minimum age rule running - %s [ customerInfo=%s ]", requestId, customerInfo));

        Integer age = facts.get("CUSTOMER_AGE");
        log.log(Level.INFO, String.format("Minimum age rule customer age from facts - %s [ age=%s ]", requestId, age));
        if (age == null) {
            LocalDate today = LocalDate.now(ZoneId.of("Africa/Nairobi"));
            LocalDate dateOfBirth = customerInfo.getDateOfBirth().toLocalDate();
            log.log(Level.INFO, String.format("Minimum age rule get date of birth - %s [ today=%s, dateOfBirth=%s, customerInfo=%s ]", requestId, today, dateOfBirth, customerInfo));

            if (today.isBefore(dateOfBirth)) {
                log.log(Level.INFO, String.format("Minimum age rule failed. Customer date of birth invalid - %s [ dateOfBirth=%s, customerInfo=%s ]", requestId, dateOfBirth, customerInfo));
                facts.put("JOHARI_ERROR", ErrorData.builder()
                        .code(1)
                        .statusMessage(String.format("Invalid date of birth from customer info - %s", dateOfBirth))
                        .customerMessage("Customer date of birth not valid")
                        .build());
                return false;
            }

            Period period = Period.between(dateOfBirth, today);
            age = period.getYears();
            facts.put("CUSTOMER_AGE", age);

            log.log(Level.INFO, String.format("Minimum age rule get age of the customer - %s [ today=%s, dateOfBirth=%s, age=%s, customerInfo=%s ]", requestId, today, dateOfBirth, age, customerInfo));

        }

        if (age < this.getMinimumAge()) {
            log.log(Level.WARNING, String.format("Minimum age rule failed. Customer too young - %s [ age=%s, customerInfo=%s ]", requestId, age, customerInfo));
            facts.put("JOHARI_ERROR", ErrorData.builder()
                    .code(2)
                    .statusMessage(String.format("Customer below required age %s, customer's age %s", this.getMinimumAge(), age))
                    .customerMessage("Customer too young for this")
                    .build());
            return false;
        }

        return true;
    }

    @Action
    public void complete(Facts facts) {
        log.log(Level.INFO, String.format("Minimum age run [ %s ]", facts.asMap()));
    }

    private int getMinimumAge() {
        return 18;
    }
}
