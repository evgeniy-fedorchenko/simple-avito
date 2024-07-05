package com.evgeniyfedorchenko.simpleavito.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

/* Я сменил аннотацию @Data на этот набор, чтобы исключить аннотацию @ToString.
   Если в других дтошках я сделал просто @ToString.Exclude на пароле, то в этом
   объекте фактически вообще нет полей, которые можно отобразить через toString() */

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class NewPassword {

    @Size(min = 8, max = 16)
    private String currentPassword;

    @Size(min = 8, max = 16)
    private String newPassword;

}
