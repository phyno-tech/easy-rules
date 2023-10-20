package ke.co.phyno.learning.easy.rules.rules.age;

import ke.co.phyno.learning.easy.rules.data.customer.CustomerInfoData;
import ke.co.phyno.learning.easy.rules.rules.BaseRule;
import ke.co.phyno.learning.easy.rules.type.RuleType;
import ke.co.phyno.learning.easy.rules.utils.SharedUtils;
import lombok.extern.java.Log;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

@Log
@SpringBootTest
public class AgeRulesTests {
    @Autowired
    private SharedUtils sharedUtils;

    @Autowired
    private RulesEngine ageRulesEngine;

    private final CustomerInfoData customerInfo = CustomerInfoData.builder()
            .name("Phelix Ochieng")
            .dateOfBirth(Date.valueOf(LocalDate.of(1980, 1, 4)))
            .build();

    private Facts facts() {
        Facts facts = new Facts();
        facts.put("REQUEST_ID", UUID.randomUUID().toString());
        facts.put("FINACLE_CUSTOMER_INFO", this.customerInfo);
        return facts;
    }

    @Test
    @DisplayName(value = "Test minimum age rule")
    public void testMinimumAgeRule() {
        Rules rules = new Rules();
        rules.register(RuleType.MINIMUM_AGE.getRule());

        RulesEngine rulesEngine = this.ageRulesEngine;
        Facts facts = this.facts();
        rulesEngine.fire(rules, facts);
        log.log(Level.INFO, String.format("Minimum age rule fired [ %s ]", facts));
    }

    @Test
    @DisplayName(value = "Test maximum age rule")
    public void testMaximumAgeRule() {
        Rules rules = new Rules();
        rules.register(RuleType.MAXIMUM_AGE.getRule());

        RulesEngine rulesEngine = this.ageRulesEngine;
        Facts facts = this.facts();
        rulesEngine.fire(rules, facts);
        log.log(Level.INFO, String.format("Maximum age rule fired [ %s ]", facts));
    }

    @Test
    @DisplayName(value = "Test all age rules")
    public void testAllAgeRules() {
        List<BaseRule> rules = new ArrayList<>();

        BaseRule min = RuleType.MINIMUM_AGE.getRule();
        min.setPriority(1);
        min.setData("{\"age\":10}");
        rules.add(min);

        BaseRule max = RuleType.MAXIMUM_AGE.getRule();
        max.setPriority(2);
        max.setData("{\"age\":65}");
        rules.add(max);

        RulesEngine rulesEngine = this.ageRulesEngine;
        Facts facts = this.facts();

        rulesEngine.fire(new Rules(rules.toArray()), facts);
        log.log(Level.INFO, String.format("All rules fired [ %s ]", this.sharedUtils.toJson(facts.asMap(), true)));
    }
}
