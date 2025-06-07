package api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    @JsonProperty("id")
    private Long id = 0L;  // Фиксируем на 0, чтобы id добавлялось автоматом

    @JsonProperty("category")
    private Category category;

    @JsonProperty("name")
    private String name;

    @JsonProperty("photoUrls")
    private List<String> photoUrls;

    @JsonProperty("tags")
    private List<Tag> tags;

    @JsonProperty("status")
    private String status;  // available, pending, sold

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Category {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("name")
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tag {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("name")
        private String name;
    }
}