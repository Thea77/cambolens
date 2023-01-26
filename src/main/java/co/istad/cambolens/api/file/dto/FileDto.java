package co.istad.cambolens.api.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class FileDto {

    private Long id;
    private String uuid;
    private String name;
    private String uri;
    private String extension;
    private Float size;
    private Integer download;
    private Boolean isEnabled;

    public void buildNameAndUri(String baseUri) {
        extension = extension.trim();
        name = String.format("%s.%s", uuid, extension);
        uri = baseUri + name;
    }

}
