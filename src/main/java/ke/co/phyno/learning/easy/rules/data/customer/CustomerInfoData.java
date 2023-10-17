package ke.co.phyno.learning.easy.rules.data.customer;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerInfoData implements Serializable {
    @Builder.Default
    private String name = "test";

    @Builder.Default
    private Date dateOfBirth = Date.valueOf(LocalDate.now());
}
