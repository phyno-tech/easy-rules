package ke.co.phyno.learning.easy.rules.data.error;

import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorData implements Serializable {
    @Builder.Default
    private Integer code = null;

    @Builder.Default
    private String statusMessage = null;

    @Builder.Default
    private String customerMessage = null;
}
