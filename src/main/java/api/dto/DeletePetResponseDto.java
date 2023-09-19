package api.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class DeletePetResponseDto {
      int code;
      String type;
      String message;
}
