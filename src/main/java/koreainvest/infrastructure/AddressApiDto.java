package koreainvest.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

public class AddressApiDto {

    @Getter
    @Setter
    @ToString
    public static class GetAddressInfo{
        @JsonProperty("results")
        private Result result;
    }

    @Getter
    @Setter
    @ToString
    public static class Result{
        @JsonProperty("common")
        private CommonResponse commonResponse;
        @JsonProperty("juso")
        private List<JusoResponse> jusoResponses;
    }


    @Getter
    @Setter
    @ToString
    public static class CommonResponse{
        @JsonProperty("errorMessage")
        private String errorMessage;
        @JsonProperty("totalCount")
        private Long totalCount;
    }

    @Getter
    @Setter
    @ToString
    public static class JusoResponse{
        @JsonProperty("rn")
        private String roadName;
        @JsonProperty("zipNo")
        private String zipNo;
        @JsonProperty("roadAddr")
        private String roadAddress;
    }
}