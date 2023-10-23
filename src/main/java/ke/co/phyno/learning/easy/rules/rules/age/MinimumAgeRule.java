package ke.co.phyno.learning.easy.rules.rules.age;

import ke.co.phyno.learning.easy.rules.data.customer.CustomerInfoData;
import ke.co.phyno.learning.easy.rules.rules.BaseRule;
import ke.co.phyno.learning.easy.rules.utils.RuleUtils;
import ke.co.phyno.learning.easy.rules.utils.SharedUtils;
import ke.co.phyno.learning.easy.rules.utils.rules.AgeRulesUtils;
import lombok.extern.java.Log;
import org.jeasy.rules.annotation.*;
import org.jeasy.rules.api.Facts;

import java.util.logging.Level;

@Log
@Rule(name = "MINIMUM_AGE", description = "Customer's Minimum Age Rule")
public class MinimumAgeRule extends BaseRule {
    private final AgeRulesUtils ageRulesUtils;
    private final SharedUtils sharedUtils;

    public MinimumAgeRule() {
        this.ageRulesUtils = RuleUtils.getBean(AgeRulesUtils.class);
        this.sharedUtils = RuleUtils.getBean(SharedUtils.class);
    }

    @Priority
    public int priority() {
        return this.getPriority();
    }

    @Condition
    public boolean when(
            @Fact(value = "REQUEST_ID") String requestId,
            @Fact(value = "FINACLE_CUSTOMER_INFO") CustomerInfoData customerInfo,
            Facts facts
    ) {
        return this.ageRulesUtils.minimumAgeRule(requestId, customerInfo, facts, this.getData());
    }

    @Action
    public void complete(Facts facts) {
        log.log(Level.INFO, String.format("Minimum age condition success [ %s ]", this.sharedUtils.toJson(facts.asMap(), true)));
    }
}
