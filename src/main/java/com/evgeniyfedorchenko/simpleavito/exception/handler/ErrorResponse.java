package com.evgeniyfedorchenko.simpleavito.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.Objects;


/**
 * Класс, содержащий детали и причины исключения, которое было поймано с помощью {@link SimpleAvitoExceptionHandler}
 * Создан с целью стандартизировать слишком обобщенный ответ {@code ResponseEntity.badRequest().build()}
 * Должен быть возвращен в теле ответа при обнаружении ситуаций, требующий возврата {@link HttpStatus#BAD_REQUEST}
 *
 * @field code        Пользовательский код ошибки
 * @field errorsCount Количество найденных нарушений
 * @field details     Детальное сообщение об обнаруженных нарушениях
 * @field badObject   Исходный невалидный объект
 *
 * @apiNote Пример сериализованого объекта:
 * <blockquote><pre>
 * {
 *     "code": 4,
 *     "details_map": {
 *         "price": "must be greater than or equal to 0",
 *         "description": "size must be between 8 and 64",
 *         "title": "size must be between 4 and 32"
 *     },
 *     "errors_count": 2,
 *     "bad_object_name": "properties",
 *     "bad_object_params": {
 *         "title": "t",
 *         "price": -1,
 *         "description": "very vey vey very large text field"
 *     }
 * }
 * </pre></blockquote>
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ErrorResponse {

    private final int code;

    @JsonProperty("errors_count")
    private final int errorsCount;

    @JsonProperty("details_map")
    private final Map<String, String> detailsMap;

    private final String details;

    @JsonProperty("bad_object_name")
    private final String badObjName;

    @JsonProperty("bad_object_params")
    private final Object badObj;

}
