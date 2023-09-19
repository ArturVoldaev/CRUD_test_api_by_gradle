package api.dto;

import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class UpdatePetDto {
    int id;
    public CreateCategory category;
    String name;
    List<String> photoUrls;
    List <CreateTagsDto> tags;
    String status;
}
