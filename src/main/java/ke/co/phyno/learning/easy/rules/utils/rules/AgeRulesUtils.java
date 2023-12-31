package ke.co.phyno.learning.easy.rules.utils.rules;

import ke.co.phyno.learning.easy.rules.data.customer.CustomerInfoData;
import ke.co.phyno.learning.easy.rules.data.error.ErrorData;
import ke.co.phyno.learning.easy.rules.data.rules.RuleAgeData;
import ke.co.phyno.learning.easy.rules.utils.SharedUtils;
import lombok.extern.java.Log;
import org.jeasy.rules.api.Facts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.logging.Level;

@Component
@Log
public class AgeRulesUtils {
    @Autowired
    private SharedUtils sharedUtils;

    public boolean minimumAgeRule(@NonNull String requestId, @NonNull CustomerInfoData customerInfo, @NonNull Facts facts, String ruleData) {
        log.log(Level.INFO, String.format("Minimum age rule running - %s [ data=%s, facts=%s ]", requestId, ruleData, this.sharedUtils.toJson(facts.asMap(), true)));
        RuleAgeData data = this.sharedUtils.fromJson(ruleData, RuleAgeData.class);
        log.log(Level.INFO, String.format("Minimum age rule data from configuration - %s [ data=%s, facts=%s ]", requestId, data, this.sharedUtils.toJson(facts.asMap(), true)));

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

        if (age < data.getAge()) {
            log.log(Level.WARNING, String.format("Minimum age rule failed. Customer too young - %s [ age=%s, customerInfo=%s ]", requestId, age, customerInfo));
            facts.put("JOHARI_ERROR", ErrorData.builder()
                    .code(2)
                    .statusMessage(String.format("Customer below required age %s, customer's age %s", data.getAge(), age))
                    .customerMessage("Customer too young for this")
                    .build());
            return false;
        }

        return true;
    }

    public void minimumAgeRuleComplete(@NonNull Facts facts) {
        log.log(Level.INFO, String.format("Minimum age condition success [ %s ]", this.sharedUtils.toJson(facts.asMap(), true)));
    }

    public boolean maximumAgeRule(@NonNull String requestId, @NonNull CustomerInfoData customerInfo, @NonNull Facts facts, String ruleData) {
        log.log(Level.INFO, String.format("Maximum age rule running - %s [ data=%s, facts=%s ]", requestId, ruleData, this.sharedUtils.toJson(facts.asMap(), true)));
        RuleAgeData data = this.sharedUtils.fromJson(ruleData, RuleAgeData.class);
        log.log(Level.INFO, String.format("Maximum age rule data from configuration - %s [ data=%s, facts=%s ]", requestId, data, this.sharedUtils.toJson(facts.asMap(), true)));

        Integer age = facts.get("CUSTOMER_AGE");
        log.log(Level.INFO, String.format("Maximum age rule customer age from facts - %s [ age=%s ]", requestId, age));
        if (age == null) {
            LocalDate today = LocalDate.now(ZoneId.of("Africa/Nairobi"));
            LocalDate dateOfBirth = customerInfo.getDateOfBirth().toLocalDate();
            log.log(Level.INFO, String.format("Maximum age rule get date of birth - %s [ today=%s, dateOfBirth=%s, customerInfo=%s ]", requestId, today, dateOfBirth, customerInfo));

            if (today.isBefore(dateOfBirth)) {
                log.log(Level.INFO, String.format("Maximum age rule failed. Customer date of birth invalid - %s [ dateOfBirth=%s, customerInfo=%s ]", requestId, dateOfBirth, customerInfo));
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

            log.log(Level.INFO, String.format("Maximum age rule get age of the customer - %s [ today=%s, dateOfBirth=%s, age=%s, customerInfo=%s ]", requestId, today, dateOfBirth, age, customerInfo));

        }

        if (age > data.getAge()) {
            log.log(Level.WARNING, String.format("Maximum age rule failed. Customer too old - %s [ age=%s, customerInfo=%s ]", requestId, age, customerInfo));
            facts.put("JOHARI_ERROR", ErrorData.builder()
                    .code(2)
                    .statusMessage(String.format("Customer above required age %s, customer's age %s", data.getAge(), age))
                    .customerMessage("Customer too old for this")
                    .build());
            return false;
        }

        return true;
    }

    public void maximumAgeRuleComplete(@NonNull Facts facts) {
        log.log(Level.INFO, String.format("Maximum age condition success [ %s ]", this.sharedUtils.toJson(facts.asMap(), true)));
    }
}
