package ke.co.phyno.learning.easy.rules.rules.age;

import ke.co.phyno.learning.easy.rules.data.customer.CustomerInfoData;
import ke.co.phyno.learning.easy.rules.rules.BaseRule;
import ke.co.phyno.learning.easy.rules.utils.RuleUtils;
import ke.co.phyno.learning.easy.rules.utils.rules.AgeRulesUtils;
import lombok.extern.java.Log;
import org.jeasy.rules.annotation.*;
import org.jeasy.rules.api.Facts;

@Log
@Rule(name = "MAXIMUM_AGE", description = "Customer's Maximum Age Rule")
public class MaximumAgeRule extends BaseRule {
    private final AgeRulesUtils ageRulesUtils;

    public MaximumAgeRule() {
        this.ageRulesUtils = RuleUtils.getBean(AgeRulesUtils.class);
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
        return this.ageRulesUtils.maximumAgeRule(requestId, customerInfo, facts, this.getData());
    }

    @Action
    public void complete(Facts facts) {
        this.ageRulesUtils.maximumAgeRuleComplete(facts);
    }
}
